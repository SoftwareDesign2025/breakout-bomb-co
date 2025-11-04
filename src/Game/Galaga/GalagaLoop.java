/*
Authors:
Murph Lennemann, Oscar Kardon
 */

package Game.Galaga;

import Game.CollisionDetector;
import Game.GameLoop;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import Objects.Laser;
import Objects.Lasers;
import javafx.scene.input.KeyCode;

public class GalagaLoop extends GameLoop {
    private final GalagaEnemies enemies;
    private final Lasers lasers;
    private final CollisionDetector collisionDetector;
    private final GalagaScreen galagaScreen;
    private Ship ship;

    private long lastShotTime = 0;
    private final int laserCooldown = 300; // ms between shots

    /**
     * Creates the Galaga game loop
     */
    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen, galagaScreen.getEnemies());
        this.galagaScreen = galagaScreen;
        galagaScreen.loadLevel(level);
        this.enemies = galagaScreen.getEnemies();
        this.lasers = galagaScreen.getLasers();
        this.ship = galagaScreen.getShip();
        this.collisionDetector = new CollisionDetector(lasers, enemies);
    }

    /**
     * Main step method, runs each frame
     */
    @Override
    public void step() {
        showScreen();
        handleKeyInput();

        if (gameOn()) return;

        runGame();
        checkLives();
        checkWin();
        lasers.update();

        // handle collisions
        points += collisionDetector.checkLaserEnemyCollisions();

        // enemy shooting
        Laser enemyLaser = enemies.tryShoot();
        if (enemyLaser != null) {
            lasers.addLaser(enemyLaser);
        }

        // ship hit detection
        if (collisionDetector.checkLaserShipCollisions(ship)) {
            lives--;
            System.out.println("Ship hit! Lives remaining: " + lives);
        }
    }

    /**
     * Handles player input each frame
     */
    @Override
    public void handleKeyInput() {
        if (gameOver || !moving) return;

        moveLeftAndRight(ship);

        if (pressedKeys.contains(KeyCode.SPACE)) {
            lastShot(ship);
        }

        // Debug key to clear enemies
        if (pressedKeys.contains(KeyCode.B)) {
            enemies.clearObjects(screen);
        }
    }

    /**
     * Handles laser shooting with cooldown
     */
    public void lastShot(Ship ship) {
        long now = System.currentTimeMillis();
        if (now - lastShotTime >= laserCooldown) {
            Laser laser = new Laser(
                    ship.getShip().getLayoutX() + ship.getShip().getFitWidth() / 2,
                    ship.getShip().getLayoutY(),
                    true
            );
            lasers.addLaser(laser);
            lastShotTime = now;
        }
    }

    /**
     * Handles enemy movement and bottom check
     */
    private void runGame() {
        enemies.drop();
        int reached = enemies.enemiesReachedBottom();
        if (reached > 0) {
            lives -= reached;
            if (lives < 0) lives = 0;
        }
    }

    private void checkWin() {
        if (levelOver()) {
            gameOverLogic();
            screen.gameWinScreen();
        }
    }

    @Override
    public boolean levelOver() {
        return enemies.isCleared();
    }

    @Override
    public void resetLevel() {
        moving = false;
        ship = galagaScreen.getShip();
    }

    @Override
    public void startMoving() {
        super.startMoving();
        enemies.drop();
    }

    @Override
    public boolean gameOn() {
        return (!moving || gameOver);
    }

    @Override
    public String getFileName() {
        return "GalagaHighScore.txt";
    }
}

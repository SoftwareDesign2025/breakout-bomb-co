/*
Authors:
Murph Lennemann
Oscar Kardon
 */

package Game.Galaga;

import Game.CollisionDetector;
import Game.GameLoop;
import Objects.Galaga.GalagaEnemy;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import Objects.HittableObject;
import Objects.HittableObjects;
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
     * Authors: Murph
     * Main step method, runs each frame
     */
    @Override
    public void step() {
        showScreen();
        if (gameOn()) return;
        runGame();
        handleKeyInput();
        checkLevel();
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
            ship.shipHit();
        }
    }

    /**
     * Authors: Murph
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
     * Authors: Murph
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
     * Authors: Murph
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

    /**
     * Authors: Murph
     * checks if the game is won
     */
    private void checkWin() {
        if (levelOver()) {
            gameOverLogic();
            screen.gameWinScreen();
        }
    }

    /**
     * Authors: Murph
     * @return if the level is over
     */
    @Override
    public boolean levelOver() {
        int enemyCount = 0;
        for (HittableObject enemy: enemies.getHittableObjects()) {
            enemyCount++;
        }
        return enemies.isCleared();
    }

    /**
     * Authors: Murph
     * resets to new level
     */
    @Override
    public void resetLevel() {
        moving = false;
        lasers.clear();
        galagaScreen.loadLevel(level);
        ship = galagaScreen.getShip();
    }

    /**
     * Authors: Murph
     * Used to start the game on click
     */
    @Override
    public void startMoving() {
        super.startMoving();
        enemies.drop();
    }

    /**
     * Authors: Murph
     * @return if the game is still playable
     */
    @Override
    public boolean gameOn() {
        return (!moving || gameOver);
    }

    /**
     * Authors: Murph
     * @return the high score file name
     */
    @Override
    public String getFileName() {
        return "GalagaHighScore.txt";
    }
}

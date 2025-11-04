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
    private final Ship ship;

    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen, galagaScreen.getEnemies());
        galagaScreen.loadLevel(1);
        this.enemies = galagaScreen.getEnemies();
        this.lasers = galagaScreen.getLasers();
        this.collisionDetector = new CollisionDetector(lasers, enemies);
        this.ship = galagaScreen.getShip();
    }

    @Override
    public void step() {
        showScreen();
        handleKeyInput();

        if (gameOn()) return;

        runGame();
        checkLives();
        checkWin();
        lasers.update();
        points += collisionDetector.checkLaserEnemyCollisions();

        Laser enemyLaser = enemies.tryShoot();
        if (enemyLaser != null) {
            lasers.addLaser(enemyLaser);
        }

        if (collisionDetector.checkLaserShipCollisions(ship)) {
            lives--;
            System.out.println("Ship hit! Lives remaining: " + lives);
        }
    }

    /**
     * Handles player input each frame
     */
    public void handleKeyInput() {
        if (gameOver || !moving) return;

        // Move left/right
        moveLeftAndRight(ship);

        // Fire laser when space is pressed
        if (pressedKeys.contains(KeyCode.SPACE)) {
            lastShot(ship);
        }

        // Clear enemies (debug/cheat)
        if (pressedKeys.contains(KeyCode.B)) {
            enemies.clearObjects(screen);
        }
    }

    @Override
    public boolean levelOver() {
        return enemies.isCleared();
    }

    /**
     * Handles enemy movement and bottom check
     */
    private void runGame() {
        enemies.drop();
        lives -= enemies.enemiesReachedBottom();
    }

    private void checkWin() {
        if (levelOver()) {
            gameOverLogic();
            screen.gameWinScreen();
        }
    }

    @Override
    public void resetLevel() {
        // Optionally implement later
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

    /**
     * Handles shooting cooldown
     */
    public void lastShot(Ship ship) {
        int laserCooldown = 300;
        now = System.currentTimeMillis();
        //if (now - lastShotTime >= laserCooldown) {
        Laser laser = new Laser(
                ship.getShip().getLayoutX() + ship.getShip().getFitWidth() / 2,
                ship.getShip().getLayoutY(),
                true
        );
        lasers.addLaser(laser);
        // lastShotTime = now;
        //}
    }

    @Override
    public String getFileName() {
        return "GalagaHighScore.txt";
    }
}

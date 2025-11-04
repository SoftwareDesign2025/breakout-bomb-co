/*
Authors:
Murph Lennemann

 */

package Game.Galaga;


import Game.GameLoop;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import javafx.scene.input.KeyCode;

public class GalagaLoop extends GameLoop {
    private final GalagaEnemies enemies;
    private long lastShotTime = 0;
    private final Ship ship;

    /**
     * Authors: Murph
     * @param galagaScreen
     */
    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen);
        galagaScreen.loadLevel(2);
        this.enemies = galagaScreen.getEnemies();
        this.ship = galagaScreen.getShip();
    }

    /**
     * Authors: Murph
     */
    @Override
    public void step() {
        showScreen();
        if (moving) {
            runGame();
            handleKeyInput();
        }
        checkLives();
    }

    /**
     * Authors: Murph
     * @return
     */
    @Override
    public boolean levelOver() {
        return (enemies.isCleared());
    }

    /**
     * Authors: Murph
     */
    private void runGame() {
        enemies.drop();
        lives -= enemies.enemiesReachedBottom();
    }

    /**
     * Authors: Murph
     * @return
     */
    public String getFileName() {
        return "GalagaHighScore.txt";
    }

    /**
     * Authors: Murph
     */
    @Override
    public void resetLevel(){}

    /**
     * Authors: Murph
     */
    @Override
    public void startMoving(){
        super.startMoving();
        enemies.drop();
    }

    /**
     * Authors: Murph
     * @return
     */
    @Override
    public boolean gameOn() {
        moving = true;
        return moving;
    }

    /**
     * Authors: Murph
     */
    @Override
    public void handleKeyInput() {
        moveLeftAndRight(ship);
        if (pressedKeys.contains(KeyCode.SPACE)) {
            lastShot(ship);
        }
    }

    /**
     * Authors: Murph
     * @param ship
     */
    public void lastShot(Ship ship) {
        int laserCooldown = 300;
        now =  System.currentTimeMillis();
        if (now - lastShotTime >= laserCooldown) {
            ship.shootLaser();
            lastShotTime = now;
        }
    }
}

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
     * Creates the loop that runs the code
     * @param galagaScreen is a new screen
     */
    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen, galagaScreen.getEnemies());
        galagaScreen.loadLevel(2);
        this.enemies = galagaScreen.getEnemies();
        this.ship = galagaScreen.getShip();
    }

    /**
     * Authors: Murph
     * The step method that defines how the game is run
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
     * @return if the game is still playable
     */
    @Override
    public boolean levelOver() {
        return (enemies.isCleared());
    }

    /**
     * Authors: Murph
     * handles the game portion
     */
    private void runGame() {
        enemies.drop();
        lives -= enemies.enemiesReachedBottom();
    }

    /**
     * Authors: Murph
     * @return gets the file name of the High Score
     */
    public String getFileName() {
        return "GalagaHighScore.txt";
    }

    /**
     * Authors: Murph
     * resets the level when level is cleared
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
     * @return if the game is still being played
     */
    @Override
    public boolean gameOn() {
        return (!moving || gameOver);
    }

    /**
     * Authors: Murph
     * Hanldes what happens on button press
     */
    @Override
    public void handleKeyInput() {
        moveLeftAndRight(ship);
        if (pressedKeys.contains(KeyCode.SPACE)) {
            lastShot(ship);
        }
        if (pressedKeys.contains(KeyCode.B)) {
            tryLevelSkip();
        }
    }

    /**
     * Authors: Murph
     * Handles shot cooldown
     * @param ship the ship on the screen
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

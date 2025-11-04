/*
Authors:
Murph Lennemann

 */

package Game;

import Objects.HittableObjects;
import Objects.SideMover;
import javafx.scene.input.KeyCode;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public abstract class GameLoop {

    protected Screen screen;
    protected int lives = 5;
    protected int points = 0;
    protected int highScore;
    protected int level = 1;
    protected boolean gameOver = false;
    protected final String fileName;
    protected boolean moving = false;
    protected final Set<KeyCode> pressedKeys = new HashSet<>();
    protected long now;
    protected long lastSkip = 0;
    protected final int SKIP_COOLDOWN = 1000;
    protected HittableObjects hittableObjects;

    /**
     * Authors: Murph
     * The game Loop object wich has shared functionality
     * @param screen the screen that the game time is showing
     */
    public GameLoop(Screen screen, HittableObjects hittableObjects) {
        this.screen = screen;
        this.fileName = getFileName();
        this.highScore = getHighScore();
        this.hittableObjects = hittableObjects;
    }

    /**
     * Authors: Murph
     * Checks if the player still has lives left and acts accordingly
     */
    public void checkLives() {
        if (lives <= 0) {
            gameOverLogic();
            screen.gameOverScreen();
        }
    }

    /**
     * Authors: Murph
     * updates the level if necessary
     */
    public void checkLevel() {
        if (!levelOver()) {
            level++;
            if (level <= 3){
                resetLevel();
            }
            else {
                gameOverLogic();
                screen.gameWinScreen();
            }
        }
    }

    /**
     * Authors: Murph
     * Displays the screen
     */
    protected void showScreen() {
        screen.displayScoreBoard(highScore, points, lives);
    }

    /**
     * Authors: Murph
     * Handles the game ending including setting the high score
     */
    public void gameOverLogic() {
        gameOver = true;
        moving = false;
        if (points > highScore) {
            setHighScore();
        }
    }

    /**
     * Authors: Murph
     * @return this gets the high score from the file
     */
    private int getHighScore() {
        try (Scanner in = new Scanner(new File(fileName))) {
            return in.nextInt();
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * Authors: Murph
     * This writes the high score into the file
     */
    private void setHighScore() {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(points);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authors: Murph
     * This manages moving the side mover left or right
     * @param sideMover The slider or ship to be moved
     */
    protected void moveLeftAndRight(SideMover sideMover) {
        if (pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A)) {
            sideMover.moveLeft();
        }
        if (pressedKeys.contains(KeyCode.RIGHT)  || pressedKeys.contains(KeyCode.D)) {
            sideMover.moveRight();
        }
    }

    /**
     * Authors: Murph
     * Used at the beginning to make game move on click
     */
    public void startMoving() {
        moving = true;
    }

    /**
     * Authors: Murph
     * Adds all keys that are being pressed into the active keys set
     * @param code the key that is pressed
     */
    public void keyPressed(KeyCode code) {
        pressedKeys.add(code);
    }

    /**
     * Authors: Murph
     * removes released keys from the active key set
     * @param code the key that is released
     */
    public void keyReleased(KeyCode code) {
        pressedKeys.remove(code);
    }

    /**
     * Authors: Murph
     * creates a timer for clearing all the bricks and skipping a level
     */
    public void tryLevelSkip() {
        now = System.currentTimeMillis();
        if (now - lastSkip >= SKIP_COOLDOWN) {
            hittableObjects.clearObjects(screen);
            lastSkip = now;
        }
    }

    public abstract void step();
    public abstract boolean gameOn();
    public abstract void handleKeyInput();
    public abstract String getFileName();
    public abstract void resetLevel();
    public abstract boolean levelOver();

}

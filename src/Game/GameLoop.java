/*
Authors:
Murph Lennemann

 */

package Game;

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

    /**
     * Authors: Murph
     * @param screen
     */
    public GameLoop(Screen screen) {
        this.screen = screen;
        this.fileName = getFileName();
        this.highScore = getHighScore();
    }

    /**
     * Authors: Murph
     */
    public void checkLives() {
        if (lives <= 0) {
            gameOverLogic();
            screen.gameOverScreen();
        }
    }

    /**
     * Authors: Murph
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
     */
    protected void showScreen() {
        screen.displayScoreBoard(highScore, points, lives);
    }

    /**
     * Authors: Murph
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
     * @return
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
     * @param sideMover
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
     */
    public void startMoving() {
        moving = true;
    }

    /**
     * Authors: Murph
     * @param code
     */
    public void keyPressed(KeyCode code) {
        pressedKeys.add(code);
    }

    /**
     * Authors: Murph
     * @param code
     */
    public void keyReleased(KeyCode code) {
        pressedKeys.remove(code);
    }

    public abstract void step();
    public abstract boolean gameOn();
//    public abstract void handleKeyInput();
    public abstract String getFileName();
    public abstract void resetLevel();
    public abstract boolean levelOver();

}

package Game;

import Objects.*;
import Objects.Breakout.Ball;
import Objects.Breakout.Bricks;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public abstract class GameLoop {


    protected final ArrayList<Ball> BALLS;
    protected final Screen screen;
    protected ArrayList<SideMover> sideMoverList;
    protected int lives = 5;
    protected int points = 0;
    protected int highScore;
    protected int level = 1;
    protected boolean gameOver = false;
    protected final Bricks bricks;
    protected final String fileName;

    public GameLoop(Screen screen) {
        this.screen = screen;
        screen.loadLevel(level);
        this.sideMoverList = screen.getSideMoverList();
        this.BALLS = new ArrayList<>();
        this.bricks = screen.getBricks();
        this.fileName = getFileName();
        this.highScore = getHighScore();
    }

    public abstract void step();

    public void checkLives() {
        if (lives <= 0) {
            gameOverLogic();
            screen.gameOverScreen();
        }
    }
    public void checkLevel() {
        if (!levelOver()) {
            level++;
            if (level <= 3) resetLevel();
            else {
                gameOverLogic();
                screen.gameWinScreen();
            }
        }
    }

    protected void showScreen() {
        screen.displayScoreBoard(highScore, points, lives);
    }

    public abstract boolean levelOver();

    public abstract void resetLevel();

    public void gameOverLogic() {
        gameOver = true;
        if (points > highScore) {
            setHighScore();
        }
    }

    private int getHighScore() {
        try (Scanner in = new Scanner(new File(fileName))) {
            return in.nextInt();
        } catch (IOException e) {
            return 0;
        }
    }

    private void setHighScore() {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(points);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String getFileName();

    public abstract void handleKeyInput(KeyCode code);

    public void clearHittableObjects() {
        for (HittableObject hittable : bricks.getHittableObjects()) {
            screen.getRoot().getChildren().remove(hittable.getHittableObject());
        }
        bricks.getHittableObjects().clear();
    }

    public abstract void startMoving();

    public abstract boolean gameOn();
}

package Game;

import Objects.*;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public abstract class GameLoop {


    protected final ArrayList<Ball> BALLS;
    protected final Screen screen;
    protected ArrayList<Slider> sliderList;
    protected int lives = 5;
    protected int points = 0;
    protected int highScore;
    protected int level = 1;
    protected boolean movingBall = false;
    protected final double RESET_BALL_SPEED = 1;
    protected final double RESET_X_DIRECTION = 0.2;
    protected final double RESET_Y_DIRECTION = 2;
    protected boolean gameOver = false;
    protected final LevelMaker LEVEL_MAKER;
    protected final Bricks bricks;

    public GameLoop(Screen screen) {
        this.screen = screen;
        this.highScore = getHighScore();
        this.LEVEL_MAKER = screen.getLevelMaker();
        screen.loadLevel(level);
        this.sliderList = screen.getSlider();
        this.BALLS = new ArrayList<>();
        this.bricks = screen.getBricks();
    }

    public abstract void step();


    public void checkLevelAndLives() {
        if (lives == 0) {
            gameOverLogic();
            screen.gameOverScreen();
            return;
        }
        boolean hasActiveBricks = bricks.getBricks().stream()
                .anyMatch(b -> b.isActive() && !b.isUnbreakable());
        if (!hasActiveBricks) {
            level++;
            if (level <= 3) resetLevel();
            else {
                gameOverLogic();
                screen.gameWinScreen();
            }
        }
    }





    public void resetLevel() {
        movingBall = false;
        BALLS.forEach(ball -> screen.getRoot().getChildren().remove(ball.getBall()));
        BALLS.clear();
        screen.loadLevel(level);
        sliderList = screen.getSlider();
    }

    public void gameOverLogic() {
        gameOver = true;
        movingBall = false;
        if (points > highScore) setHighScore();
    }

    private int getHighScore() {
        try (Scanner in = new Scanner(new File("HighScore.txt"))) {
            return in.nextInt();
        } catch (IOException e) {
            return 0;
        }
    }

    private void setHighScore() {
        try (PrintWriter out = new PrintWriter("HighScore.txt")) {
            out.println(points);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void handleKeyInput(KeyCode code);

    public void clearHittableObjects() {
        for (HittableObject hittable : bricks.getBricks()) {
            screen.getRoot().getChildren().remove(hittable.getHittableObject());
        }
        bricks.getBricks().clear();
    }

    public void startMoving() {
        movingBall = true;
    }
}

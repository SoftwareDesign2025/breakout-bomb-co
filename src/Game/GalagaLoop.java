package Game;

import Objects.Ball;
import Objects.Slider;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;

public class GalagaLoop extends GameLoop {

    public GalagaLoop(Screen screen) {
        super(screen);
    }

    @Override
    public void step() {
        screen.displayScoreBoard(highScore, points, lives);
        if (!movingBall || gameOver) return;
        ArrayList<Ball> toRemove = new ArrayList<>();
        for (Ball ball : BALLS) {
            updateBall(ball);
            handleSliderCollisions(ball);
            screen.checkBallToWall(ball);
            points += bricks.checkBrickCollisions(ball);
            if (screen.ballOutOfBounds(ball)) toRemove.add(ball);
        }
        handleBallRemovals(toRemove);
        checkLevelAndLives();
    }

    private void updateBall(Ball ball) {
        ball.updateBallLocation();
    }

    private void handleSliderCollisions(Ball ball) {
        for (Slider slider : sliderList) {
            slider.checkSliderCollision(ball);
        }
    }

    private void handleBallRemovals(ArrayList<Ball> toRemove) {
        BALLS.addAll(screen.consumeQueuedBalls());
        for (Ball ball : toRemove) {
            screen.getRoot().getChildren().remove(ball.getBall());
            BALLS.remove(ball);
        }
        if (BALLS.isEmpty()) resetBall();
    }

    private void initBall() {
        freshBall = new Ball(10, LEVEL_MAKER.getBallX(), LEVEL_MAKER.getBallY());
        BALLS.add(freshBall);
        screen.getRoot().getChildren().add(freshBall.getBall());
        freshBall.changeSpeed(RESET_BALL_SPEED);
        freshBall.changeXDirection(RESET_X_DIRECTION);
        freshBall.changeYDirection(RESET_Y_DIRECTION);
    }

    public void resetBall() {
        movingBall = false;
        lives -= 1;
        initBall();
    }

    public void resetLevel() {
        movingBall = false;
        BALLS.forEach(ball -> screen.getRoot().getChildren().remove(ball.getBall()));
        BALLS.clear();
        screen.loadLevel(level);
        initBall();
        sliderList = screen.getSlider();
    }

    @Override
    public void handleKeyInput(KeyCode code) {
        if (!gameOver) {
            for (Slider slider : sliderList) {
                slider.handleMovement(code);
            }
        }
        if (code == KeyCode.B) {
            clearHittableObjects();
        }
    }
}

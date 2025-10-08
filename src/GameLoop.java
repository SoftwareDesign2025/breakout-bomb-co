import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class GameLoop {
	private Ball ball;
	private Screen screen;
	private Slider slider;
	private int lives = 3;
	private int points = 0;
	private boolean movingBall = false;
	private final double RESET_BALL_SPEED = 1;
    private final double RESET_X_DIRECTION = 0.2;
    private  final double RESET_Y_DIRECTION = 2;
    private boolean gameOver = false;
	
	public GameLoop(Ball ball, Slider slider, Screen screen) {
        this.ball = ball;
        this.slider = slider;
        this.screen = screen;
	}
	
	public void handleKeyInput(KeyCode code) {
		if (!gameOver) {
			slider.handleMovement(code);
		}
    }
	
	public void step(double elapsedTime) {
		if (movingBall && !gameOver) {
			ball.updateBallLocation();
			slider.checkSliderCollision(ball);
			screen.checkBallToWall(ball);
			points += screen.checkBrickCollisions(ball);
			if (screen.ballOutOfBounds(ball)) {
				resetBall();
			}
			screen.displayScoreBoard(points, lives);
			if (lives == 0) {
				gameOverLogic();
				screen.gameOverScreen();
			}
			int activeCount = 0;
			for (Brick brick: screen.getBricks()) {
				if (brick.isBrickActive()) {
					activeCount++;
					break;
				}
			}
			if (activeCount == 0) {
				gameOverLogic();
				screen.gameWinScreen();
			}
		}
		
	}
	
	public void startMoving() {
		movingBall = true;
	}
	
	public void resetBall() {
		movingBall = false;
		lives -= 1;
		ball.getBall().setCenterX(400);
	    ball.getBall().setCenterY(400);
	    ball.changeSpeed(RESET_BALL_SPEED);
	    ball.changeXDirection(RESET_X_DIRECTION);
	    ball.changeYDirection(RESET_Y_DIRECTION);
		
	}
	
	public void gameOverLogic()  {
		gameOver = true;
		movingBall = false;
	}


}
        
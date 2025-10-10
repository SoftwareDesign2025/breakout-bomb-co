import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GameLoop {
	private Ball ball;
	private Screen screen;
	private Slider slider;
	private int lives = 3;
	private int points = 0;
	private int highScore;
	private List<PowerUp> powerUpList;
	private boolean movingBall = false;
	private final double RESET_BALL_SPEED = 1;
    private final double RESET_X_DIRECTION = 0.2;
    private  final double RESET_Y_DIRECTION = 2;
    private boolean gameOver = false;
    private static final String HIGH_SCORE_FILE = "HighScore.txt";
  
	
	public GameLoop(Ball ball, Slider slider, Screen screen, List<PowerUp> powerUpList) {
        this.ball = ball;
        this.slider = slider;
        this.screen = screen;
        this.highScore = getHighScore();
        this.powerUpList = powerUpList;
	}
	
	public void handleKeyInput(KeyCode code) {
		if (!gameOver) {
			slider.handleMovement(code);
		}
    }
	
	public void step(double elapsedTime) {
		screen.displayScoreBoard(highScore, points, lives);
		if (movingBall && !gameOver) {
			ball.updateBallLocation();
			slider.checkSliderCollision(ball);
			screen.checkBallToWall(ball);
			int newPoints = screen.checkBrickCollisions(ball);
			points += newPoints;
			if (newPoints > 0) {
				PowerUp pu = new PowerUp(0,0).maybeDropPowerUp(ball.getX(), ball.getY());
				if (pu != null) {
					powerUpList.add(pu);
					screen.getRoot().getChildren().add(pu.getNode());
				}
			}
			if (!powerUpList.isEmpty()) {
				for (PowerUp powerUp: powerUpList) {
					powerUp.update_position();
				}
				slider.checkPowerUpCollision(powerUpList, screen);
			}
			if (screen.ballOutOfBounds(ball)) {
				resetBall();
			}
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
		if (points > highScore) {
			setHighScore();
		}
	}
	
	private int getHighScore() {
		try (Scanner in = new Scanner(new File(HIGH_SCORE_FILE))) {
		    highScore = in.nextInt();
		} catch (IOException e) {
		    highScore = 0;
		}
		return highScore;
	}

	
	public void setHighScore() {
		try (PrintWriter out = new PrintWriter(new File(HIGH_SCORE_FILE))) {
	        out.println(points);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
        
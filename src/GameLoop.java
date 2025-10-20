import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameLoop {
	private Ball ball;
	private Screen screen;
	private ArrayList<Slider> sliderList;
	private ArrayList<PowerUp> powerUpList;
	private int lives = 3;
	private int points = 0;
	private int highScore;
	private boolean movingBall = false;
	private final double RESET_BALL_SPEED = 1;
    private final double RESET_X_DIRECTION = 0.2;
    private  final double RESET_Y_DIRECTION = 2;
    private boolean gameOver = false;
	
	public GameLoop(Ball ball, Screen screen) {
        this.ball = ball;
        this.screen = screen;
        this.highScore = getHighScore();
        LevelMaker levelMaker = new LevelMaker(screen.getRoot(), screen.getBricks());
        this.sliderList = screen.getSlider();
	}
	
	public void handleKeyInput(KeyCode code) {
		if (!gameOver) {
			for (Slider slider: sliderList) {
				slider.handleMovement(code);
			}
		}
    }
	
	public void step(double elapsedTime) {
		screen.displayScoreBoard(highScore, points, lives);
		
		if (movingBall && !gameOver) {
			ball.updateBallLocation();
			for (Slider slider: sliderList)
			slider.checkSliderCollision(ball);
			screen.checkBallToWall(ball);
			ArrayList<Brick> contactList = screen.checkBrickCollisions(ball);
			for (Brick contact: contactList) {
				points += 1;
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
		try (Scanner in = new Scanner(new File("HighScore.txt"))) {
		    highScore = in.nextInt();
		} catch (IOException e) {
		    highScore = 0;
		}
		return highScore;
	}

	
	public void setHighScore() {
	    try (PrintWriter out = new PrintWriter("HighScore.txt")) {
	        out.println(points);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
        
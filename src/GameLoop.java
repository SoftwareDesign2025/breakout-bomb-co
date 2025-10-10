import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class GameLoop {
	private Ball ball;
	private Screen screen;
	private Slider slider;
	private PowerUp powerUp;
	private int lives = 3;
	private int points = 0;
	private int highScore;
	private boolean movingBall = false;
	private final double RESET_BALL_SPEED = 1;
    private final double RESET_X_DIRECTION = 0.2;
    private  final double RESET_Y_DIRECTION = 2;
    private boolean gameOver = false;
    private final List<PowerUp> activePowerUps = new ArrayList<>();

  
	
	public GameLoop(Ball ball, Slider slider, Screen screen, PowerUp powerUp) {
        this.ball = ball;
        this.slider = slider;
        this.screen = screen;
        this.highScore = getHighScore();
        this.powerUp = powerUp;
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
			int gained = screen.checkBrickCollisions(ball);
			if (gained > 0) {
			    points += gained;

			    // Spawn chance happens only when a brick was just broken.
			    double bx = ball.getBall().getCenterX();
			    double by = ball.getBall().getCenterY();

			    PowerUp spawned = powerUp.maybeDropPowerUp(bx, by); // uses your spawner instance
			    if (spawned != null) {
			        screen.getRoot().getChildren().add(spawned.getNode());
			        activePowerUps.add(spawned);
			    }
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
			// Update all falling power-ups and clean up any that fall off-screen
			for (int i = activePowerUps.size() - 1; i >= 0; i--) {
			    PowerUp pu = activePowerUps.get(i);
			    pu.update_position();

			    // remove if off the bottom of the screen (600 tall)
			    if (pu.getNode().getBoundsInParent().getMinY() > 600) {
			        screen.getRoot().getChildren().remove(pu.getNode());
			        activePowerUps.remove(i);
			    }
			}
			slider.checkPowerUpCollision(activePowerUps, screen);

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
		try (Scanner in = new Scanner(new File("/Users/alennemann/git/breakout-bomb-co/src/HighScore.txt"))) {
		    highScore = in.nextInt();
		} catch (IOException e) {
		    highScore = 0;
		}
		return highScore;
	}

	
	public void setHighScore() {
	    try (PrintWriter out = new PrintWriter("/Users/alennemann/git/breakout-bomb-co/src/HighScore.txt")) {
	        out.println(points);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
        
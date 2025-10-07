import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class GameLoop {
	private Ball ball;
	private Screen screen;
	private Slider slider;
	private int lives = 3;
	private int points = 0;
	private boolean movingBall = false;
	
	public GameLoop(Ball ball, Slider slider, Screen screen) {
        this.ball = ball;
        this.slider = slider;
        this.screen = screen;
	}
	
	public void handleKeyInput(KeyCode code) {
        slider.handleMovement(code);
    }
	
	public void step(double elapsedTime) {
		if (movingBall) {
			ball.updateBallLocation();
			slider.checkSliderCollision(ball);
			screen.checkBallToWall();
		}
	}
	
	public void startMoving() {
		movingBall = true;
	}
	
	public void resetBall() {
		movingBall = true;
	}
}
        
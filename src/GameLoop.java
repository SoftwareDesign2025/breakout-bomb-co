import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class GameLoop {
	Ball ball;
	Screen screen;
	Slider slider;
	
	public GameLoop(Ball ball, Slider slider, Screen screen) {
        this.ball = ball;
        this.slider = slider;
        this.screen = screen;
	}
	
	public void handleKeyInput(KeyCode code) {
        slider.handleMovement(code);
    }
	
	public void step(double elapsedTime) {
		ball.updateBallLocation();
		screen.checkBallToWall();
		slider.checkSliderCollision(ball);
		
		
	}
}
        
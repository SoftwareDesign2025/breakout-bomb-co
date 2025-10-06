import javafx.stage.Stage;

public class GameLoop {
	Ball ball;
	Screen screen;
	Slider slider;
	
	public GameLoop(Ball ball, Slider slider, Screen screen) {
        this.ball = ball;
        this.slider = slider;
        this.screen = screen;
	}
	
	public void step(double elapsedTime) {
		ball.updateBallLocation();
		
	}
}
        
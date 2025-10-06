import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
	private Ball ball;
	private Slider paddle;
	private Screen screen;
	
	public GameLoop(Ball ball, Slider slider, Screen screen) {
		this.ball = ball;
		this.slider = slider;
		this.screen = screen;
	}
	
	@Override
	public void handle(long now) {
		ball.UpdateBallLocation
		
	}
}

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Sphere;

public class GameLoop extends AnimationTimer {
	private Sphere ball;
	private Rectangle slider;
	private Screen screen;
	
	public GameLoop(Sphere ball, Rectangle slider, Screen screen) {
		this.ball = ball;
		this.slider = slider;
		this.screen = screen;
	}
	
	@Override
	public void handle(long now) {
		
	}
}

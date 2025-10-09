/*

//Author Ben farmer
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


/*this class is to implement power ups*/
public class PowerUp {
	
}
	private static double FALL_SPEED = 7.0;
	private static double WIDTH = 7.0;
	private static double HEIGHT = 7.0;
	//private static COLOR = ;
	private static double DROP_RATE= .1;
	private final Circle powerUp;
	private boolean activated = false;
	//change
	private int xpos;
	private int ypos;
	
	protected PowerUp(double x, double y) {
		powerUp = new Circle(WIDTH, Color.POWDERBLUE);
		powerUp.setCenterX(x);		
        powerUp.setCenterY(y);
        xpos = (int)x;
        ypos = (int)y;
	}
	
	
	
	//if brick is destroyed, drop power up DROP_RATE of the time
	private  dropPowerUp(double x, double y) {
		double chance = Math.random();
		if(chance< DROP_RATE) {
			return new BiggerSlider(x,y);
		}
		return null;
		
	}
	
	public void powerUpSelect() {
		
	}
	
	public void update_position() {
		//powerUp.setCenterX(powerUp.getCenterX());
		powerUp.setCenterY(powerUp.getCenterY()+FALL_SPEED);
		// xpos = (int) powerUp.getCenterX();
	     //ypos= (int) powerUp.getCenterY();
	}
	//checks if slider and power up collide
	public void checkCollision(Slider slider, Runnable onConsume) {
		Node paddle = slider.getNode();//confused here
		 if (paddle.getBoundsInParent().intersects(powerUp.getBoundsInParent())) {
			 if (!activated) { 		//only activate once
				 activated = true;
				 startEffect(slider); 
				 if (onConsume != null) onConsume.run();
			 }
		 }
	}
	
	abstract void startEffect(Slider slider);
	
	void runTimed(double seconds, Runnable start, Runnable end) {
		start.run();
		
		
	}
	//helper functions
	boolean isactivated() {
		return activated;
	}
	Circle getNode() { 
		return powerUp;
		}
	
}


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
	

	private static double FALL_SPEED = 1.5;
	private static double WIDTH = 7.0;
	private static double HEIGHT = 7.0;
	//private static COLOR = ;
	private static double DROP_RATE= .1;
	private final Circle powerUp;
	private boolean activated = false;
	//change
	private int xpos;
	private int ypos;
	
	private int countdownFrames = -1;
	
	PowerUp(double x, double y) {
		powerUp = new Circle(WIDTH, Color.POWDERBLUE);
		powerUp.setCenterX(x);		
        powerUp.setCenterY(y);
        xpos = (int)x;
        ypos = (int)y;
	}
	
	
	
	//if brick is destroyed, drop power up DROP_RATE of the time
	public PowerUp maybeDropPowerUp(double x, double y) {
		double chance = Math.random();
		if(chance< DROP_RATE) {
			return new BiggerSlider(x,y);
		}
		return null;
		
	}
	
	
	
	public void powerUpSelect() {
		//placeholder for different powerups later
	}
	
	public void update_position() {
		//powerUp.setCenterX(powerUp.getCenterX());
		if (powerUp == null) {
			return;
		}
		powerUp.setCenterY(powerUp.getCenterY()+FALL_SPEED);
		xpos = (int) powerUp.getCenterX();
	    ypos= (int) powerUp.getCenterY();
	    
	    if (countdownFrames > 0) {
	    	countdownFrames = updatePowerUpCountdown(countdownFrames);
	    }
	}
	//checks if slider and power up collide
	/*
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
	*/
	
	// your team calls this when THEY detect a pickup
    void onPickup(Slider slider) {
        if (activated) return;
        activated = true;
        startEffect(slider);   // <-- base declares this so subclasses can override
    }
	
	public int updatePowerUpCountdown(int countdown) {
		countdown -= 1;
		if (countdown <= 1) {
			stopPowerUp();
		}
		return countdown;
	}
	
	// declare these so subclasses can override ======
    void startEffect(Slider slider) {
        // base no-op; BiggerSlider overrides
    }

	public void stopPowerUp() {
		activated = false;
		countdownFrames = -1;
	}
	
	// allow subclass to start a countdown (frames)
    void beginCountdown(int frames) { 
    	countdownFrames = frames; 
    	}
	
	
	//helper functions
	boolean isactivated() {
		return activated;
	}
	Circle getNode() { 
		return powerUp;
		}
}
	



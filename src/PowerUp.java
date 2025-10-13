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

	private static double DROP_RATE= .3;

	private final Circle powerUp;
	private boolean activated = false;
	public boolean finished = false;
	//change
	private int xpos;
	private int ypos;
	
	private int countdownFrames = -1;
	
	public PowerUp(double x, double y) {
		powerUp = new Circle(WIDTH, Color.POWDERBLUE);
		powerUp.setCenterX(x);		
        powerUp.setCenterY(y);
        xpos = (int)x;
        ypos = (int)y;
	}
	
	
	
	//if brick is destroyed, drop power up DROP_RATE of the time
	public PowerUp maybeDropPowerUp(double x, double y) {
		double chance = Math.random()*20;
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
		if (!activated) {
		powerUp.setCenterY(powerUp.getCenterY()+FALL_SPEED);
		xpos = (int) powerUp.getCenterX();
	    ypos= (int) powerUp.getCenterY();
	    
	    if (countdownFrames > 0) {
	    	countdownFrames = updatePowerUpCountdown(countdownFrames);
	    }
	    
		}
	}
	
	// your team calls this when THEY detect a pickup
    public void onPickup(Slider slider) {
        if (activated) return;
        activated = true;
        startEffect(slider);   // <-- base declares this so subclasses can override
    }
	
	public int updatePowerUpCountdown(int countdown) {
		countdown --;
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
	boolean isPowerUpOver() {
		return finished;
	}
	Circle getNode() { 
		return powerUp;
		}
}
	



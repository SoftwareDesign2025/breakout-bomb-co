//Author Ben farmer
package Powerups;

import java.util.ArrayList;
import java.util.List;

import Objects.Slider;
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
	
	
	
	/*
	 * original design for unstructured levels
	//if brick is destroyed, drop power up DROP_RATE of the time
	public PowerUp maybeDropPowerUp(double x, double y) {
		double chance = Math.random()*20;
		if(chance< DROP_RATE) {
			return new BiggerSlider(x,y);
		}
		return null;
		
	}
	
	*/
	
	//new powerup design for structured levels
	
	
	public void update_position() {
		if (activated) 
			return;
		powerUp.setCenterY(powerUp.getCenterY()+FALL_SPEED);
		xpos = (int) powerUp.getCenterX();
	    ypos= (int) powerUp.getCenterY();
	}
	
	// your team calls this when THEY detect a pickup
    public void onPickup(ArrayList<Slider> sliders) {
        if (activated) return;
        activated = true;
        startEffect(sliders);   // <-- base declares this so subclasses can override
    }
    
	// declare these so subclasses can override ======
    void startEffect(ArrayList<Slider> sliders) {
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
	public boolean isactivated() {
		return activated;
	}
	 public boolean isPowerUpOver() {
		return finished;
	}
	public Circle getNode() { 
		return powerUp;
		}



	public PowerUp spawnAt(double x, double y) {
		 
		return new PowerUp(x,y);
	}
}
	



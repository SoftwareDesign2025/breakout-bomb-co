/*
Authors:
Ben Farmer
Murph Lennemann

 */

package Powerups;

import java.util.ArrayList;
import Game.Breakout.BreakoutScreen;
import Objects.Breakout.Ball;
import Objects.Breakout.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


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

	/**
	 * Authors:
	 * @param x
	 * @param y
	 */
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

	/**
	 * Authors:
	 */
	public void update_position() {
		if (activated) 
			return;
		powerUp.setCenterY(powerUp.getCenterY()+FALL_SPEED);
		xpos = (int) powerUp.getCenterX();
	    ypos= (int) powerUp.getCenterY();
	}


	/**
	 * Authors:
	 * @param sliders
	 */
    public void onPickup(ArrayList<Slider> sliders) {
        if (activated) return;
        activated = true;
        startEffect(sliders);   // <-- base declares this so subclasses can override
    }

	/**
	 * Authors:
	 * @param sliders
	 */
    void startEffect(ArrayList<Slider> sliders) {
        // base no-op; BiggerSlider overrides
    }

	/**
	 * Authors:
	 */
	public void stopPowerUp() {
		activated = false;
		countdownFrames = -1;
	}

	/**
	 * Authors
	 * @param frames
	 */
    void beginCountdown(int frames) { 
    	countdownFrames = frames; 
    	}


	/**
	 * Authors:
	 * @return
	 */
	public boolean isactivated() {
		return activated;
	}

	/**
	 * Authors:
	 * @return
	 */
	 public boolean isPowerUpOver() {
		return finished;
	}

	/**
	 * Authors:
	 * @return
	 */
	public Circle getNode() { 
		return powerUp;
		}


	/**
	 * Authors:
	 * @param x
	 * @param y
	 * @return
	 */
	public PowerUp spawnAt(double x, double y) {
		return new PowerUp(x,y);
	}

	public void onSpawn(BreakoutScreen screen, ArrayList<Ball> balls) {}

	public void tick() {}

}
	



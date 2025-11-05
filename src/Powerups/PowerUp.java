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
	 * Authors:Farmer
	 * @param x
	 * @param y
	 */
	public PowerUp(double x, double y) {
		powerUp = new Circle(WIDTH, Color.POWDERBLUE);
		powerUp.setCenterX(x);		
        powerUp.setCenterY(y);
        
	}
	
	
	
	
	
	/**
	 * Authors:Farmer
	 */
	public void update_position() {
		if (activated) 
			return;
		powerUp.setCenterY(powerUp.getCenterY()+FALL_SPEED);
		
	}


	/**
	 * Authors:Farmer
	 * @param sliders
	 */
    public void onPickup(ArrayList<Slider> sliders) {
        if (activated) return;
        activated = true;
        startEffect(sliders);   // <-- base declares this so subclasses can override
    }

	/**
	 * Authors:Farmer
	 * @param sliders
	 */
    void startEffect(ArrayList<Slider> sliders) {
        // BiggerSlider overrides
    }

	/**
	 * Authors:Farmer
	 */
	public void stopPowerUp() {
		activated = false;
		countdownFrames = -1;
	}

	/**
	 * Authors: Farmer
	 * @param frames
	 */
    void beginCountdown(int frames) { 
    	countdownFrames = frames; 
    	}


	/**
	 * Authors:Farmer
	 * @return
	 */
	public boolean isactivated() {
		return activated;
	}

	/**
	 * Authors:Farmer
	 * @return
	 */
	 public boolean isPowerUpOver() {
		return finished;
	}

	/**
	 * Authors:Farmer
	 * @return
	 */
	public Circle getNode() { 
		return powerUp;
		}


	/**
	 * Authors:Farmer
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
	



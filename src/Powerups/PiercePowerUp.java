/*
Authors:
Ben Farmer

 */

package Powerups;

import Objects.Breakout.Ball;
import Objects.Breakout.Slider;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PiercePowerUp extends PowerUp {
    private static int charges = 0;          // how many uses the player has
    private static int framesLeft = 0;       // active pierce time remaining
    private static final int PIERCE_FRAMES = 60; // ~1s at 60 FPS
    private Ball ball;
    private boolean finished = false;

    /**
     * Authors:
     * @param x
     * @param y
     */
    public PiercePowerUp(double x, double y) {
        super(x, y);
        getNode().setFill(Color.ORANGERED);
    }

    /**
     * Authors:
     * @param sliders
     */
    @Override
    void startEffect(ArrayList<Slider> sliders) {
        charges++;          // pickup grants a charge
        finished = true;    // this falling object can be removed
        super.stopPowerUp();
    }

    /**
     * Authors:
     */
    public static void tryActivate() {
        if (charges > 0 && framesLeft == 0) {
            charges--;
            framesLeft = PIERCE_FRAMES;
        }
    }

    /**
     * Authors:
     * called once per frame
     */
    public static void tickGlobal() {
        if (framesLeft > 0) framesLeft--;
    }

    /**
     * Authors:
     * Brick uses this to decide wether to skip the bounce
     * @return
     */
    public static boolean isActive() {
        return framesLeft > 0;
    }

    /**
     * Authors:
     * @return
     */
    public boolean isPowerUpOver() { return finished; }

    /**
     * Authors:
     * @param ball
     */
	public void setBall(Ball ball) {
		this.ball = ball;
	}

    /**
     * Authors:
     * @param x
     * @param y
     * @return
     */
	@Override public PowerUp spawnAt(double x, double y) {
		return new PiercePowerUp(x, y); 
		}
}

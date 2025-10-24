package Powerups;
import Objects.Ball;
import Objects.Slider;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PiercePowerUp extends PowerUp {
    private static int charges = 0;          // how many uses the player has
    private static int framesLeft = 0;       // active pierce time remaining
    private static final int PIERCE_FRAMES = 60; // ~1s at 60 FPS
    private Ball ball;
    private boolean finished = false;

    public PiercePowerUp(double x, double y) {
        super(x, y);
        getNode().setFill(Color.ORANGERED);
    }

    @Override
    void startEffect(ArrayList<Slider> sliders) {
        charges++;          // pickup grants a charge
        finished = true;    // this falling object can be removed
        super.stopPowerUp();
    }

    // called from your input handler (e.g., SPACE key)
    public static void tryActivate() {
        if (charges > 0 && framesLeft == 0) {
            charges--;
            framesLeft = PIERCE_FRAMES;
        }
    }

    // called once per frame
    public static void tickGlobal() {
        if (framesLeft > 0) framesLeft--;
    }

    // Brick uses this to decide whether to skip the bounce
    public static boolean isActive() {
        return framesLeft > 0;
    }

    public boolean isPowerUpOver() { return finished; }

	public void setBall(Ball ball) {
		this.ball = ball;
		
	}
	
	@Override public PowerUp spawnAt(double x, double y) {
		return new PiercePowerUp(x, y); 
		}
}

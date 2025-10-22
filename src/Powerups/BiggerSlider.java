package Powerups;

import Objects.Slider;
import javafx.scene.Node;

public class BiggerSlider extends PowerUp {
    private final double BIG_SIZE = 1.5;      // bigger state
    private final int DURATION_FRAMES = 600;  // ~5s @ 60 FPS

    private Slider slider;
    private boolean active = false;   // currently big?
    private int framesLeft = 0;
    private boolean over = false;

    public BiggerSlider(double x, double y) { super(x, y); }

    @Override
    void startEffect(Slider slider) {
        this.slider = slider;
        Node paddle = slider.getNode();

        if (!active) {
            paddle.setScaleX(BIG_SIZE); // first time: go big
            active = true;
        }
        // overlap pickup: just reset the same timer
        framesLeft = DURATION_FRAMES;
        over = false;
    }

    public void tick() {
        if (!active) return;
        if (framesLeft > 0) {
            framesLeft--;
            if (framesLeft == 0) {
                stopPowerUp();
            }
        }
    }
    @Override public PowerUp spawnAt(double x, double y) { 
    	return new BiggerSlider(x, y); 
    	}
    
    @Override
    public void stopPowerUp() {
        if (!active) return;
        active = false;
        if (slider != null) {
            slider.getNode().setScaleX(1.0); // back to normal
        }
        over = true;
        super.stopPowerUp();
    }

    public boolean isPowerUpOver() { return over; }
}

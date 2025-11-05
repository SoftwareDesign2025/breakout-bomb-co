/*
Authors:
Ben Farmer

 */

package Powerups;

import Objects.Breakout.Slider;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BiggerSlider extends PowerUp {
    private final double BIG_SIZE = 1.5;      // bigger state
    private final int DURATION_FRAMES = 600;  // ~5s @ 60 FPS
    private ArrayList<Slider> sliders;
    private boolean active = false;   // currently big?
    private int framesLeft = 0;
    private boolean over = false;

    /**
     * Authors:Farmer
     * @param x
     * @param y
     */
    public BiggerSlider(double x, double y) {
        super(x, y);
        getNode().setFill(Color.HOTPINK);
    }

    /**
     * Authors:Farmer
     * @param sliders
     */
    @Override
    void startEffect(ArrayList<Slider> sliders) {
        this.sliders = (sliders != null) ? sliders : new ArrayList<>();
        if (this.sliders.isEmpty()) return;

        if (active) {
            framesLeft = DURATION_FRAMES;
            over = false;
            return;
        }

        active = true;
        for (Slider slider : this.sliders) {
            slider.getNode().setScaleX(BIG_SIZE);
        }
        framesLeft = DURATION_FRAMES;
        over = false;
    }

    /**
     * Authors:Farmer
     */
    @Override
    public void tick() {
        if (!active) return;
        if (framesLeft > 0) {
            framesLeft--;
            if (framesLeft == 0) {
                stopPowerUp();
            }
        }
    }

    /**
     * Authors:Farmer
     * @param x
     * @param y
     * @return
     */
    @Override public PowerUp spawnAt(double x, double y) { 
    	return new BiggerSlider(x, y); 
    	}

    /**
     * Authors:Farmer
     */
    @Override
    public void stopPowerUp() {
        if (!active) return;
        active = false;
        for (Slider slider : sliders) {
            if (slider != null) {
                slider.getNode().setScaleX(1.0);
            }
        }
        over = true;
        super.stopPowerUp();
    }

    /**
     * Authors:Farmer
     * @return
     */
    public boolean isPowerUpOver() { return over; }
}

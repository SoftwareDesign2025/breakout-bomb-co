// BiggerSlider.java
import javafx.scene.Node;

class BiggerSlider extends PowerUp {
    // widen factor and duration (in frames)
    private double bigScale = 1.5;          // 50% wider
    private double originalScale = 1.0;
    private int durationFrames = 300;     // ~5s at 60 FPS

    // to restore later
    private Slider slider;
    boolean active = false;
    private int framesLeft = 0;
    private boolean powerUpOver = false; //

    BiggerSlider(double x, double y) {
        super(x, y);
    }

    @Override
    void startEffect(Slider slider) {
        this.slider = slider;
        Node paddle = slider.getNode();

        if (!active) {
        	//make the slider bigger
        	paddle.setScaleX(bigScale);
        	active = true;
        }
        framesLeft = durationFrames;
        powerUpOver = false;
    }
    
    void tick() {
    	if(!active) return;
    	if (framesLeft > 0) {
    		framesLeft--;
    		}
    	if (framesLeft < 1) {
    		stopPowerUp();
    	}
    }

    @Override
    public void stopPowerUp() {
        // restore the paddle scale if we had applied it
        if (!active) return;
        active = false;
        if (slider != null) {
            slider.getNode().setScaleX(originalScale);
        }
        powerUpOver = true;
        super.stopPowerUp();
    }
    boolean isPowerUpOver(){
    	return powerUpOver;
    }

    // optional tuning if you need it from elsewhere
    
    void setDurationFrames(int f)    { durationFrames = f; }
}

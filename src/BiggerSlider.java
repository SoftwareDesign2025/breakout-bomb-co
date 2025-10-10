// BiggerSlider.java
import javafx.scene.Node;

class BiggerSlider extends PowerUp {
    // widen factor and duration (in frames)
    private double factor = 1.5;          // 50% wider
    private int durationFrames = 600;     // ~10s at 60 FPS

    // to restore later
    private Slider slider;
    private double originalScaleX = 1.0;

    BiggerSlider(double x, double y) {
        super(x, y);
    }

    @Override
    void startEffect(Slider slider) {
        this.slider = slider;
        Node paddle = slider.getNode();

        // current scale factor
        double currentScale = paddle.getScaleX();
        originalScaleX = currentScale;

        // only increase if below max (1.5× original size)
        double targetScale = Math.min(currentScale * factor, 1.5);

        paddle.setScaleX(targetScale);

        beginCountdown(durationFrames);
    }

    @Override
    public void stopPowerUp() {
        // restore the paddle scale if we had applied it
        if (slider != null) {
            slider.getNode().setScaleX(originalScaleX);
        }
        
        super.stopPowerUp();
    }

    // optional tuning if you need it from elsewhere
    void setFactor(double f)         { factor = f; }
    void setDurationFrames(int f)    { durationFrames = f; }
}

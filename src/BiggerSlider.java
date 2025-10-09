// BiggerSlider.java
import javafx.scene.Node;

class BiggerSlider extends PowerUp {
    private double factor = 1.5;     // 50% wider
    private double duration = 10.0;  // seconds

    BiggerSlider(double x, double y) {
        super(x, y);
    }

    @Override
    void startEffect(Slider slider) {
        Node paddle = slider.getNode();
        final double oldScaleX = paddle.getScaleX();

        // scale visually in X (keeps your edge math untouched)
        runTimed(duration,
            () -> paddle.setScaleX(oldScaleX * factor),
            () -> paddle.setScaleX(oldScaleX)
        );
    }

    // optional tuning
    void setFactor(double f)   { factor = f; }
    void setDuration(double s) { duration = s; }
}

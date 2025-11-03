package Objects.Breakout;

import Objects.SideMover;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Slider extends SideMover {

    private final Rectangle slider;
    public static final double HEIGHT = 20;

    public Slider(double startX, double startY) {
        super(startX, startY, 80, 20);
        slider = new Rectangle(xLocation, yLocation, width, HEIGHT);
        slider.setFill(Color.BLACK);
    }

    @Override
    protected void stopAtEdges() {
        if (xLocation < 0) xLocation = 0;
        else if (xLocation + width > 800) xLocation = 800 - width;
    }

    @Override
    protected void updateNode() {
        slider.setX(xLocation);
    }

    public Rectangle getNode() {
        return slider;
    }

    public void checkSliderCollision(Ball ball) {
        if (ball.getBall().getBoundsInParent().intersects(slider.getBoundsInParent())) {
            double sliderX = slider.getX();
            double sliderWidth = slider.getWidth();
            double ballX = ball.getBall().getCenterX();
            double angleMath = (sliderX + sliderWidth / 2) - ballX;
            ball.changeXDirection(angleMath / -20);
            ball.reverseYDirection();
            ball.increaseSpeed();
        }
    }
}

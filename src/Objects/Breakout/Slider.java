/*
Authors:

 */

package Objects.Breakout;

import Objects.SideMover;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Slider extends SideMover {

    private final Rectangle slider;
    public static final double HEIGHT = 20;
    private final double startX;
    private final double startY;
    private final double baseWidth;

    /**
     * Authors:
     * @param startX
     * @param startY
     */
    public Slider(double startX, double startY) {
        super(startX, startY, 80, 5);
        this.startX = startX;
        this.startY = startY;
        this.baseWidth = 80;
        slider = new Rectangle(xLocation, yLocation, width, HEIGHT);
        slider.setFill(Color.BLACK);
    }

    /**
     * Authors:
     */
    @Override
    protected void stopAtEdges() {
        if (xLocation < 0) xLocation = 0;
        else if (xLocation + width > 800) xLocation = 800 - width;
    }

    /**
     * Authors:
     */
    @Override
    protected void updateNode() {
        slider.setX(xLocation);
    }

    /**
     * Authors:
     * @return
     */
    public Rectangle getNode() {
        return slider;
    }

    /**
     * Authors:
     * @param ball
     */
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

    /**
     * Authors:
     */
    public void resetSlider() {
        xLocation = startX;
        yLocation = startY;
        slider.setX(xLocation);
        slider.setY(yLocation);
        slider.setWidth(baseWidth);
    }
}

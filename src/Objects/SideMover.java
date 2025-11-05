/*
Authors:


 */

package Objects;

public abstract class SideMover {
    protected double xLocation;
    protected double yLocation;
    protected final double width;
    protected final double speed;

    /**
     * Authors: Gavin
     * @param startX
     * @param startY
     * @param width
     * @param speed
     */
    public SideMover(double startX, double startY, double width, double speed) {
        this.xLocation = startX;
        this.yLocation = startY;
        this.width = width;
        this.speed = speed;
    }

    /**
     * Authors: Gavin
     */
    public void moveLeft() {
        moveSideToSide(true);
    }

    /**
     * Authors: Gavin
     */
    public void moveRight() {
        moveSideToSide(false);
    }

    /**
     * Authors: Gavin
     * @param goLeft
     */
    public void moveSideToSide(boolean goLeft) {
        if (goLeft) xLocation -= speed;
        else xLocation += speed;
        stopAtEdges();
        updateNode();
    }

    protected abstract void stopAtEdges();
    protected abstract void updateNode();
}

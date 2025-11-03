package Objects;

import javafx.scene.input.KeyCode;

public abstract class SideMover {
    protected double xLocation;
    protected double yLocation;
    protected final double width;
    protected final double speed;

    public SideMover(double startX, double startY, double width, double speed) {
        this.xLocation = startX;
        this.yLocation = startY;
        this.width = width;
        this.speed = speed;
    }

    public void moveLeft() {
        moveSideToSide(true);
    }

    public void moveRight() {
        moveSideToSide(false);
    }

    public void moveSideToSide(boolean goLeft) {
        if (goLeft) xLocation -= speed;
        else xLocation += speed;
        stopAtEdges();
        updateNode();
    }

    protected abstract void stopAtEdges();
    protected abstract void updateNode();
}

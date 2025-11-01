package Objects;

import javafx.scene.input.KeyCode;

public abstract class SideMover {
    protected double xLocation;
    protected final double yLocation;
    protected final double width;
    protected final double speed;

    public SideMover(double startX, double startY, double width, double speed) {
        this.xLocation = startX;
        this.yLocation = startY;
        this.width = width;
        this.speed = speed;
    }

    public void handleMovement(KeyCode code) {
        if (code == KeyCode.LEFT || code == KeyCode.A) moveSideToSide(true);
        else if (code == KeyCode.RIGHT || code == KeyCode.D) moveSideToSide(false);
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

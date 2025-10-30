package Objects;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ship {
    public static final double SPEED = 20.0;
    public static final double WIDTH = 40;
    public static final double HEIGHT = 40;

    private double xLocation;
    private double yLocation;
    private Polygon ship;

    public Ship(double startX, double startY) {
        this.xLocation = startX;
        this.yLocation = startY;
        ship = new Polygon();
        ship.setFill(Color.DEEPSKYBLUE);
        ship.setStroke(Color.WHITE);
        ship.setStrokeWidth(2);
    }

    public void handleMovement(KeyCode code) {
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            moveSideToSide(true);
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            moveSideToSide(false);
        }
    }

    public void moveSideToSide(boolean goLeft) {
        if (goLeft) {
            xLocation -= SPEED;
        } else {
            xLocation += SPEED;
        }
        stopAtEdges(400);
    }

    private void stopAtEdges(double screenWidth) {
        if (xLocation < WIDTH / 2) {
            xLocation = WIDTH / 2;
        } else if (xLocation + WIDTH / 2 > screenWidth) {
            xLocation = screenWidth - WIDTH / 2;
        }
        double offsetX = xLocation - ship.getLayoutBounds().getCenterX();
        ship.setTranslateX(offsetX);
    }

    public Polygon getNode() {
        return ship;
    }
}

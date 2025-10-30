package Objects;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ship extends SideMover {

    public static final double HEIGHT = 40;
    private final Polygon ship;

    public Ship(double startX, double startY) {
        super(startX, startY, 40, 20);
        ship = new Polygon();
        ship.getPoints().addAll(
                xLocation, yLocation - HEIGHT / 2,
                xLocation - width / 2, yLocation + HEIGHT / 2,
                xLocation + width / 2, yLocation + HEIGHT / 2
        );
        ship.setFill(Color.DEEPSKYBLUE);
        ship.setStroke(Color.WHITE);
        ship.setStrokeWidth(2);
    }

    @Override
    protected void stopAtEdges() {
        if (xLocation < width / 2) xLocation = width / 2;
        else if (xLocation + width / 2 > 800) xLocation = 800 - width / 2;
    }

    @Override
    protected void updateNode() {
        double offsetX = xLocation - ship.getLayoutBounds().getCenterX();
        ship.setTranslateX(offsetX);
    }

    public Polygon getNode() {
        return ship;
    }

    public void shootLaser(KeyCode code) {
        if (code == KeyCode.SPACE) {

            System.out.println("Shoot laser!");
        }
    }
}

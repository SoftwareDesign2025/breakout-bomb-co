package Game;

import Objects.Ship;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GalagaScreen extends Screen {

    private Ship ship;
    private Rectangle background;
    private static final double SCREEN_WIDTH = 400;
    private static final double SCREEN_HEIGHT = 800;

    public GalagaScreen() {
        super(null);
        getRoot().getChildren().clear();
        initBackground();
        initShip();
    }

    private void initBackground() {
        background = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        background.setFill(Color.BLACK);
        getRoot().getChildren().add(background);
    }

    private void initShip() {
        ship = new Ship(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 100);
        getRoot().getChildren().add(ship.getNode());
    }

    public Ship getShip() {
        return ship;
    }
}

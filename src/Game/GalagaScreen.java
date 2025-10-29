package Game;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import Objects.Ball;
import javafx.scene.shape.Rectangle;


public class GalagaScreen extends Screen {

    private Circle ship;
    private Rectangle background;

    public GalagaScreen() {
        super(null);
        getRoot().getChildren().clear();
        initBackground();
    }

    private void initBackground() {
        background = new Rectangle(0, 0, 400, 800);
        background.setFill(Color.BLACK);
        getRoot().getChildren().add(background);
    }


    public Circle getShip() {
        return ship;
    }
}


package Game;

import Game.Levels.GalagaLevel;
import Objects.Ship;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GalagaScreen extends Screen {
    private final Group root = new Group();

    private Ship ship;
    private Rectangle background;
    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;

    private GalagaLevelMaker galagaLevelMaker;

    public GalagaScreen() {
        super(null); // ✅ Give the parent Screen a proper Group root

        initBackground();
        initShip();

        galagaLevelMaker = new GalagaLevelMaker(root);
        loadLevel(); // optional
    }


    public GalagaLevelMaker getGalagaLevelMaker(){
        return galagaLevelMaker;
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

    public void loadLevel(){
        galagaLevelMaker.loadLevel(new GalagaLevel());
    }
    @Override
    public Group getRoot() {
        return root;
    }
}

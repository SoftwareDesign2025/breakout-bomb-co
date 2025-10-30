package Game;

import Game.Levels.GalagaLevel;
import Objects.Bricks;
import Objects.GalagaEnemies;
import Objects.GalagaEnemy;
import Objects.Ship;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GalagaScreen extends Screen {
    private final Group root = new Group();

    private Ship ship;
    private Rectangle background;
    private static final double SCREEN_WIDTH = 800;
    private static final double SCREEN_HEIGHT = 600;

    private GalagaLevelMaker galagaLevelMaker;
    private List<GalagaEnemy> enemiesList;
    private GalagaEnemies enemies;


    public GalagaScreen() {
        super(null);

        initBackground();
        initShip();

        enemiesList = new ArrayList<>();
        galagaLevelMaker = new GalagaLevelMaker(root, enemiesList);
        enemies = new GalagaEnemies(enemiesList);

        loadLevel();


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

    public GalagaEnemies getEnemies(){
        return enemies;
    }
    @Override
    public Group getRoot() {
        return root;
    }
}

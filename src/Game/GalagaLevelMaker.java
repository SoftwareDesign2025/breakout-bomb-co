package Game;

import Game.Levels.GalagaLevel;
import Objects.GalagaEnemy;
import Objects.Ship;
import Objects.SideMover;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GalagaLevelMaker extends LevelMaker{

    private final List<GalagaEnemy> ENEMIES;

    public GalagaLevelMaker(Group root, List<GalagaEnemy> enemiesList) {
        super(root);
        this.ENEMIES = enemiesList;
    }

    public List<GalagaEnemy> getEnemies() {
        return ENEMIES;
    }



    public void resetLevel() {
        clearGameObjects();
        ENEMIES.clear();
    }

    public void addShip(String imagePath, double startX, double startY) {
        Ship ship = new Ship(imagePath, startX, startY);
        SIDE_MOVER_LIST.add(ship);
        ROOT.getChildren().add(ship.getShip());
        NODE_LIST.add(ship.getShip());
    }

    public void addOutOfBounds(double x, double y, double width, double height, Color color) {
        Rectangle r = new Rectangle(x, y, width, height);
        r.setFill(color);
        OUT_OF_BOUNDS_LIST.add(r);
        ROOT.getChildren().add(r);
        NODE_LIST.add(r);
    }

    public void addEnemy(GalagaEnemy enemy) {
        ENEMIES.add(enemy);
        ROOT.getChildren().add(enemy.getEnemy());
        NODE_LIST.add(enemy.getEnemy());
    }

    public void loadLevel(GalagaLevel level) {
        resetLevel();
        level.build(this);
    }
}

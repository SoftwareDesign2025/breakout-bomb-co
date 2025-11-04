package Game.Galaga;

import Game.LevelMaker;
import Objects.Galaga.GalagaEnemy;
import Objects.Galaga.Ship;
import javafx.scene.Group;

import java.util.List;

public class GalagaLevelMaker extends LevelMaker {

    private final List<GalagaEnemy> ENEMIES;
    private Ship ship;

    public GalagaLevelMaker(Group root, List<GalagaEnemy> enemiesList) {
        super(root);
        this.ENEMIES = enemiesList;
    }

    public void resetLevel() {
        clearGameObjects();
        ENEMIES.clear();
    }

    public void addShip(String imagePath, double startX, double startY) {
        ship = new Ship(imagePath, startX, startY);
        ROOT.getChildren().add(ship.getShip());
        NODE_LIST.add(ship.getShip());
    }

    public Ship getShip() {
        return ship;
    }

    public void addEnemy(GalagaEnemy enemy) {
        ENEMIES.add(enemy);
        ROOT.getChildren().add(enemy.getEnemy());
        NODE_LIST.add(enemy.getEnemy());
    }
}

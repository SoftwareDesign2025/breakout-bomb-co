/*
Authors:
Murph Lennemann

 */

package Game.Galaga;

import Game.LevelMaker;
import Objects.Galaga.GalagaEnemy;
import Objects.Galaga.Ship;
import javafx.scene.Group;

import java.util.List;

public class GalagaLevelMaker extends LevelMaker {

    private final List<GalagaEnemy> ENEMIES;
    private Ship ship;

    /**
     * Authors:
     * @param root
     * @param enemiesList
     */
    public GalagaLevelMaker(Group root, List<GalagaEnemy> enemiesList) {
        super(root);
        this.ENEMIES = enemiesList;
    }

    /**
     * Authors:
     */
    public void resetLevel() {
        clearGameObjects();
        ENEMIES.clear();
    }

    /**
     * Authors: Murph
     * creates a new ship
     * @param imagePath The call to the image used.
     * @param startX the starting X value
     * @param startY the starting Y value
     */
    public void addShip(String imagePath, double startX, double startY) {
        ship = new Ship(imagePath, startX, startY);
        ROOT.getChildren().add(ship.getShip());
        NODE_LIST.add(ship.getShip());
    }

    /**
     * Authors: Murph
     * Getter
     * @return a ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Authors:
     * @param enemy
     */
    public void addEnemy(GalagaEnemy enemy) {
        ENEMIES.add(enemy);
        ROOT.getChildren().add(enemy.getEnemy());
        NODE_LIST.add(enemy.getEnemy());
    }
}

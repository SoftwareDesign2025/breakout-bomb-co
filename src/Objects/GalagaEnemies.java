package Objects;

import java.util.List;
import java.util.ArrayList;
import Game.Screen;

public class GalagaEnemies implements HittableObjects{

    private List<GalagaEnemy> enemies;

    public GalagaEnemies(List<GalagaEnemy> enemies){
        this.enemies = enemies;
    }

    public List<HittableObject> getHittableObjects(){
        return new ArrayList<>(enemies);
    }

    public void drop() {
        for (GalagaEnemy enemy: enemies) {
            enemy.moveDown();
        }
    }

    public int resolveCollisions(Ball ball) {
        return 0;
    }

    public boolean isCleared() {
        return enemies.isEmpty();
    }

    @Override
    public void clearObjects(Screen screen) {
        for (GalagaEnemy enemy:  enemies) {
            screen.getRoot().getChildren().remove(enemy);
        }
        enemies.clear();
    }
}
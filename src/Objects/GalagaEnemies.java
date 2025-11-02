package Objects;

import java.util.List;
import java.util.ArrayList;
import Game.Screen;

public class GalagaEnemies implements HittableObjects{
    private List<GalagaEnemy> enemies;
    private static final double ENEMY_BOTTOM_THRESHOLD = 550;


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

    public int enemiesReachedBottom() {
        int livesLost = 0;
        for (GalagaEnemy enemy : enemies) {
            if (enemy.getEnemy().getLayoutY() + enemy.getEnemy().getTranslateY() + enemy.getEnemy().getFitHeight() >= ENEMY_BOTTOM_THRESHOLD) {
                livesLost++;
                enemy.deactivate();
            }
        }
        return livesLost;
    }


    @Override
    public void clearObjects(Screen screen) {
        for (GalagaEnemy enemy:  enemies) {
            screen.getRoot().getChildren().remove(enemy.getEnemy());
        }
        enemies.clear();
    }
}
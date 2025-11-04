/*
Authors:
Murph Lennemann

 */

package Objects.Galaga;

import java.util.List;
import java.util.ArrayList;
import Game.Screen;
import Objects.Breakout.Ball;
import Objects.HittableObject;
import Objects.HittableObjects;

public class GalagaEnemies implements HittableObjects {
    private List<GalagaEnemy> enemies;
    private static final double ENEMY_BOTTOM_THRESHOLD = 550;

    /**
     * Authors:
     * @param enemies
     */
    public GalagaEnemies(List<GalagaEnemy> enemies){
        this.enemies = enemies;
    }

    /**
     * Authors:
     * @return
     */
    public List<HittableObject> getHittableObjects(){
        return new ArrayList<>(enemies);
    }

    /**
     * Authors:
     */
    public void drop() {
        for (GalagaEnemy enemy: enemies) {
            enemy.moveDown();
        }
    }

    /**
     * Authors:
     * @param ball
     * @return
     */
    public int resolveCollisions(Ball ball) {
        return 0;
    }

    /**
     * Authors:
     * @return
     */
    public boolean isCleared() {
        return enemies.isEmpty();
    }

    /**
     * Authors:
     * @return
     */
    public int enemiesReachedBottom() {
        int livesLost = 0;
        for (GalagaEnemy enemy : enemies) {
            if (enemy.getEnemy().getLayoutY() + enemy.getEnemy().getTranslateY() + enemy.getEnemy().getFitHeight() >= ENEMY_BOTTOM_THRESHOLD ) {
                if(enemy.isActive()){
                    livesLost++;
                    enemy.deactivate();
                }
            }
        }
        return livesLost;
    }


    /**
     * Authors: Murph
     * Used for Easter eggs
     * @param screen the screen to be cleared
     */
    @Override
    public void clearObjects(Screen screen) {
        for (GalagaEnemy enemy:  enemies) {
            screen.getRoot().getChildren().remove(enemy.getEnemy());
        }
        enemies.clear();
    }
}
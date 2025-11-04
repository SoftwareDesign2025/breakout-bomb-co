/*
Authors:
Murph Lennemann

 */

package Objects.Galaga;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import Game.Screen;
import Objects.Breakout.Ball;
import Objects.HittableObject;
import Objects.HittableObjects;
import Objects.Laser;

public class GalagaEnemies implements HittableObjects {
    private List<GalagaEnemy> enemies;
    private static final double ENEMY_BOTTOM_THRESHOLD = 550;
    
    private int frameCounter = 0;
    private int shootInterval;
    private Random random;

    public GalagaEnemies(List<GalagaEnemy> enemies) {
        this(enemies, 60); // Default: shoot every 60 frames (~1 second at 60fps)
    }

  
    
    public GalagaEnemies(List<GalagaEnemy> enemies, int shootInterval) {
        this.enemies = enemies;
        this.shootInterval = shootInterval;
        this.random = new Random();
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
    
    public Laser tryShoot() {
        frameCounter++;
        if (frameCounter >= shootInterval) {
            frameCounter = 0;
            return shootFromRandomEnemy();
        }
        return null;
    }
    
    private Laser shootFromRandomEnemy() {
        List<GalagaEnemy> activeEnemies = new ArrayList<>();
        for (GalagaEnemy enemy : enemies) {
            if (enemy.isActive()) {
                activeEnemies.add(enemy);
            }
        }
        
        if (activeEnemies.isEmpty()) {
            return null;
        }
        
        GalagaEnemy shooter = activeEnemies.get(random.nextInt(activeEnemies.size()));
        
        return new Laser(
            shooter.getEnemy().getLayoutX() + shooter.getEnemy().getFitWidth() / 2,
            shooter.getEnemy().getLayoutY() + shooter.getEnemy().getFitHeight(),
            false  // Enemy laser
        );
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
    
    public void setShootInterval(int interval) {
        this.shootInterval = interval;
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
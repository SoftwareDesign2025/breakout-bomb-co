package Objects;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import Game.Screen;

public class GalagaEnemies implements HittableObjects{
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

    public List<HittableObject> getHittableObjects(){
        return new ArrayList<>(enemies);
    }

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

    public int resolveCollisions(Ball ball) {
        return 0;
    }

    public boolean isCleared() {
        return enemies.isEmpty();
    }
    
    public void setShootInterval(int interval) {
        this.shootInterval = interval;
    }

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


    @Override
    public void clearObjects(Screen screen) {
        for (GalagaEnemy enemy:  enemies) {
            screen.getRoot().getChildren().remove(enemy.getEnemy());
        }
        enemies.clear();
    }
}
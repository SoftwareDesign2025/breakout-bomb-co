package Game;

import Objects.GalagaEnemy;
import Objects.HittableObject;
import Objects.Laser;
import Objects.Lasers;
import Objects.GalagaEnemies;

public class CollisionDetector {
    private Lasers lasers;
    private GalagaEnemies enemies;
    
    public CollisionDetector(Lasers lasers, GalagaEnemies enemies) {
        this.lasers = lasers;
        this.enemies = enemies;
    }
    
    public void checkLaserEnemyCollisions() {
        for (Laser laser : lasers.getActiveLasers()) {
            if (!laser.isPlayerLaser()) continue;
            
            for (HittableObject hittableObject : enemies.getHittableObjects()) {
                if (!hittableObject.isActive()) continue;
                
                GalagaEnemy enemy = (GalagaEnemy) hittableObject;
                
                if (laser.collidesWith(
                    enemy.getEnemy().getLayoutX(),
                    enemy.getEnemy().getLayoutY(),
                    enemy.getEnemy().getFitWidth(),
                    enemy.getEnemy().getFitHeight()
                )) {
                    laser.destroy();
                    enemy.deactivate();
                    break;
                }
            }
        }
    }
}
/*
Authors:
Ben Farmer
 */

package Game;

import Objects.Galaga.GalagaEnemies;
import Objects.Galaga.GalagaEnemy;
import Objects.HittableObject;
import Objects.Laser;
import Objects.Lasers;
import Objects.Galaga.Ship;

public class CollisionDetector {
    private Lasers lasers;
    private GalagaEnemies enemies;
    private int points;
    private Ship ship;
    
    public CollisionDetector(Lasers lasers, GalagaEnemies enemies) {
        this.lasers = lasers;
        this.enemies = enemies;
        this.points = 0;
    }
    
    public int checkLaserEnemyCollisions() {
    	int pointsEarned = 0;
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
                    enemy.loseLife();
                    enemy.enemeyHit();
                    if(enemy.getLives() <= 0) {
                        enemy.deactivate();
                    }
                    pointsEarned += enemy.getPointValue();
                    break;
                }
            }
        }
        return pointsEarned;
    }
    
    public boolean checkLaserShipCollisions(Ship ship) {
        for (Laser laser : lasers.getActiveLasers()) {
            if (laser.isPlayerLaser()) continue;  // Only enemy lasers hit ship
            
            if (laser.collidesWith(
                ship.getShip().getLayoutX(),
                ship.getShip().getLayoutY(),
                ship.getShip().getFitWidth(),
                ship.getShip().getFitHeight()
            )) {
                laser.destroy();
                return true;  // Ship was hit!
            }
        }
        return false;  // Ship safe
    }
   

}

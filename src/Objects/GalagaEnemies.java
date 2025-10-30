package Objects;

import java.util.List;

public class GalagaEnemies {
    private List<GalagaEnemy> enemies;
    public GalagaEnemies(List<GalagaEnemy> enemies){
        this.enemies = enemies;
    }
    public List<GalagaEnemy> getGalagaEnemies() { return enemies; }

    public void moveEnemies(){
        for(GalagaEnemy enemy: enemies){
            enemy.moveDown();
        }
    }


}

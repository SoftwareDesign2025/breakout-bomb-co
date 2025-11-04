package Game.Levels;
/*
Authors: Oscar Kardon
 */

import Game.Galaga.GalagaLevelMaker;
import Objects.Galaga.GalagaEnemy;

//Builds third level of Galaga with an every-other pattern, lives increase by row
public class GalagaLevelThree implements Level<GalagaLevelMaker> {

    @Override
    public void build(GalagaLevelMaker maker) {
        maker.addShip("ship.png", 400, 500);

        double width = 40;
        double height = 40;
        String imagePath = "galagaEnemyImage.png";

        int rows = 4;
        int cols = 8;
        double startX = 100;
        double startY = 0;
        double spacingX = 70;
        double spacingY = 60;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                //skip every other cell
                if ((row + col) % 2 == 0) continue;

                double enemyX = startX + col * spacingX;
                double enemyY = startY + row * spacingY;

                //enemy should get more lives by row
                GalagaEnemy enemy = new GalagaEnemy(
                        width,
                        height,
                        imagePath,
                        enemyX,
                        enemyY,
                        50,
                        null,
                        0.08,
                        4 - row
                );
                enemy.setMovementPattern("wavey");
                maker.addEnemy(enemy);
            }
        }
    }
}

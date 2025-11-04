/*
Authors:

 */

package Game.Levels;

import Game.Galaga.GalagaLevelMaker;

import Objects.GalagaEnemy;

import Objects.Galaga.GalagaEnemy;


public class GalagaLevelOne implements Level<GalagaLevelMaker> {

    /**
     * Authors:
     * @param maker
     */
    public void build(GalagaLevelMaker maker) {
        maker.addShip("ship.png", 400, 500);
        double startX = 100;
        double startY = 80;
        double enemyWidth = 75;
        double enemyHeight = 75;
        double gapX = 15;
        double gapY = 20;
        int rows = 2;
        int cols = 6;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (enemyWidth + gapX);
                double y = startY + row * (enemyHeight + gapY);

                GalagaEnemy enemy = new GalagaEnemy(enemyWidth,enemyHeight, "galagaEnemyImage.png", x, y, 1, null, 0.1, 1);
                maker.addEnemy(enemy);
            }
        }
    }
}

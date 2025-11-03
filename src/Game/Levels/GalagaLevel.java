package Game.Levels;

import Game.GalagaLevelMaker;
import Objects.GalagaEnemy;

public class GalagaLevel implements Level<GalagaLevelMaker> {

    public void build(GalagaLevelMaker maker) {

        maker.addShip("ship.png", 400, 500);
        double startX = 100;
        double startY = 80;
        double enemyWidth = 75;
        double enemyHeight = 75;
        double gapX = 15;
        double gapY = 20;
        int rows = 2;
        int cols = 7;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (enemyWidth + gapX);
                double y = startY + row * (enemyHeight + gapY);

                GalagaEnemy enemy = new GalagaEnemy(enemyWidth,enemyHeight,"/images/bfarmer.png", x, y, 1, null, 0.1);
                maker.addEnemy(enemy);
            }
        }

    }
}

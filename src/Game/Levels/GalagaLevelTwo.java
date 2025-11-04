package Game.Levels;

import Game.Galaga.GalagaLevelMaker;
import Objects.Galaga.GalagaEnemy;

public class GalagaLevelTwo implements Level<GalagaLevelMaker> {

    @Override
    public void build(GalagaLevelMaker maker) {

        maker.addShip("ship.png", 400, 500);

        double startX = 90;
        double startY = 50;
        double enemyWidth = 50;
        double enemyHeight = 50;
        double gapX = 20;
        double gapY = 25;

        String[] enemyImages = {
                "bfarmer.png",
                "okardon.png",
                "gcollins.png",
                "mlennemann.png"
        };

        int rows = 4;
        int cols = 9;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (enemyWidth + gapX);
                double y = startY + row * (enemyHeight + gapY);
                String imagePath = enemyImages[row % enemyImages.length];
                GalagaEnemy enemy = new GalagaEnemy(
                        enemyWidth,
                        enemyHeight,
                        imagePath,
                        x,
                        y,
                        2,
                        null,
                        0.10,
                        1
                );
                maker.addEnemy(enemy);
            }
        }


        double bossWidth = 200;
        double bossHeight = 150;
        double bossX = 300;
        double bossY = -100;

        GalagaEnemy boss = new GalagaEnemy(
                bossWidth,
                bossHeight,
                "mlennemannhonors.png",
                bossX,
                bossY,
                10,
                null,
                0.10,
                15
        );
        maker.addEnemy(boss);
    }
}

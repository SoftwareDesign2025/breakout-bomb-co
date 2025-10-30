package Game.Levels;

import Game.GalagaLevelMaker;
import Objects.GalagaEnemy;
import javafx.scene.paint.Color;

public class GalagaLevel {

    public void build(GalagaLevelMaker maker) {
        maker.addSlider(350, 550);

        double startX = 100;
        double startY = 80;
        double enemyWidth = 75;
        double enemyHeight = 75;
        double gapX = 15;
        double gapY = 20;
        int rows = 4;
        int cols = 6;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (enemyWidth + gapX);
                double y = startY + row * (enemyHeight + gapY);

                GalagaEnemy enemy = new GalagaEnemy(enemyWidth,enemyHeight,"mlennemann.png", x, y, 1, null);
                System.out.println(x  + "  and  "  + y);
                maker.addEnemy(enemy);
            }
        }



        maker.addOutOfBounds(0, 0, 10, 600, Color.TRANSPARENT);
        maker.addOutOfBounds(790, 0, 10, 600, Color.TRANSPARENT);
    }
}

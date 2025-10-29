package Game.Levels;

import Game.LevelMaker;
import javafx.scene.paint.Color;

public class LevelTwo implements Level {
    @Override
    public void build(LevelMaker maker) {
        maker.addOutOfBounds(0, 580, 800, 20, Color.RED);
        maker.addOutOfBounds(0, 40, 800, 20, Color.RED);

        double brickWidth = 70;
        double brickHeight = 20;
        double startX = 200;
        double startY = 145;
        double brickGap = 10;
        int pointValue = 1;
        Color color = Color.MAROON;
        boolean colorChange = false;

        maker.addSlider(360, 540);
        maker.addSlider(360, 80);
        maker.setBallPosition(400, 500);

        int[][] ePattern = {
                {1,1,1,1,1},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,1,1,1,1},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,1,1,1,1},
        };

        maker.printLevel(
                ePattern,
                startX,
                startY,
                brickWidth,
                brickHeight,
                brickGap,
                pointValue,
                color,
                colorChange
        );
    }
}

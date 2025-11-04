package Game.Levels;

import Game.LevelMaker;
import Game.Breakout.BreakoutLevelMaker;
import javafx.scene.paint.Color;
import Objects.Brick;

public class LevelOne implements Level<BreakoutLevelMaker> {
    @Override
    public void build(BreakoutLevelMaker maker) {
        maker.addSlider(360, 500);
        maker.addOutOfBounds(0, 580, 800, 20, Color.RED);

        double brickWidth = 70;
        double brickHeight = 20;
        double startX = 35;
        double startY = 60;
        double brickGap = 10;
        int pointValue = 1;
        Color color = Color.BLUE;
        boolean colorChange = true;

        maker.setBallPosition(400, 400);

        int[][] sixPattern = {
                {1,1,1,1},
                {1,0,0,0},
                {1,0,0,0},
                {1,0,0,0},
                {1,1,1,1},
                {1,0,0,1},
                {1,0,0,1},
                {1,0,0,1},
                {1,0,0,1},
                {1,1,1,1}
        };

        int[][] sevenPattern = {
                {1,1,1,1},
                {0,0,0,1},
                {0,0,0,1},
                {0,0,0,1},
                {0,0,0,1},
                {0,0,0,1},
                {0,0,0,1},
                {0,0,0,1},
                {0,0,0,1},
                {0,0,0,1}
        };

        maker.buildBricks(sixPattern, startX, startY, brickWidth, brickHeight, brickGap, pointValue, color, colorChange);
        double sevenOffsetX = startX + 5 * (brickWidth + brickGap);
        maker.buildBricks(sevenPattern, sevenOffsetX, startY, brickWidth, brickHeight, brickGap, pointValue, color, colorChange);

        Brick unbreakable = new Brick(brickWidth, brickHeight, 400, 200, pointValue, Color.DARKGRAY, null);
        unbreakable.setUnbreakable(true);
        maker.addBrick(unbreakable);
    }
}
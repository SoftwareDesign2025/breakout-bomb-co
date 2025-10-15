// Author: Gavin Collins
import javafx.scene.Group;

import java.util.List;
import java.util.Random;

public class LevelMaker {

    private Group root;
    private List<Brick> bricks;
   

    public LevelMaker(Group root, List<Brick> bricks) {
        this.root = root;
        this.bricks = bricks;
    }

    public void makeLevelOne() {
        double brickWidth = 70;
        double brickHeight = 20;
        double startX = 35;
        double startY = 60;
        double brickGap = 10;
        int pointValue = 1;

        int [][] sixPattern = {
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

        int [][] sevenPattern = {
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

  
        for (int row = 0; row < sixPattern.length; row++) {
            for (int col = 0; col < sixPattern[0].length; col++) {
                if (sixPattern[row][col] == 1) {
                    double x = startX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue);
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        }

       
        double sevenOffsetX = startX + 5 * (brickWidth + brickGap);
        for (int row = 0; row < sevenPattern.length; row++) {
            for (int col = 0; col < sevenPattern[0].length; col++) {
                if (sevenPattern[row][col] == 1) {
                    double x = sevenOffsetX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue);
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        }
    }
}

       
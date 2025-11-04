/*
Authors:
Oscar Kardon

 */

package Game.Levels;

import Game.Breakout.BreakoutLevelMaker;
import Powerups.BallPowerUp;
import javafx.scene.paint.Color;
import Objects.Breakout.Brick;
import Powerups.BiggerSlider;
import Powerups.PiercePowerUp;
import Powerups.PowerUp;

public class LevelThree implements Level<BreakoutLevelMaker> {

    /**
     * Authors:
     * @param maker
     */
    @Override
    public void build(BreakoutLevelMaker maker) {
        maker.addOutOfBounds(0, 580, 800, 20, Color.RED);
        maker.addSlider(360, 500);
        maker.setBallPosition(400, 450);

        double brickWidth = 50;
        double brickHeight = 25;
        int rows = 12;
        int cols = 12;
        double totalWidth = cols * brickWidth;
        double startX = (800 - totalWidth) / 2;
        double startY = 100;
        double centerRow = rows / 2.0;
        double centerCol = cols / 2.0;
        double radius = 5.2;

        // Circle pattern
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double dx = col - centerCol;
                double dy = row - centerRow;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < radius + 0.5) {
                    double x = startX + col * brickWidth;
                    double y = startY + row * brickHeight;
                    Color color;

                    if (dist < radius * 0.5) color = Color.rgb(10, 10, 10);
                    else if (dist < radius * 0.8) color = Color.rgb(25, 25, 25);
                    else color = Color.rgb(45, 45, 45);

                    Brick brick = new Brick(brickWidth - 2, brickHeight - 2, x, y, 1, color, null);
                    int val = maker.randomizeBrick(1);
                    maker.assignPowerUp(brick, val, x, y);
                    maker.addBrick(brick);
                }
            }
        }

        // Fuse pattern with powerups
        double fuseX = startX + centerCol * brickWidth - brickWidth / 2;
        double fuseY = startY + (centerRow - radius - 1) * brickHeight;

        for (int i = 0; i < 4; i++) {
            PowerUp powerUp = null;
            double y = fuseY - i * brickHeight;
            Color color;

            if (i == 0) {
                color = Color.YELLOW;
                powerUp = new PiercePowerUp(fuseX, y);
            } else if (i == 1) {
                color = Color.ORANGE;
                powerUp = new BallPowerUp(fuseX, y);
            } else {
                color = Color.RED;
                powerUp = new BiggerSlider(fuseX, y);
            }

            Brick fuseBrick = new Brick(
                    brickWidth - 2,
                    brickHeight - 2,
                    fuseX,
                    y,
                    5,
                    color,
                    powerUp
            );
            maker.addBrick(fuseBrick);
        }
    }
}

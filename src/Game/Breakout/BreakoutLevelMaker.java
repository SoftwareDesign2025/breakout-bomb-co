/*
Authors:
Murph Lennemann

 */

package Game.Breakout;

import Game.LevelMaker;
import Objects.Breakout.Brick;
import Objects.Breakout.Slider;
import Powerups.BallPowerUp;
import Powerups.BiggerSlider;
import Powerups.PiercePowerUp;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class BreakoutLevelMaker extends LevelMaker {
    private double ballX;
    private double ballY;
    private final List<Brick> BRICKS;
    private final ArrayList<Slider> SLIDER_LIST;

    /**
     * Author:
     * @param root
     * @param bricks
     */
    public BreakoutLevelMaker(Group root, List<Brick> bricks){
        super(root);
        this.BRICKS = bricks;
        this.SLIDER_LIST = new ArrayList<>();
    }

    /**
     * Authors: Murph
     * Getter
     * @return returns the sliders created in the level
     */
    public ArrayList<Slider> getSliderList(){
        return SLIDER_LIST;
    }

    /**
     * Authors: Murph, Oscar
     * Resets all of the gameObjects and removes them from the screen
     * Used
     */
    public void resetLevel() {
        clearGameObjects();
        for (Brick brick : new ArrayList<>(BRICKS)) {
            ROOT.getChildren().remove(brick.getBrick());
        }
        BRICKS.clear();
        for (Slider slider : new ArrayList<>(SLIDER_LIST)) {
            ROOT.getChildren().remove(slider.getNode());
        }
        SLIDER_LIST.clear();
        ballX  = 0;
        ballY = 0;
    }

    /**
     * Authors: Murph
     * @param startX
     * @param startY
     */
    public void addSlider(double startX, double startY) {
        Slider s = new Slider(startX, startY);
        SLIDER_LIST.add(s);
        ROOT.getChildren().add(s.getNode());
        NODE_LIST.add(s.getNode());
    }

    /**
     * Authors: Murph
     * @return
     */
    public double getBallX() {
        return ballX;
    }

    /**
     * Authors: Murph
     * @return
     */
    public double getBallY() {
        return ballY;
    }

    /**
     * Authors:
     * @param val
     * @return
     */
    public int randomizeBrick(int val) {
        if (val != 1) return val;
        int chance = RAND.nextInt(100);
        if (chance < 10) return 2;
        if (chance < 15) return 3;
        if (chance < 20) return 4;
        return 1;
    }

    /**
     * Authors:
     * @param val
     * @param color
     * @return
     */
    private Color getBrickColor(int val, Color color) {
        if (val == 2) return Color.YELLOW;
        if (val == 3) return Color.LIMEGREEN;
        if (val == 4) return Color.BLACK;
        return color;
    }

    /**
     * Authors:
     * @param brick
     * @param val
     * @param x
     * @param y
     */
    public void assignPowerUp(Brick brick, int val, double x, double y) {
        if (val == 2) brick.setPowerUp(new BiggerSlider(x, y));
        if (val == 3) brick.setPowerUp(new PiercePowerUp(x, y));
        if (val == 4) brick.setPowerUp(new BallPowerUp(x, y));
    }

    /**
     * Authors: Murph
     * @param pattern
     * @param startX
     * @param startY
     * @param brickWidth
     * @param brickHeight
     * @param brickGap
     * @param pointValue
     * @param color
     * @param colorChange
     */
    public void buildBricks(int[][] pattern, double startX, double startY, double brickWidth, double brickHeight, double brickGap, int pointValue, Color color, boolean colorChange) {
        for (int row = 0; row < pattern.length; row++) {
            for (int column = 0; column < pattern[row].length; column++) {
                int value = randomizeBrick(pattern[row][column]);
                if (value != 0) {
                    double x = startX + column * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue, color, null);
                    if (colorChange) {
                        brick.getBrick().setFill(getBrickColor(value, color));
                    }
                    assignPowerUp(brick, value, x, y);
                    BRICKS.add(brick);
                    ROOT.getChildren().add(brick.getBrick());
                }
            }
        }
    }

    /**
     * Authors:
     * @param x
     * @param y
     */
    public void setBallPosition(double x, double y) {
        this.ballX = x;
        this.ballY = y;
    }

    /**
     * Authors:
     * @param brick
     */
    public void addBrick(Brick brick) {
        BRICKS.add(brick);
        ROOT.getChildren().add(brick.getBrick());
    }
}

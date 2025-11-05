/*
Authors:
Murph Lennemann
Oscar Kardon
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
     * Author: Oscar Kardon
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
     * Used when loading a new level
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
     * Adds a new slider into list and onto scene
     * @param startX is the x location of a slider
     * @param startY is the y location of a slider
     */
    public void addSlider(double startX, double startY) {
        Slider s = new Slider(startX, startY);
        SLIDER_LIST.add(s);
        ROOT.getChildren().add(s.getNode());
        NODE_LIST.add(s.getNode());
    }

    /**
     * Authors: Murph
     * @return gets the x location of the ball center
     */
    public double getBallX() {
        return ballX;
    }

    /**
     * Authors: Murph
     * @return gets the y location of the ball center
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
     * Author: Oscar Kardon
     * Randomly assigns a powerup to a brick, with optional color change.
     * Cleaning earlier code
     * @param brick the brick to modify
     * @param changeColor whether the brick color should reflect the powerup
     */
    public void randomizeBrick(Brick brick, boolean changeColor) {
        int chance = RAND.nextInt(100);
        if (chance < 10) {
            brick.setPowerUp(new BiggerSlider(brick.getBrick().getLayoutX(), brick.getBrick().getY()));
            if (changeColor) brick.getBrick().setFill(Color.YELLOW);
        }
        else if (chance < 15) {
            brick.setPowerUp(new PiercePowerUp(brick.getBrick().getLayoutX(), brick.getBrick().getY()));
            if (changeColor) brick.getBrick().setFill(Color.LIMEGREEN);
        }
        else if (chance < 20) {
            brick.setPowerUp(new BallPowerUp(brick.getBrick().getLayoutX(), brick.getBrick().getY()));
            if (changeColor) brick.getBrick().setFill(Color.BLACK);
        }
    }


    /**
     * Authors: Murph
     * Creates the level given level parameters
     * @param pattern the 2d arrayList that holds the brick pattern
     * @param startX the x location of the left most brick
     * @param startY the y location of the top most brick
     * @param brickWidth the width of a brick
     * @param brickHeight the height of a brick
     * @param brickGap how far apart bricks are from each other
     * @param pointValue how many points a brick is worth
     * @param color The colors of the bricks
     * @param colorChange Weather or not colors are randomized
     */
    public void buildBricks(int[][] pattern, double startX, double startY, double brickWidth, double brickHeight, double brickGap, int pointValue, Color color, boolean colorChange) {
        for (int row = 0; row < pattern.length; row++) {
            for (int column = 0; column < pattern[row].length; column++) {
                int value = randomizeBrick(pattern[row][column]);
                if (value != 0) {
                    double x = startX + column * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue, color, null);
                    randomizeBrick(brick, colorChange);
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
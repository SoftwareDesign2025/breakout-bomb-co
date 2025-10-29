// Author: Gavin Collins
package Game;

import Game.Levels.Level;
import Powerups.PowerUp;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import Objects.Brick;
import Objects.Slider;
import Powerups.BiggerSlider;
import Powerups.PiercePowerUp;
import Objects.BallPowerUp;

public class LevelMaker {

    private double ballX;
    private double ballY;
    private final Group ROOT;
    private final List<Brick> BRICKS;
    private final ArrayList<Slider> SLIDER_LIST = new ArrayList<>();
    private final ArrayList<Rectangle> OUT_OF_BOUNDS_LIST = new ArrayList<>();
    private final ArrayList<Node> NODE_LIST = new ArrayList<>();
    private final Random RAND = new Random();

    public LevelMaker(Group root, List<Brick> bricks) {
        this.ROOT = root;
        this.BRICKS = bricks;
    }

    public ArrayList<Slider> getSliderList() {
        return SLIDER_LIST;
    }

    public ArrayList<Rectangle> getOutOfBounds() {
        return OUT_OF_BOUNDS_LIST;
    }

    public void resetLevel() {
        if (!NODE_LIST.isEmpty()) {
            ROOT.getChildren().removeAll(NODE_LIST);
            NODE_LIST.clear();
        }
        if (!BRICKS.isEmpty()) {
            for (Brick b : new ArrayList<>(BRICKS)) {
                ROOT.getChildren().remove(b.getBrick());
            }
            BRICKS.clear();
        }
        SLIDER_LIST.clear();
        OUT_OF_BOUNDS_LIST.clear();
        ballX  = 0;
        ballY = 0;
    }

    public void addSlider(double startX, double startY) {
        Slider s = new Slider(startX, startY);
        SLIDER_LIST.add(s);
        ROOT.getChildren().add(s.getNode());
        NODE_LIST.add(s.getNode());
    }

    public void addOutOfBounds(double x, double y, double width, double height, Color color) {
        Rectangle r = new Rectangle(x, y, width, height);
        r.setFill(color);
        OUT_OF_BOUNDS_LIST.add(r);
        ROOT.getChildren().add(r);
        NODE_LIST.add(r);
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public int randomizeBrick(int val) {
        if (val != 1) return val;
        int chance = RAND.nextInt(100);
        if (chance < 10) return 2;
        if (chance < 15) return 3;
        if (chance < 20) return 4;
        return 1;
    }

    private Color getBrickColor(int val, Color color) {
        if (val == 2) return Color.YELLOW;
        if (val == 3) return Color.LIMEGREEN;
        if (val == 4) return Color.BLACK;
        return color;
    }

    public void assignPowerUp(Brick brick, int val, double x, double y) {
        if (val == 2) brick.setPowerUp(new BiggerSlider(x, y));
        if (val == 3) brick.setPowerUp(new PiercePowerUp(x, y));
        if (val == 4) brick.setPowerUp(new BallPowerUp(x, y));
    }

    public void printLevel(int[][] pattern, double startX, double startY, double brickWidth, double brickHeight, double brickGap, int pointValue, Color color, boolean colorChange) {
        for (int row = 0; row < pattern.length; row++) {
            for (int col = 0; col < pattern[row].length; col++) {
                int val = randomizeBrick(pattern[row][col]);
                if (val != 0) {
                    double x = startX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue, color, null);
                    if (colorChange) {
                        brick.getBrick().setFill(getBrickColor(val, color));
                    }
                    assignPowerUp(brick, val, x, y);
                    BRICKS.add(brick);
                    ROOT.getChildren().add(brick.getBrick());
                }
            }
        }
    }

    public void setBallPosition(double x, double y) {
        this.ballX = x;
        this.ballY = y;
    }

    public void addBrick(Brick brick) {
        BRICKS.add(brick);
        ROOT.getChildren().add(brick.getBrick());
    }
    public void loadLevel(Level level) {
        resetLevel();
        level.build(this);
    }

}

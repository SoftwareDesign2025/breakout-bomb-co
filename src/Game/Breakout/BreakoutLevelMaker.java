package Game.Breakout;

import Game.LevelMaker;
import Game.Levels.Level;
import Objects.Brick;
import Objects.Slider;
import Powerups.BallPowerUp;
import Powerups.BiggerSlider;
import Powerups.PiercePowerUp;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class BreakoutLevelMaker extends LevelMaker{
    private double ballX;
    private double ballY;
    private final List<Brick> BRICKS;

    public BreakoutLevelMaker(Group root, List<Brick> bricks){
        super(root);
        this.BRICKS = bricks;
    }

    public void resetLevel() {
        clearGameObjects();
        for (Brick b : new ArrayList<>(BRICKS)) {
            ROOT.getChildren().remove(b.getBrick());
        }
        BRICKS.clear();
        ballX  = 0;
        ballY = 0;
    }

    public void addSlider(double startX, double startY) {
        Slider s = new Slider(startX, startY);
        SIDE_MOVER_LIST.add(s);
        ROOT.getChildren().add(s.getNode());
        NODE_LIST.add(s.getNode());
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

    public void buildBricks(int[][] pattern, double startX, double startY, double brickWidth, double brickHeight, double brickGap, int pointValue, Color color, boolean colorChange) {
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

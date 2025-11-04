/*
Authors:
Murph Lennemann

 */

package Game.Breakout;

import Game.Levels.LevelOne;
import Game.Levels.LevelThree;
import Game.Levels.LevelTwo;
import Game.Screen;
import Objects.Breakout.Ball;
import Objects.Breakout.Bricks;
import Objects.Breakout.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BreakoutScreen extends Screen {
    private BreakoutLevelMaker breakoutLevelMaker;
    private Bricks bricks;
    protected List<Ball> queuedBalls = new ArrayList<>();


    /**
     * Authors: Murph
     * Creates a screen used for breakout
     */
    public BreakoutScreen() {
        super();
        bricks = new Bricks(new ArrayList<>());
        this.breakoutLevelMaker = new BreakoutLevelMaker(root, bricks.getBricksList());
        createLevelList();
        loadLevel(1);
    }

    /**
     * Authors:
     * @param ball
     */
    public void queueNewBall(Ball ball) {
        queuedBalls.add(ball);
    }

    /**
     * Authors:
     * @return
     */
    public List<Ball> consumeQueuedBalls() {
        List<Ball> list = new ArrayList<>(queuedBalls);
        queuedBalls.clear();
        return list;
    }

    public void createLevelList() {
        levels = new ArrayList<>();
        levels.add(new LevelOne());
        levels.add(new LevelTwo());
        levels.add(new LevelThree());
    }

    /**
     * Authors: Murph
     * @param level The current level being played
     */
    @Override
    public void loadLevel(int level) {
        int index = level - 1;
        breakoutLevelMaker.loadLevel(levels.get(index));
    }

    /**
     * Authors: Murph
     * Getter
     * @return the list of sliders currently used in the level
     */
    public ArrayList<Slider> getSliderList() { return breakoutLevelMaker.getSliderList(); }

    /**
     * Authors: Gavin
     */
    @Override
    public void gameOverScreen() {
        Text over = new Text(300, 300, "GAME OVER");
        over.setFill(Color.RED);
        over.setFont(new Font(40));
        root.getChildren().add(over);
    }

    /**
     * Authors: Gavin
     */
    @Override
    public void gameWinScreen() {
        Text win = new Text(300, 300, "YOU WIN!");
        win.setFill(Color.GREEN);
        win.setFont(new Font(40));
        root.getChildren().add(win);
    }

    /**
     * Authors: Gavin
     * @param ball
     */
    public void checkBallToWall(Ball ball) {
        double ballX = ball.getBall().getCenterX();
        double ballY = ball.getBall().getCenterY();
        double radius = ball.getBall().getRadius();
        if (ballX - radius <= 0 || ballX + radius >= 800) {
            ball.reverseXDirection(); }
        if (ballY - radius <= 0) { ball.reverseYDirection(); }
    }

    /**
     * Authors: Gavin
     * @param ball
     * @return
     */
    public boolean ballOutOfBounds(Ball ball) {
        for (javafx.scene.shape.Rectangle bounds : breakoutLevelMaker.getOutOfBounds()) {
            if (ball.getBall().getBoundsInParent().intersects(bounds.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Authors: Murph
     * Getter
     * @return the bricks currently used in the level
     */
    public Bricks getBricks() {
        return bricks;
    }

    /**
     * Authors: Murph
     * Getter
     * @return the levelMaker being used
     */
    public BreakoutLevelMaker getBreakoutLevelMaker(){
        return breakoutLevelMaker;
    }

}

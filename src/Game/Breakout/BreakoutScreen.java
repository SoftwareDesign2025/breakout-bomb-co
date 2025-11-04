/*
Authors:
Murph Lennemann

 */

package Game.Breakout;

import Game.Screen;
import Objects.Breakout.Ball;
import Objects.Breakout.Bricks;
import Objects.Breakout.Slider;
import Objects.SideMover;
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
     */
    public BreakoutScreen() {
        super();
        bricks = new Bricks(new ArrayList<>());
        this.breakoutLevelMaker = new BreakoutLevelMaker(root, bricks.getBricksList());
        loadLevel(1);
    }

    /**
     * Authors: Murph
     * @param b
     */
    public void queueNewBall(Ball b) {
        queuedBalls.add(b);
    }

    /**
     * Authors: Murph
     * @return
     */
    public List<Ball> consumeQueuedBalls() {
        List<Ball> list = new ArrayList<>(queuedBalls);
        queuedBalls.clear();
        return list;
    }

    /**
     * Authors: Murph
     * @param level
     */
    @Override
    public void loadLevel(int level) {
        breakoutLevelMaker.resetLevel();
        if (level == 1) breakoutLevelMaker.loadLevel(new Game.Levels.LevelOne());
        else if (level == 2) breakoutLevelMaker.loadLevel(new Game.Levels.LevelTwo());
        else if (level == 3) breakoutLevelMaker.loadLevel(new Game.Levels.LevelThree());

    }

    /**
     * Authors: Murph
     * @return
     */
    public ArrayList<Slider> getSliderList() { return breakoutLevelMaker.getSliderList(); }

    /**
     * Authors:
     */
    @Override
    public void gameOverScreen() {
        Text over = new Text(300, 300, "GAME OVER");
        over.setFill(Color.RED);
        over.setFont(new Font(40));
        root.getChildren().add(over);
    }

    /**
     * Authors:
     */
    @Override
    public void gameWinScreen() {
        Text win = new Text(300, 300, "YOU WIN!");
        win.setFill(Color.GREEN);
        win.setFont(new Font(40));
        root.getChildren().add(win);
    }

    /**
     * Authors:
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
     * Authors:
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
     * @return
     */
    public Bricks getBricks() {
        return bricks;
    }

    /**
     * Authors: Murph
     * @return
     */
    public BreakoutLevelMaker getBreakoutLevelMaker(){
        return breakoutLevelMaker;
    }

}

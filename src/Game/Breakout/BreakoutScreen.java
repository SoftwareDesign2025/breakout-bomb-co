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

    public BreakoutScreen() {
        super();
        bricks = new Bricks(new ArrayList<>());
        this.breakoutLevelMaker = new BreakoutLevelMaker(root, bricks.getBricksList());
        loadLevel(1);
    }

    public void queueNewBall(Ball b) {
        queuedBalls.add(b);
    }

    public List<Ball> consumeQueuedBalls() {
        List<Ball> list = new ArrayList<>(queuedBalls);
        queuedBalls.clear();
        return list;
    }

    @Override
    public void loadLevel(int level) {
        breakoutLevelMaker.resetLevel();
        if (level == 1) breakoutLevelMaker.loadLevel(new Game.Levels.LevelOne());
        else if (level == 2) breakoutLevelMaker.loadLevel(new Game.Levels.LevelTwo());
        else if (level == 3) breakoutLevelMaker.loadLevel(new Game.Levels.LevelThree());

    }

    public ArrayList<Slider> getSliderList() { return breakoutLevelMaker.getSliderList(); }

    @Override
    public void gameOverScreen() {
        Text over = new Text(300, 300, "GAME OVER");
        over.setFill(Color.RED);
        over.setFont(new Font(40));
        root.getChildren().add(over);
    }

    @Override
    public void gameWinScreen() {
        Text win = new Text(300, 300, "YOU WIN!");
        win.setFill(Color.GREEN);
        win.setFont(new Font(40));
        root.getChildren().add(win);
    }
    public void checkBallToWall(Ball ball) {
        double ballX = ball.getBall().getCenterX();
        double ballY = ball.getBall().getCenterY();
        double radius = ball.getBall().getRadius();
        if (ballX - radius <= 0 || ballX + radius >= 800) {
            ball.reverseXDirection(); }
        if (ballY - radius <= 0) { ball.reverseYDirection(); }
    }

    public boolean ballOutOfBounds(Ball ball) {
        for (javafx.scene.shape.Rectangle bounds : breakoutLevelMaker.getOutOfBounds()) {
            if (ball.getBall().getBoundsInParent().intersects(bounds.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public Bricks getBricks() {
        return bricks;
    }

    public BreakoutLevelMaker getBreakoutLevelMaker(){
        return breakoutLevelMaker;
    }

}

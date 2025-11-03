package Game;

import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import Objects.Ball;
import Objects.Bricks;
import Objects.SideMover;

public abstract class Screen {
    protected Group root;
    protected Text scoreboard;
    protected Bricks bricks;
    protected List<Ball> queuedBalls = new ArrayList<>();

    public Screen() {
        root = new Group();
        scoreboard = new Text(10, 20, "High Score: 0 Score: 0 Lives: 5");
        scoreboard.setFill(Color.GREEN);
        scoreboard.setFont(new Font(23));
        root.getChildren().add(scoreboard);
        bricks = new Bricks(new ArrayList<>());
    }

    public abstract void loadLevel(int level);

    public Group getRoot() { return root; }
    public Bricks getBricks() { return bricks; }
    public abstract ArrayList<SideMover> getSideMoverList();

    public void queueNewBall(Ball b) { queuedBalls.add(b); }
    public List<Ball> consumeQueuedBalls() {
        List<Ball> list = new ArrayList<>(queuedBalls);
        queuedBalls.clear();
        return list;
    }
    public abstract void gameOverScreen();
    public abstract void gameWinScreen();

    public boolean ballOutOfBounds(Ball ball) {

        return false;
    }


    public void displayScoreBoard(int highScore, int score, int lives) {
        scoreboard.setText("High Score: " + highScore + " Score: " + score + " Lives: " + lives);
    }

    public void checkBallToWall(Ball ball) {

    }



}

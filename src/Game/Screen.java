/*
Authors:
Murph Lennemann

 */

package Game;

import Game.Levels.Level;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import Objects.Breakout.Ball;
import Objects.Breakout.Bricks;
import Objects.SideMover;

public abstract class Screen {

    protected Group root;
    protected Text scoreboard;
    protected List<Level> levels;

    public Screen() {
        root = new Group();
        scoreboard = new Text(10, 20, "High Score: 0 Score: 0 Lives: 5");
        scoreboard.setFill(Color.GREEN);
        scoreboard.setFont(new Font(23));
        root.getChildren().add(scoreboard);
        createLevelList();
    }

    /**
     * Authors:
     * @param ball
     * @return
     */
    public boolean ballOutOfBounds(Ball ball) {
        return false;
    }

    /**
     * Authors: Murph
     * Getter
     * @return returns the root that runs the scene
     */
    public Group getRoot() {
        return root;
    }

    /**
     * Authors:
     * @param highScore
     * @param score
     * @param lives
     */
    public void displayScoreBoard(int highScore, int score, int lives) {
        scoreboard.setText("High Score: " + highScore + " Score: " + score + " Lives: " + lives);
    }

    /**
     * Authors:
     * @param ball
     */
    public void checkBallToWall(Ball ball) {}
    public abstract void createLevelList();
    public abstract void gameOverScreen();
    public abstract void loadLevel(int level);
    public abstract void gameWinScreen();

}

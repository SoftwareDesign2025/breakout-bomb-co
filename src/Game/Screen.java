/**
 * @author Murph Lennemann
 * @author Gavin Collins
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

public abstract class Screen {

    protected Group root;
    protected Text scoreboard;
    protected List<Level> levels;
    protected int level;

    public Screen(int level) {
        double scoreboardX = 10.0;
        double scoreboardY = 20.0;
        double scoreboardFontSize = 23.0;
        String scoreboardText = "High Score: 0 Score: 0 Lives: 5";
        root = new Group();
        this.level = level;
        this.levels = new ArrayList<>();
        scoreboard = new Text(scoreboardX, scoreboardY, scoreboardText);
        scoreboard.setFill(Color.GREEN);
        scoreboard.setFont(new Font(scoreboardFontSize));
        root.getChildren().add(scoreboard);
        createLevelList();
    }

    /**
     * Authors: Gavin
     * @param ball
     * @return
     */
    protected boolean ballOutOfBounds(Ball ball) {
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
     * Authors: Gavin
     * @param highScore
     * @param score
     * @param lives
     */
    protected void displayScoreBoard(int highScore, int score, int lives) {
        scoreboard.setText("High Score: " + highScore + " Score: " + score + " Lives: " + lives);
    }

    /**
     * Authors: Gavin
     * @param ball
     */
    protected void checkBallToWall(Ball ball) {}
    protected abstract void createLevelList();
    protected abstract void gameOverScreen();
    protected abstract void loadLevel(int level);
    protected abstract void gameWinScreen();

}

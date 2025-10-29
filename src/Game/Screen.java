// Author: Gavin Collins
package Game;

import java.util.ArrayList;
import java.util.List;

import Game.Levels.LevelOne;
import Game.Levels.LevelThree;
import Game.Levels.LevelTwo;
import Objects.Ball;
import Objects.Brick;
import Objects.Bricks;
import Objects.Slider;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

public class Screen {
   private Group root;
   private Text scoreboard;
   private Rectangle background;
   private Brick brick;
   private List<Brick> bricksList;
   private LevelMaker levelMaker;
   private Bricks bricks;

   public Screen(Ball ball) {
       root = new Group();
       background = new Rectangle(0, 0, 800, 600);
       background.setFill(Color.WHITE);
       root.getChildren().add(background);

       scoreboard = new Text(10, 20, "High Score: 0   Score: 0   Lives: 3");
       scoreboard.setFill(Color.BLACK);
       scoreboard.setFont(new Font(23));
       root.getChildren().add(scoreboard);

       bricksList = new ArrayList<>();

       // Now LevelMaker handles all level creation
       levelMaker = new LevelMaker(root, bricksList);
       bricks = new Bricks(bricksList);
   }

   // Everything else remains exactly the same...
   public Bricks getBricks() { return bricks; }
   public Group getRoot() { return root; }
   public ArrayList<Slider> getSlider() { return levelMaker.getSliderList(); }
   public Brick getBrick() { return brick; }
   public ArrayList<Rectangle> getOutOfBounds() { return levelMaker.getOutOfBounds(); }
    private List<Ball> queuedBalls = new ArrayList<>();

    public void queueNewBall(Ball b) {
        queuedBalls.add(b);
    }

    public List<Ball> consumeQueuedBalls() {
        List<Ball> result = new ArrayList<>(queuedBalls);
        queuedBalls.clear();
        return result;
    }



    public void checkBallToWall(Ball ball) {
       double ballX = ball.getBall().getCenterX();
       double ballY = ball.getBall().getCenterY();
       double radius = ball.getBall().getRadius();
       if (ballX - radius <= 0 || ballX + radius >= 800) {
           ball.reverseXDirection();
       }
       if (ballY - radius <= 0) {
           ball.reverseYDirection();
       }
   }

   public boolean ballOutOfBounds(Ball ball) {
	   for (Rectangle bounds: levelMaker.getOutOfBounds()) {
		   if (ball.getBall().getBoundsInParent().intersects(bounds.getBoundsInParent())) {
			   return true;
		   }
	   }
	   return false;
   }

   public void displayScoreBoard(int HighScore,int score, int lives) {
       scoreboard.setText("High Score: " + HighScore + " Score: " + score + "  Lives: " + lives);
   }

   public void gameOverScreen() {
       Text gameOver = new Text(300, 300, "GAME OVER");
       gameOver.setFill(Color.RED);
       gameOver.setFont(new Font(36));
       root.getChildren().add(gameOver);
   }

   public void gameWinScreen() {
       Text win = new Text(300, 300, "YOU WIN!");
       win.setFill(Color.GREEN);
       win.setFont(new Font(36));
       root.getChildren().add(win);
   }


    public void loadLevel(int levelNumber) {
        levelMaker.resetLevel();

        if (levelNumber == 1) {
            levelMaker.loadLevel(new LevelOne());
        }
        else if (levelNumber == 2) {
            levelMaker.loadLevel(new LevelTwo());
        }
        else if (levelNumber == 3) {
            levelMaker.loadLevel(new LevelThree());
        }
    }


    public LevelMaker getLevelMaker() {
	    return levelMaker;
	}
}

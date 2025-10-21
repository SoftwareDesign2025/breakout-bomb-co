// Author: Gavin Collins
import java.util.ArrayList;
import java.util.List;
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
   private List<Brick> bricks;
   LevelMaker levelMaker;

   public Screen(Ball ball) {
       root = new Group();
       background = new Rectangle(0, 0, 800, 600);
       background.setFill(Color.WHITE);
       root.getChildren().add(background);

       scoreboard = new Text(10, 20, "High Score: 0   Score: 0   Lives: 3");
       scoreboard.setFill(Color.BLACK);
       scoreboard.setFont(new Font(23));
       root.getChildren().add(scoreboard);

       bricks = new ArrayList<>();

       // Now LevelMaker handles all level creation
       levelMaker = new LevelMaker(root, bricks);
       root.getChildren().add(ball.getBall());
   }

   // Everything else remains exactly the same...
   public List<Brick> getBricks() { return bricks; }
   public Group getRoot() { return root; }
   public ArrayList<Slider> getSlider() { return levelMaker.getSliderList(); }
   public Brick getBrick() { return brick; }
   public ArrayList<Rectangle> getOutOfBounds() { return levelMaker.getOutOfBounds(); }

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
   public void levelClearScreen() {
       Text lvlClear = new Text(300, 300, "LEVEL CLEARED!");
       lvlClear.setFill(Color.GREEN);
       lvlClear.setFont(new Font(36));
       root.getChildren().add(lvlClear);
   }
   
   public void loadLevel(int levelNumber) {
		levelMaker.resetLevel();
		if (levelNumber == 1) {
			levelMaker.makeLevelTwo();
		}
		else if (levelNumber == 2) {
			levelMaker.makeLevelTwo();
		}
		
	}

   public LevelMaker getLevelMaker() {
	    return levelMaker;
	}
}

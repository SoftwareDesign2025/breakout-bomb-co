

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

public class Screen {

    private Group root;          
    private Text scoreboard;     
    private Slider slider;
    private Rectangle background;
    private Rectangle outOfBounds;

    public Screen() {
        root = new Group();

        
        background = new Rectangle(0, 0, 800, 600);
        background.setFill(Color.WHITE);
        root.getChildren().add(background);

        
        scoreboard = new Text(10, 20, "Score: 0  Lives: 3");
        scoreboard.setFill(Color.BLACK);
        scoreboard.setFont(new Font(18));
        root.getChildren().add(scoreboard);

        
        slider = new Slider(360); 
        root.getChildren().add(slider.getNode());
        
        outOfBounds = new Rectangle(0,580,800,600);
        outOfBounds.setFill(Color.RED);
        root.getChildren().add(outOfBounds);
    }
    public Rectangle getOutOfBound() {
    	return outOfBounds;
    }
    

    public Group getRoot() {
        return root;
    }

    public Slider getSlider() {
        return slider;
    }

    public void displayScoreBoard(int score, int lives) {
        scoreboard.setText("Score: " + score + "  Lives: " + lives);
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
    public void ballOnScreen() { } 
    public void bricksOnScreen() { }
}

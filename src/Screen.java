import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class Screen {

    private Group root;          
    private Rectangle background; 
    private Text scoreboard;     

    public Screen() {
       
        root = new Group();

        // Also if we don't like the color we can change these 
        background = new Rectangle(0, 0, 800, 600); 
        background.setFill(Color.WHITE);

       
        root.getChildren().add(background);

      
        scoreboard = new Text(10, 20, "Score: 0  Lives: 3");
        scoreboard.setFill(Color.BLACK);
        scoreboard.setFont(new Font(18));

       
        root.getChildren().add(scoreboard);
    }

    
    public Group getRoot() {
        return root;
    }

   
    public void displayScoreBoard(int score, int lives) {
        scoreboard.setText("Score: " + score + "  Lives: " + lives);
    }

    // Murph call this and the gameWinScreen() in the game loop 
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

    // Will add these when they are ready
    public void sliderOnScreen() {
    	
    }
    public void ballOnScreen() {
    	
    }
    public void bricksOnScreen() {
    	
    }
}

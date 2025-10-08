//Author Gavin Collins

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
    private Slider slider;
    private Rectangle background;
    private Rectangle outOfBounds;
    private Brick brick;
    private List<Brick> bricks;

    public Screen(Ball ball) {
        root = new Group();

        background = new Rectangle(0, 0, 800, 600);
        background.setFill(Color.WHITE);
        root.getChildren().add(background);

        scoreboard = new Text(10, 20, "Score: 0  Lives: 3");
        scoreboard.setFill(Color.BLACK);
        scoreboard.setFont(new Font(23));
        root.getChildren().add(scoreboard);

        slider = new Slider(360);
        root.getChildren().add(slider.getNode());

        outOfBounds = new Rectangle(0, 580, 800, 20);
        outOfBounds.setFill(Color.RED);
        root.getChildren().add(outOfBounds);

        bricks = new ArrayList<>();
        makeBrickPicture(7, 9);

       
        root.getChildren().add(ball.getBall());
    }

    private void makeBrickPicture(int rows, int cols) {
        double brickWidth = 70;
        double brickHeight = 20;
        double startX = 35;
        double startY = 60;
        double brickGap = 10;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + col * (brickWidth + brickGap);
                double y = startY + row * (brickHeight + brickGap);
                Brick brick = new Brick(brickWidth, brickHeight, x, y);
                bricks.add(brick);
                root.getChildren().add(brick.getBrick());
            }
        }
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public Group getRoot() {
        return root;
    }

    public Slider getSlider() {
        return slider;
    }

    public Brick getBrick() {
        return brick;
    }

    public Rectangle getOutOfBounds() {
        return outOfBounds;
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
        return ball.getBall().getBoundsInParent().intersects(outOfBounds.getBoundsInParent());
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

    public boolean checkBrickCollisions(Ball ball){
        for (Brick brick : bricks) {
            if(brick.isBrickActive()) {
                if(brick.detectCollisionWithBall(ball)){
                    return true;
                }
            }
        }
        return false;
    }
}

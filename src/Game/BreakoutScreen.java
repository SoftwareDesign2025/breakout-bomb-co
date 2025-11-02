package Game;

import Objects.Ball;
import Objects.Bricks;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BreakoutScreen extends Screen {
    public BreakoutScreen(Ball ball) {
        super();
        loadLevel(1);
    }

    @Override
    public void loadLevel(int level) {
        levelMaker.resetLevel();
        if (level == 1) levelMaker.loadLevel(new Game.Levels.LevelOne());
        else if (level == 2) levelMaker.loadLevel(new Game.Levels.LevelTwo());
        else if (level == 3) levelMaker.loadLevel(new Game.Levels.LevelThree());

    }

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
        for (javafx.scene.shape.Rectangle bounds : levelMaker.getOutOfBounds()) {
            if (ball.getBall().getBoundsInParent().intersects(bounds.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

}

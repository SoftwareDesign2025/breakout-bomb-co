
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Brick {
    private boolean active;
    private Rectangle brick;
    //public final double WIDTH = 80;
    //public final double HEIGHT = 20;

    public Brick(double width, double height, double startX, double startY){
        active = true;
        brick = new Rectangle(startX, startY, width, height);
        brick.setFill(Color.BLUE);
    }

    public Rectangle getBrick() {
        return brick;
    }


    public void deactivateBrick(){
        active = false;
        brick.setVisible(false);
    }

    public boolean isBrickActive(){
        return active;
    }

    public boolean detectCollisionWithBall(Ball ball) {
        if (brick.getBoundsInParent().intersects(ball.getBall().getBoundsInParent())) {
            connectWithBall(ball);
            return true;
        }
        return false;
    }
    public void connectWithBall(Ball ball){
        deactivateBrick();
        ball.reverseYDirection();
    }
}
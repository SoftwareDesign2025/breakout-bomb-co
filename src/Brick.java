
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Brick {
    private boolean active;
    private Rectangle brick;
    private int pointValue;


    public Brick(double width, double height, double startX, double startY, int pointValue){
        active = true;
        this.pointValue = pointValue;
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

    public int detectCollisionWithBall(Ball ball) {
        if (brick.getBoundsInParent().intersects(ball.getBall().getBoundsInParent())) {
            connectWithBall(ball);
            return pointValue;
        }
        return 0;
    }
    public void connectWithBall(Ball ball){
        deactivateBrick();
        ball.reverseYDirection();
    }
}
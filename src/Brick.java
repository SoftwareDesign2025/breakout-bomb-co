import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Brick {
    private boolean active;
    private Rectangle brick;
    public final double WIDTH = 80;
    public final double HEIGHT = 20;

    public Brick(double startX, double startY){
        active = true;
        brick = new Rectangle(startX, startY, WIDTH, HEIGHT);
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

    public void connectWithBall(Ball ball){
        if (brick.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            deactivateBrick();
            ball.reverseYDirection();
            //add to score?
        }
    }
}

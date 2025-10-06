import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Brick {
    private boolean active;
    private Location location;
    private Rectangle brick;
    public static final double WIDTH = 80;
    public static final double HEIGHT = 20;

    public Brick(Location location){
        this.location = location;
        active = true;
        brick = new Rectangle(location.getX(), location.getY(), WIDTH, HEIGHT);
        brick.setFill(Color.BLUE);
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

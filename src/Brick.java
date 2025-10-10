
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
    public void connectWithBall(Ball ball) {
        double ballX = ball.getBall().getCenterX();
        double ballY = ball.getBall().getCenterY();
        double brickLeft = brick.getX();
        double brickRight = brick.getX() + brick.getWidth();
        double brickTop = brick.getY();
        double brickBottom = brick.getY() + brick.getHeight();

        double overlapLeft = Math.abs(ballX + ball.getBall().getRadius() - brickLeft);
        double overlapRight = Math.abs(brickRight - (ballX - ball.getBall().getRadius()));
        double overlapTop = Math.abs(ballY + ball.getBall().getRadius() - brickTop);
        double overlapBottom = Math.abs(brickBottom - (ballY - ball.getBall().getRadius()));

        //find which overlap happens more
        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

        //if hits the side of a brick
        if (minOverlap == overlapLeft || minOverlap == overlapRight) {
            ball.reverseXDirection();
        } else {
            ball.reverseYDirection();
        }

        deactivateBrick();
    }
}
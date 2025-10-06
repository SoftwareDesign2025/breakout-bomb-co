import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Ball {
    private final double RADIUS = 10;
    private final double STARTING_BALL_SPEED = 1;
    private double speed;
    private double xDirection;
    private double yDirection;
    private Circle ball;

    public Ball(double startX, double startY){
        speed = STARTING_BALL_SPEED;
        xDirection = 1.5;
        yDirection = 2;
        ball = new Circle(startX, startY, RADIUS, Color.RED);
    }
    public Circle getBall() {
        return ball;
    }
    public void updateBallLocation(){
        ball.setCenterX(ball.getCenterX() + xDirection * speed);
        ball.setCenterY(ball.getCenterY() + yDirection * speed);
    }

    public void reverseXDirection() {
        xDirection *= -1;
    }

    public void reverseYDirection() {
        yDirection *= -1;
    }

    public void changeXDirection(double xDirection) {
        this.xDirection = xDirection;
    }

    public void changeYDirection(double yDirection) {
        this.yDirection = yDirection;
    }

    public void changeSpeed(double speed){
        this.speed = speed;
    }

}

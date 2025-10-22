import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

//This class contains the Ball object
public class Ball {
    private final double STARTING_BALL_SPEED = 1;
    private final double STARTING_X_DIRECTION = 0.2;
    private  final double STARTING_Y_DIRECTION = 2;
    private double radius;
    private double speed;
    private double xDirection;
    private double yDirection;
    private Circle ball;

    public Ball(double radius, double startX, double startY){
        this.radius = radius;
        speed = STARTING_BALL_SPEED;
        xDirection = STARTING_X_DIRECTION;
        yDirection = STARTING_Y_DIRECTION;
        ball = new Circle(startX, startY, radius, Color.GREEN);
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

    public void increaseSpeed(){
        speed+=0.05;
    }
    public double getX() { return ball.getCenterX(); }
    public double getY() { return ball.getCenterY(); }


}

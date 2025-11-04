/*
Authors:
Oscar Kardon
 */

package Objects.Breakout;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

//This class contains the Ball object for the Breakout game
public class Ball {
    private final double STARTING_BALL_SPEED = 1;
    private final double STARTING_X_DIRECTION = 0.2;
    private  final double STARTING_Y_DIRECTION = 2;
    private double speed;
    private double xDirection;
    private double yDirection;
    private Circle ball;

    /**
     * Authors:Oscar Kardon
     * @param radius
     * @param startX
     * @param startY
     */
    public Ball(double radius, double startX, double startY){
        speed = STARTING_BALL_SPEED;
        xDirection = STARTING_X_DIRECTION;
        yDirection = STARTING_Y_DIRECTION;
        ball = new Circle(startX, startY, radius, Color.GREEN);
    }

    /**
     * Authors:Oscar Kardon
     * @return the Circle object of ball
     */
    public Circle getBall() {
        return ball;
    }

    /**
     * Authors:Oscar Kardon
     */
    public void updateBallLocation(){
        ball.setCenterX(ball.getCenterX() + xDirection * speed);
        ball.setCenterY(ball.getCenterY() + yDirection * speed);
    }

    /**
     * Authors:Oscar Kardon
     */
    public void reverseXDirection() {
        xDirection *= -1;
    }

    /**
     * Authors:Oscar Kardon
     */
    public void reverseYDirection() {
        yDirection *= -1;
    }

    /**
     * Authors:Oscar Kardon
     * @param xDirection
     */
    public void changeXDirection(double xDirection) {
        this.xDirection = xDirection;
    }

    /**
     * Authors:Oscar Kardon
     * @param yDirection
     */
    public void changeYDirection(double yDirection) {
        this.yDirection = yDirection;
    }

    /**
     * Authors:Oscar Kardon
     * @param speed
     */
    public void changeSpeed(double speed){
        this.speed = speed;
    }

    /**
     * Authors:Oscar Kardon
     */
    public void increaseSpeed(){
        speed+=0.05;
    }
    public double getX() { return ball.getCenterX(); }
    public double getY() { return ball.getCenterY(); }

}

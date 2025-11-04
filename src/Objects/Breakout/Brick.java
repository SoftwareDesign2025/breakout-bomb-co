/*
Authors:

 */

package Objects.Breakout;

import Objects.HittableObject;
import javafx.scene.shape.Rectangle;
import Powerups.PowerUp;
import javafx.scene.paint.Color;

//This class contains the Brick class
public class Brick extends HittableObject {
    private Rectangle brick;
    private boolean unbreakable = false;

    /**
     * Authors:
     * @param width
     * @param height
     * @param startX
     * @param startY
     * @param pointValue
     * @param color
     * @param powerUp
     */
    public Brick(double width, double height, double startX, double startY, int pointValue, Color color, PowerUp powerUp){
        super(startX, startY, pointValue, powerUp);
        brick = new Rectangle(startX, startY, width, height);
        brick.setFill(color);
        this.hittableObject = brick;
    }

    /**
     * Authors:
     * @return
     */
    public Rectangle getBrick() {
        return brick;
    }

    /**
     * Authors:
     * @param value
     */
    public void setUnbreakable(boolean value) {
        unbreakable = value;
    }

    /**
     * Authors:
     * @return
     */
    @Override
    public boolean isUnbreakable() {
        return unbreakable;
    }

    /**
     * Authors:
     */
    @Override
    public void deactivate(){
        if (unbreakable) return;
        super.deactivate();
    }

    /**
     * Authors:
     * @param ball
     * @return
     */
    public int detectCollisionWithBall(Ball ball) {
        if (brick.getBoundsInParent().intersects(ball.getBall().getBoundsInParent())) {
            connectWithBall(ball);
            return pointValue;
        }
        return 0;
    }

    /**
     * Authors:
     * @param ball
     */
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

        // find which overlap happens more
        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

        // if hits the side of a brick
        if (!Powerups.PiercePowerUp.isActive()) {
            if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                ball.reverseXDirection();
            } else {
                ball.reverseYDirection();
            }
        }

        if (powerUp != null){
            powerUp.spawnAt(brick.getX(), brick.getY());
        }

        
        if (!unbreakable) {
            deactivate();
        }
    }
}

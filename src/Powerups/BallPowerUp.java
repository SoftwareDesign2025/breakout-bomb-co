/*
Authors:
Ben Farmer

 */

package Powerups;
import Game.Breakout.BreakoutScreen;
import Objects.Breakout.Ball;
import Objects.Breakout.Slider;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BallPowerUp extends PowerUp {
    private BreakoutScreen screen;
    private java.util.List<Ball> balls;
    private boolean finished = false;

    /**
     * Authors:
     * @param x
     * @param y
     */
    public BallPowerUp(double x, double y) {
        super(x, y);
        getNode().setFill(Color.BLUEVIOLET);
    }

    /**
     * Authors:
     * set right after spawning (so it knows where to put the new ball)
     * @param screen
     * @param balls
     */
    public void setBallPosition(BreakoutScreen screen, java.util.List<Ball> balls) {
        this.screen = screen;
        this.balls = balls;
    }

    /**
     * Authors:
     * @param sliders
     */
    void startEffect(ArrayList<Slider> sliders) {
        if (screen == null || balls == null || balls.isEmpty()) {
            finished = true;
            return;
        }
        Ball one = balls.get(0); // main ball
        double bx = one.getBall().getCenterX();
        double by = one.getBall().getCenterY();
        double r  = one.getBall().getRadius();

        Ball addedBall = new Ball((int) r, bx, by);
        addedBall.changeSpeed(1);
        addedBall.changeXDirection(randomDirection());
        addedBall.changeYDirection(randomDirection());

        screen.getRoot().getChildren().add(addedBall.getBall());
        if (screen != null) {
            screen.queueNewBall(addedBall);
        }

        finished = true; 
        super.stopPowerUp();
    }

    /**
     * Authors:
     * @return
     */
    public double randomDirection() {
        if (Math.random() < 0.5) {
          
            return -2 + Math.random() * .3;
        } else {
           
            return 1 + Math.random() * .3;
        }
    }

    /**
     * Authors:
     * @param x
     * @param y
     * @return
     */
    @Override public PowerUp spawnAt(double x, double y) { 
    	return new BallPowerUp(x, y); 
    	}

    /**
     * Authors
     * @return
     */
    public boolean isPowerUpOver() { return finished; }

    /**
     * Authors:
     * @param sliders
     */
    @Override
    public void onPickup(ArrayList<Slider> sliders) {
        startEffect(sliders);
    }

    /**
     * Authors:
     * @param myScreen
     * @param myBalls
     */
    @Override
    public void onSpawn(BreakoutScreen myScreen, ArrayList<Ball> myBalls) {
        setBallPosition(myScreen, myBalls);
    }

}

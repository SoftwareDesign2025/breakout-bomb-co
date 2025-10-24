
package Objects;
import Game.Screen;
import Powerups.PowerUp;

import java.util.ArrayList;

public class BallPowerUp extends PowerUp {
    private Screen screen;
    private java.util.List<Ball> balls;
    private boolean finished = false;

    public BallPowerUp(double x, double y) { super(x, y); }

    // set right after spawning (so it knows where to put the new ball)
    public void setBallPosition(Screen screen, java.util.List<Ball> balls) {
        this.screen = screen;
        this.balls = balls;
    }

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

    public double randomDirection() {
        if (Math.random() < 0.5) {
          
            return -2 + Math.random() * .3;
        } else {
           
            return 1 + Math.random() * .3;
        }
    }
    @Override public PowerUp spawnAt(double x, double y) { 
    	return new BallPowerUp(x, y); 
    	}
    
    public boolean isPowerUpOver() { return finished; }

    @Override
    public void onPickup(ArrayList<Slider> sliders) {
        startEffect(sliders);
    }

}

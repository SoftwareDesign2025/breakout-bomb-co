//Author Gavin Collins
package Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;

import Game.Screen;
import Powerups.PowerUp;
import javafx.scene.input.KeyCode;
public class Slider {
   
   public static final double SPEED = 20.0;
   public static final double WIDTH = 80;
   public static final double HEIGHT = 20;
   private double yLocation;
   private double xLocation;
   private Rectangle slider;
   public Slider(double startX, double startY) {
       this.xLocation = startX;
       this.yLocation = startY;
       slider = new Rectangle(xLocation, yLocation, WIDTH, HEIGHT);
       slider.setFill(Color.BLACK);
   }
   public void moveSideToSide(boolean goLeft) {
       if (goLeft) {
           xLocation -= SPEED;
       } else {
           xLocation += SPEED;
       }
       stopAtEdges(800);
   }
   public void handleMovement(KeyCode code) {
       if (code == KeyCode.LEFT || code == KeyCode.A) {
           moveSideToSide(true);
       } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
           moveSideToSide(false);
       }
   }
   private void stopAtEdges(double screenWidth) {
       if (xLocation < 0) {
           xLocation = 0;
       } else if (xLocation + WIDTH > screenWidth) {
           xLocation = screenWidth - WIDTH;
       }
       slider.setX(xLocation);
   }
 
   public void checkSliderCollision(Ball ball) {
	  
	       if (ball.getBall().getBoundsInParent().intersects(slider.getBoundsInParent())) {
	           double sliderX = slider.getX();
	           double sliderWidth = slider.getWidth();
	           double ballX = ball.getBall().getCenterX();
	           double zoneWidth = sliderWidth / 3;
	          
	           double angleMath = (sliderX + 40) - ballX;
	           ball.changeXDirection(angleMath/-20);
	           ball.reverseYDirection();
	           ball.increaseSpeed();
           
       }
   }
  
   public void checkPowerUpCollision(List<PowerUp> activePowerUps, Screen screen) {
	    for (int i = activePowerUps.size() - 1; i >= 0; i--) {
	        PowerUp pu = activePowerUps.get(i);
	        if (pu.getNode().getBoundsInParent().intersects(slider.getBoundsInParent())) {
	            pu.onPickup(this);                          // start the effect
	            screen.getRoot().getChildren().remove(pu.getNode()); // hide the circle
	            // DO NOT remove pu from the list here — it still needs to tick
	        }
	    }
	}

  
   public Rectangle getNode() {
       return slider;
   }
}

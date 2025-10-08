//Author Gavin Collins
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;

public class Slider {

    public static final double YLOCATION = 500;
    public static final double SPEED = 15.0;
    public static final double WIDTH = 80;
    public static final double HEIGHT = 20;

    private double xLocation;
    private Rectangle slider;

    public Slider(double startX) {
        this.xLocation = startX;
        slider = new Rectangle(xLocation, YLOCATION, WIDTH, HEIGHT);
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
            

            
            if (ballX < sliderX + zoneWidth) {
                ball.changeXDirection(-1.5);
                ball.reverseYDirection();
                ball.increaseSpeed();
                
                
            }
            
            else if (ballX < sliderX + 2 * zoneWidth) {
                ball.changeXDirection(0);
                ball.reverseYDirection();
                ball.increaseSpeed();
            }
            
            else {
                ball.changeXDirection(1.5);
                ball.reverseYDirection();
                ball.increaseSpeed();
            }
        }
    }
    public void checkPowerUpCollision() {
    	
    }

    public Rectangle getNode() {
        return slider;
    }
}

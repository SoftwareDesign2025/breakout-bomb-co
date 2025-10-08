//Author Ben farmer
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
/*this class is to implement power ups*/
public class PowerUp {
	private static double FALL_SPEED = 7.0;
	private static double WIDTH = 7.0;
	private static double HEIGHT = 7.0;
	//private static COLOR =;
	private static double DROP_RATE= .1;
	private Circle powerUp;
	//change
	
	private int xposition;
	private int yposition;
	
	//if brick is destroyed, drop power up DROP_RATE of the time
	private  dropPowerUp() {
		double chance = Math.random();
		if(chance< DROP_RATE) {
			return new BiggerSlider(x,y);
		}
		return null;
		
	}
	
	public void powerUpSelect() {
		
	}
	
	public void update_position() {
		powerUp.setCenterX(powerUp.getCenterX());
		powerUp.setCenterY(powerUp.getCenterY()+FALL_SPEED);
	}
	
	public void checkCollision('power up with brick') {
		 if (brick.getBoundsInParent().intersects(powerUp.getBall().getBoundsInParent())) {
	            connectWithBall(ball);
	            return pointValue;
		
	}
	
	public void startEffect() {
		
		
	}
	
	
}


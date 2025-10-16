import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class BallPowerUp extends PowerUp{
	 private Screen screen;
	 private Slider slider;
	 private java.util.List<Ball> balls;
	 private Ball spawned;
	 private boolean finished = false;
	 
	 BallPowerUp(double x, double y){
		 super(x,y);
		}
	 
	 void setBallPosition(Screen screen,java.util.List<Ball> balls) {
		 this.screen = screen;
		 this.balls = balls;
	 }
	 
	 void startEffect(SLider slider) {
		 if (screen == null || balls == null || balls.isEmpty()) {
			 finished = true;
			 return;
		 }
	 }
	 
	 ;
	 double bx = one.getBall().getCenterX();
	 double by = one.getBall().getCenterY();
     double r  = one.getBall().getRadius();
     
	 
	 addedBall = new Ball(bx,by,(int)r);
	 addedBall.changeSpeed(1);
	 addedBall.changeYDirection(1);
	 addedBall.changeXDirection(0);
	 
}

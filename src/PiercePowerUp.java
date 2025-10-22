
class PiercePowerUp extends PowerUp{

	private Ball ball;
	private boolean finished = false;
	
	PierceUse(double x, double y){
		super(x,y);
	}
	
	void setBall(Ball b) {
		this.ball = b
		}
	
	void startEffect(Slider slider) {
		if (ball != null) ball.addPierceUse();
		finished = true;
	}

	
	//helper functions
	boolean isFinished() {
		return finished;
		
	}




}
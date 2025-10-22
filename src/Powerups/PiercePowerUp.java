
class PiercePowerUp extends PowerUp {
    private static int charges = 0;          // how many uses the player has
    private static int framesLeft = 0;       // active pierce time remaining
    private static final int PIERCE_FRAMES = 60; // ~1s at 60 FPS
    private Ball ball;
    private boolean finished = false;

    PiercePowerUp(double x, double y) { super(x, y); }

    @Override
    void startEffect(Slider slider) {
        charges++;          // pickup grants a charge
        finished = true;    // this falling object can be removed
        super.stopPowerUp();
    }

    // called from your input handler (e.g., SPACE key)
    static void tryActivate() {
        if (charges > 0 && framesLeft == 0) {
            charges--;
            framesLeft = PIERCE_FRAMES;
        }
    }

    // called once per frame
    static void tickGlobal() {
        if (framesLeft > 0) framesLeft--;
    }

    // Brick uses this to decide whether to skip the bounce
    static boolean isActive() {
        return framesLeft > 0;
    }

    boolean isPowerUpOver() { return finished; }

	public void setBall(Ball ball) {
		this.ball = ball;
		
	}
	
	@Override public PowerUp spawnAt(double x, double y) {
		return new PiercePowerUp(x, y); 
		}
}

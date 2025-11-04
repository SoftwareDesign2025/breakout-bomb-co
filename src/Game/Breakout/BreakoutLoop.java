// Author: Murph Lennemann

package Game.Breakout;
import Game.GameLoop;
import Objects.Breakout.Ball;
import Objects.Breakout.Brick;
import Objects.Breakout.Slider;
import javafx.scene.input.KeyCode;
import java.util.*;
import Objects.*;
import Powerups.*;

public class BreakoutLoop extends GameLoop {
	private ArrayList<PowerUp> powerUpList;
	private final BreakoutLevelMaker LEVEL_MAKER;
	private long lastEasterEgg = 0;
	private final long EASTER_EGG_COOLDOWN = 1000;



	public BreakoutLoop(BreakoutScreen breakoutScreen) {
		super(breakoutScreen);
		this.powerUpList = new ArrayList<>();
		this.LEVEL_MAKER = breakoutScreen.getBreakoutLevelMaker();
		initBall();
	}

	@Override
	public void step() {
		showScreen();
		handleKeyInput();
		if (gameOn()) { return;}
		updateScreen();
		checkLevel();

		checkLives();
	}

	private void updateScreen() {
		ArrayList<Ball> toRemove = new ArrayList<>();
		for (Ball ball : BALLS) {
			updateBall(ball);
			handleSliderCollisions(ball);
			screen.checkBallToWall(ball);
			points += bricks.resolveCollisions(ball);
			spawnPowerUpsFromBricks();
			handlePowerUpPickups();
			if (screen.ballOutOfBounds(ball)) {
				toRemove.add(ball);
			}

		}
		updatePowerUps();
		handleBallRemovals(toRemove);
	}

	@Override
	public boolean gameOn() {
		return (!moving || gameOver);
	}



	private void updateBall(Ball ball) {
		ball.updateBallLocation();
	}

	private void handleSliderCollisions(Ball ball) {
		for (Slider slider : sliderList()) {
			slider.checkSliderCollision(ball);
		}
	}

	private void spawnPowerUpsFromBricks() {
		for (HittableObject h : new ArrayList<>(bricks.getHittableObjects())) {
			Brick b = (Brick) h;
			if (!b.isActive()) {
				PowerUp p = b.getPowerUp();
				if (p != null) {
					PowerUp newPowerUp = p.spawnAt(
							b.getBrick().getX() + b.getBrick().getWidth() / 2.0,
							b.getBrick().getY() + b.getBrick().getHeight() / 2.0
					);
					newPowerUp.onSpawn(screen, BALLS);
					screen.getRoot().getChildren().add(newPowerUp.getNode());
					powerUpList.add(newPowerUp);
					b.setPowerUp(null);
					}
			}
		}
	}

	private void handlePowerUpPickups() {
		ArrayList<Slider> sliders = sliderList();
		for (Slider slider : sliders) {
			for (int i = powerUpList.size() - 1; i >= 0; i--) {
				PowerUp pu = powerUpList.get(i);
				if (pu.getNode().getBoundsInParent().intersects(slider.getNode().getBoundsInParent())) {
					pu.onPickup(sliders);
					screen.getRoot().getChildren().remove(pu.getNode());
				}
			}
		}
		PiercePowerUp.tickGlobal();
	}

	private void updatePowerUps() {
		for (int i = powerUpList.size() - 1; i >= 0; i--) {
			PowerUp pu = powerUpList.get(i);
			pu.update_position();
			if (!pu.isactivated() && pu.getNode().getBoundsInParent().getMinY() > 600) {
				screen.getRoot().getChildren().remove(pu.getNode());
				powerUpList.remove(i);
			}
			pu.tick();
			if (pu.isPowerUpOver()) {
				powerUpList.remove(i);
			}
		}
	}

	private void handleBallRemovals(ArrayList<Ball> toRemove) {
		BALLS.addAll(screen.consumeQueuedBalls());
		for (Ball ball : toRemove) {
			screen.getRoot().getChildren().remove(ball.getBall());
			BALLS.remove(ball);
		}
		if (BALLS.isEmpty()) handleLifeLost();
	}

	private void initBall() {
		double resetBallSpeed = 1;
		double resetXDirection = 0.2;
		double resetYDirection = 2;
		Ball freshBall;
		freshBall = new Ball(10, LEVEL_MAKER.getBallX(), LEVEL_MAKER.getBallY());
		BALLS.add(freshBall);
		screen.getRoot().getChildren().add(freshBall.getBall());
		freshBall.changeSpeed(resetBallSpeed);
		freshBall.changeXDirection(resetXDirection);
		freshBall.changeYDirection(resetYDirection);
	}

	public void handleLifeLost() {
		moving = false;
		lives -= 1;
		initBall();
		for (Slider slider: sliderList()) {
			slider.resetSlider();
		}
		for (PowerUp powerUp: powerUpList) {
			powerUp.stopPowerUp();
			screen.getRoot().getChildren().remove(powerUp.getNode());
		}
		powerUpList.clear();

	}

	@Override
	public void resetLevel() {
		moving = false;
		BALLS.forEach(ball -> screen.getRoot().getChildren().remove(ball.getBall()));
		BALLS.clear();
		powerUpList.forEach(pu -> screen.getRoot().getChildren().remove(pu.getNode()));
		powerUpList.clear();
		screen.loadLevel(level);
		initBall();
		sideMoverList = screen.getSideMoverList();
	}

	@Override
	public void handleKeyInput() {
		if (!gameOver && moving) {
			for (Slider slider : sliderList()) {
				if (pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A)) {
					slider.moveLeft();
				}
				if (pressedKeys.contains(KeyCode.RIGHT)  || pressedKeys.contains(KeyCode.D)) {
					slider.moveRight();
				}
			}
			if (pressedKeys.contains(KeyCode.SPACE)) {
				PiercePowerUp.tryActivate();
			}
			if (pressedKeys.contains(KeyCode.B)) {
				tryLevelSkip();
			}
			handleTestPowerUps();
		}
	}

	private void handleTestPowerUps() {
		double bx = 400;
		double by = 200;
		if (pressedKeys.contains(KeyCode.Z)) {
			tryActivatePowerUp(new BiggerSlider(bx, by));
		}
		if (pressedKeys.contains(KeyCode.X)) {
			tryActivatePowerUp(new BallPowerUp(bx, by));
		}
		if  (pressedKeys.contains(KeyCode.C)) {
			tryActivatePowerUp(new PiercePowerUp(bx, by));
		}
	}

	private void addPowerUp(PowerUp pu) {
		pu.onSpawn(screen, BALLS);
		screen.getRoot().getChildren().add(pu.getNode());
		powerUpList.add(pu);
	}

	@Override
	public boolean levelOver() {
		for (Brick brick: bricks.getBricksList()) {
			if (brick.isActive() && !brick.isUnbreakable()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getFileName() {
		return "BreakoutHighScore.txt";
	}

	@Override
	public void gameOverLogic() {
		super.gameOverLogic();
		moving = false;
	}

	public void tryActivatePowerUp(PowerUp pu) {
		now = System.currentTimeMillis();
		if (now - lastEasterEgg >= EASTER_EGG_COOLDOWN) {
			addPowerUp(pu);
			lastEasterEgg = now;
		}
	}

	public void tryLevelSkip() {
		now = System.currentTimeMillis();
		if (now - lastEasterEgg >= EASTER_EGG_COOLDOWN) {
			bricks.clearObjects(screen);
			lastEasterEgg = now;
		}
	}

	private ArrayList<Slider> sliderList() {
		ArrayList<Slider> sliders = new ArrayList<>();
		for (SideMover sideMover : sideMoverList) {
			sliders.add((Slider) sideMover);
		}
		return sliders;
	}

}
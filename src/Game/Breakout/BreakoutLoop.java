/*
Authors:
Murph Lennemann

 */

package Game.Breakout;
import Game.GameLoop;
import Objects.Breakout.Ball;
import Objects.Breakout.Brick;
import Objects.Breakout.Bricks;
import Objects.Breakout.Slider;
import javafx.scene.input.KeyCode;
import java.util.*;
import Objects.*;
import Powerups.*;

public class BreakoutLoop extends GameLoop {
	private final ArrayList<Ball> BALLS;
	private ArrayList<PowerUp> powerUpList;
	private final BreakoutLevelMaker LEVEL_MAKER;
	private long lastEasterEgg = 0;
	private final long EASTER_EGG_COOLDOWN = 1000;
	private final Bricks bricks;
	private ArrayList<Slider> sliderList;
	private BreakoutScreen breakoutScreen;

	/**
	 * Authors: Murph
	 * @param breakoutScreen
	 */
	public BreakoutLoop(BreakoutScreen breakoutScreen) {
		super(breakoutScreen);
		this.breakoutScreen = breakoutScreen;
		this.BALLS = new ArrayList<>();
		this.bricks = breakoutScreen.getBricks();
		this.powerUpList = new ArrayList<>();
		this.LEVEL_MAKER = breakoutScreen.getBreakoutLevelMaker();
		this.sliderList = breakoutScreen.getSliderList();
		initBall();
	}

	/**
	 * Authors: Murph
	 */
	@Override
	public void step() {
		showScreen();
		handleKeyInput();
		if (gameOn()) { return;}
		updateScreen();
		checkLevel();

		checkLives();
	}

	/**
	 * Authors: Murph
	 */
	private void updateScreen() {
		ArrayList<Ball> toRemove = new ArrayList<>();
		for (Ball ball : BALLS) {
			updateBall(ball);
			handleSliderCollisions(ball);
			breakoutScreen.checkBallToWall(ball);
			points += bricks.resolveCollisions(ball);
			spawnPowerUpsFromBricks();
			handlePowerUpPickups();
			if (breakoutScreen.ballOutOfBounds(ball)) {
				toRemove.add(ball);
			}

		}
		updatePowerUps();
		handleBallRemovals(toRemove);
	}

	/**
	 * Authors: Murph
	 * @return
	 */
	@Override
	public boolean gameOn() {
		return (!moving || gameOver);
	}

	/**
	 * Authors: Murph
	 * @param ball
	 */
	private void updateBall(Ball ball) {
		ball.updateBallLocation();
	}

	/**
	 * Authors: Murph
	 * @param ball
	 */
	private void handleSliderCollisions(Ball ball) {
		for (Slider slider : sliderList) {
			slider.checkSliderCollision(ball);
		}

	}

	/**
	 * Authors: Murph
	 */
	private void spawnPowerUpsFromBricks() {
		for (Brick brick: bricks.getBricksList()) {
			if (!brick.isActive()) {
				PowerUp powerUp = brick.getPowerUp();
				if (powerUp != null) {
					PowerUp newPowerUp = powerUp.spawnAt(
							brick.getBrick().getX() + brick.getBrick().getWidth() / 2.0,
							brick.getBrick().getY() + brick.getBrick().getHeight() / 2.0
					);
					newPowerUp.onSpawn(breakoutScreen, BALLS);
					breakoutScreen.getRoot().getChildren().add(newPowerUp.getNode());
					powerUpList.add(newPowerUp);
					brick.setPowerUp(null);
					}
			}
		}
	}

	/**
	 * Murph
	 */
	private void handlePowerUpPickups() {
		for (Slider slider : sliderList) {
			for (int i = powerUpList.size() - 1; i >= 0; i--) {
				PowerUp powerUp = powerUpList.get(i);
				if (powerUp.getNode().getBoundsInParent().intersects(slider.getNode().getBoundsInParent())) {
					powerUp.onPickup(sliderList);
					breakoutScreen.getRoot().getChildren().remove(powerUp.getNode());
				}
			}
		}
		PiercePowerUp.tickGlobal();
	}

	/**
	 * Author: Murph
	 */
	private void updatePowerUps() {
		for (int i = powerUpList.size() - 1; i >= 0; i--) {
			PowerUp pu = powerUpList.get(i);
			pu.update_position();
			if (!pu.isactivated() && pu.getNode().getBoundsInParent().getMinY() > 600) {
				breakoutScreen.getRoot().getChildren().remove(pu.getNode());
				powerUpList.remove(i);
			}
			pu.tick();
			if (pu.isPowerUpOver()) {
				powerUpList.remove(i);
			}
		}
	}

	/**
	 * Author: Murph
	 * @param toRemove
	 */
	private void handleBallRemovals(ArrayList<Ball> toRemove) {
		BALLS.addAll(breakoutScreen.consumeQueuedBalls());
		for (Ball ball : toRemove) {
			breakoutScreen.getRoot().getChildren().remove(ball.getBall());
			BALLS.remove(ball);
		}
		if (BALLS.isEmpty()) handleLifeLost();
	}

	/**
	 * Authors: Murph
	 */
	private void initBall() {
		double resetBallSpeed = 1;
		double resetXDirection = 0.2;
		double resetYDirection = 2;
		Ball freshBall;
		freshBall = new Ball(10, LEVEL_MAKER.getBallX(), LEVEL_MAKER.getBallY());
		BALLS.add(freshBall);
		breakoutScreen.getRoot().getChildren().add(freshBall.getBall());
		freshBall.changeSpeed(resetBallSpeed);
		freshBall.changeXDirection(resetXDirection);
		freshBall.changeYDirection(resetYDirection);
	}

	/**
	 * Authors: Murph
	 */
	public void handleLifeLost() {
		moving = false;
		lives -= 1;
		initBall();
		for (Slider slider: sliderList) {
			slider.resetSlider();
		}
		for (PowerUp powerUp: powerUpList) {
			powerUp.stopPowerUp();
			breakoutScreen.getRoot().getChildren().remove(powerUp.getNode());
		}
		powerUpList.clear();

	}

	/**
	 * Authors: Murph
	 */
	@Override
	public void resetLevel() {
		moving = false;
		for (Ball ball: BALLS) {
			breakoutScreen.getRoot().getChildren().remove(ball.getBall());
		}
		BALLS.clear();
		for (PowerUp powerUp: powerUpList) {
			breakoutScreen.getRoot().getChildren().remove(powerUp.getNode());
		}
		powerUpList.clear();
		breakoutScreen.loadLevel(level);
		sliderList = breakoutScreen.getSliderList();
		initBall();
	}

	/**
	 * Authors: Murph
	 */
	@Override
	public void handleKeyInput() {
		if (!gameOver && moving) {
			for (Slider slider : sliderList) {
				moveLeftAndRight(slider);
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

	/**
	 * Authors: Murph
	 */
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

	/**
	 * Authors: Murph
	 * @param pu
	 */
	private void addPowerUp(PowerUp pu) {
		pu.onSpawn(breakoutScreen, BALLS);
		breakoutScreen.getRoot().getChildren().add(pu.getNode());
		powerUpList.add(pu);
	}

	/**
	 * Authors: Murph
	 * @return
	 */
	@Override
	public boolean levelOver() {
		for (Brick brick: bricks.getBricksList()) {
			if (brick.isActive() && !brick.isUnbreakable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Authors: Murph
	 * @return
	 */
	@Override
	public String getFileName() {
		return "BreakoutHighScore.txt";
	}

	/**
	 * Authors: Murph
	 * @param pu
	 */
	public void tryActivatePowerUp(PowerUp pu) {
		now = System.currentTimeMillis();
		if (now - lastEasterEgg >= EASTER_EGG_COOLDOWN) {
			addPowerUp(pu);
			lastEasterEgg = now;
		}
	}

	/**
	 * Authors: Murph
	 */
	public void tryLevelSkip() {
		now = System.currentTimeMillis();
		if (now - lastEasterEgg >= EASTER_EGG_COOLDOWN) {
			bricks.clearObjects(breakoutScreen);
			lastEasterEgg = now;
		}
	}
}
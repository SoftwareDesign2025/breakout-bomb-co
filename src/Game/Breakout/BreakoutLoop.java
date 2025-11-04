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
	 * @param breakoutScreen Is the screen object for this game
	 */
	public BreakoutLoop(BreakoutScreen breakoutScreen) {
		super(breakoutScreen, breakoutScreen.getBricks());
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
	 * The overall game running method that runs every frame
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
	 * This method updates things on the screen
	 */
	private void updateScreen() {
		ArrayList<Ball> toRemove = new ArrayList<>();
		for (Ball ball : BALLS) {
			ball.updateBallLocation();
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
	 * @return if the game is running
	 */
	@Override
	public boolean gameOn() {
		return (!moving || gameOver);
	}

	/**
	 * Authors: Murph
	 * Sees if a ball contacts a slider
	 * @param ball The ball being checked
	 */
	private void handleSliderCollisions(Ball ball) {
		for (Slider slider : sliderList) {
			slider.checkSliderCollision(ball);
		}

	}

	/**
	 * Authors: Murph
	 * Checks if a powerUp should be dropped and makes one if so
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
					addPowerUp(newPowerUp);
					brick.setPowerUp(null);

					}
			}
		}
	}


	/**
	 * Authors: Murph
	 * Checks if a powerUp has contacted a slider and activates the powerUp accordingly
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
	 * Authors: Murph
	 * Moves the power up across the screen
	 */

	private void updatePowerUps() {
		for (int i = powerUpList.size() - 1; i >= 0; i--) {
			PowerUp pu = powerUpList.get(i);
			pu.update_position();
			if (!pu.isactivated() && pu.getNode().getBoundsInParent().getMinY() > 600) {
				powerUpList.remove(i);
			}
			pu.tick();
			if (pu.isPowerUpOver()) {
				powerUpList.remove(i);
			}
		}
	}


	/**
	 * Authors: Murph
	 * Removes balls from screen
	 * @param toRemove The list of balls that have left the screen
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
	 * Initializes a new ball
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
	 * Resets game when a life is lost
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
	 * Resets a level including things added to the screen
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
	 * Handles slider movements
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
	 * Handles creating test powerups
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
	 * @param powerUp is a powerUp being added to the screen
	 */
	private void addPowerUp(PowerUp powerUp) {
		powerUp.onSpawn(breakoutScreen, BALLS);
		breakoutScreen.getRoot().getChildren().add(powerUp.getNode());
		powerUpList.add(powerUp);
	}

	/**
	 * Authors: Murph
	 * @return if the level is over
	 */

	@Override
	public boolean levelOver() {
		for (Brick brick: bricks.getBricksList()) {
			if (brick.isActive() && !brick.isUnbreakable()) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Authors: Murph
	 * @return the string with the fileName for the high score
	 */

	@Override
	public String getFileName() {
		return "BreakoutHighScore.txt";
	}

	/**
	 * Authors: Murph
	 * Creates a timer for creating an easter egg power up
	 * @param powerUp a powerUp to be generated
	 */
	public void tryActivatePowerUp(PowerUp powerUp) {
		now = System.currentTimeMillis();
		if (now - lastEasterEgg >= EASTER_EGG_COOLDOWN) {
			addPowerUp(powerUp);
			lastEasterEgg = now;
		}
	}
}

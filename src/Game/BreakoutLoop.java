package Game;
import javafx.scene.input.KeyCode;
import java.util.*;
import Objects.*;
import Powerups.*;

public class BreakoutLoop extends GameLoop {
	private ArrayList<PowerUp> powerUpList;

	public BreakoutLoop(Screen screen) {
		super(screen);
		this.powerUpList = new ArrayList<>();
	}

	@Override
	public void step() {
		screen.displayScoreBoard(highScore, points, lives);
		if (!movingBall || gameOver) return;
		ArrayList<Ball> toRemove = new ArrayList<>();
		for (Ball ball : BALLS) {
			updateBall(ball);
			handleSliderCollisions(ball);
			screen.checkBallToWall(ball);
			points += bricks.checkBrickCollisions(ball);
			spawnPowerUpsFromBricks();
			handlePowerUpPickups();
			updatePowerUps();
			if (screen.ballOutOfBounds(ball)) toRemove.add(ball);
		}
		handleBallRemovals(toRemove);
		checkLevelAndLives();
	}

	private void updateBall(Ball ball) {
		ball.updateBallLocation();
	}

	private void handleSliderCollisions(Ball ball) {
		for (Slider slider : sliderList) {
			slider.checkSliderCollision(ball);
		}
	}

	private void spawnPowerUpsFromBricks() {
		for (Brick b : new ArrayList<>(bricks.getBricks())) {
			if (b.isActive()) continue;
			PowerUp p = b.getPowerUp();
			if (p == null) continue;
			PowerUp newPowerUp = p.spawnAt(
					b.getBrick().getX() + b.getBrick().getWidth() / 2.0,
					b.getBrick().getY() + b.getBrick().getHeight() / 2.0
			);
			if (newPowerUp instanceof BallPowerUp) {
				((BallPowerUp) newPowerUp).setBallPosition(screen, BALLS);
			}
			screen.getRoot().getChildren().add(newPowerUp.getNode());
			powerUpList.add(newPowerUp);
			b.setPowerUp(null);
		}
	}

	private void handlePowerUpPickups() {
		for (Slider s : sliderList) {
			for (int i = powerUpList.size() - 1; i >= 0; i--) {
				PowerUp pu = powerUpList.get(i);
				if (pu.getNode().getBoundsInParent().intersects(s.getNode().getBoundsInParent())) {
					pu.onPickup(sliderList);
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
				continue;
			}
			if (pu instanceof BiggerSlider bs) {
				bs.tick();
				if (bs.isPowerUpOver()) powerUpList.remove(i);
			} else if (pu.isPowerUpOver()) {
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
		if (BALLS.isEmpty()) resetBall();
	}

	private void initBall() {
		freshBall = new Ball(10, LEVEL_MAKER.getBallX(), LEVEL_MAKER.getBallY());
		BALLS.add(freshBall);
		screen.getRoot().getChildren().add(freshBall.getBall());
		freshBall.changeSpeed(RESET_BALL_SPEED);
		freshBall.changeXDirection(RESET_X_DIRECTION);
		freshBall.changeYDirection(RESET_Y_DIRECTION);
	}

	public void resetBall() {
		movingBall = false;
		lives -= 1;
		initBall();
	}

	@Override
	public void resetLevel() {
		movingBall = false;
		BALLS.forEach(ball -> screen.getRoot().getChildren().remove(ball.getBall()));
		BALLS.clear();
		powerUpList.forEach(pu -> screen.getRoot().getChildren().remove(pu.getNode()));
		powerUpList.clear();
		screen.loadLevel(level);
		initBall();
		sliderList = screen.getSlider();
	}

	@Override
	public void handleKeyInput(KeyCode code) {
		if (!gameOver) {
			for (Slider slider : sliderList) {
				slider.handleMovement(code);
			}
		}
		if (code == KeyCode.B) clearBricks();
		if (code == KeyCode.SPACE) PiercePowerUp.tryActivate();
		handleTestPowerUps(code);
	}

	private void clearBricks() {
		for (Brick brick : bricks.getBricks()) {
			screen.getRoot().getChildren().remove(brick.getBrick());
		}
		bricks.getBricks().clear();
	}

	private void handleTestPowerUps(KeyCode code) {
		double bx = 400, by = 200;
		if (code == KeyCode.Z) addPowerUp(new BiggerSlider(bx, by));
		else if (code == KeyCode.X) addPowerUp(new BallPowerUp(bx, by));
		else if (code == KeyCode.C) addPowerUp(new PiercePowerUp(bx, by));
	}

	private void addPowerUp(PowerUp pu) {
		if (pu instanceof BallPowerUp bpu) {
			bpu.setBallPosition(screen, BALLS);
		}
		screen.getRoot().getChildren().add(pu.getNode());
		powerUpList.add(pu);
		System.out.println("[TEST] Spawned. " + pu.getClass().getSimpleName());
	}
}
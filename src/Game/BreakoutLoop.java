// Author: Murph Lennemann

package Game;
import javafx.scene.input.KeyCode;
import java.util.*;
import Objects.*;
import Powerups.*;

public class BreakoutLoop extends GameLoop {
	private ArrayList<PowerUp> powerUpList;

	private final BreakoutLevelMaker LEVEL_MAKER;

	public BreakoutLoop(BreakoutScreen breakoutScreen) {
		super(breakoutScreen);
		this.powerUpList = new ArrayList<>();
		this.LEVEL_MAKER = breakoutScreen.getBreakoutLevelMaker();
		initBall();
	}

	@Override
	public void step() {
		showScreen();
		if (gameOn()) { return;}
		ArrayList<Ball> toRemove = updateScreen();
		checkLevel();
		handleBallRemovals(toRemove);
		checkLives();
	}

	private ArrayList<Ball> updateScreen() {
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
		return toRemove;
	}

	private boolean gameOn() {
		return (!movingBall || gameOver);
	}



	private void updateBall(Ball ball) {
		ball.updateBallLocation();
	}

	private void handleSliderCollisions(Ball ball) {
		for (SideMover sideMover : sideMoverList) {
			Slider slider = (Slider) sideMover;
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
		ArrayList<Slider> sliders = new ArrayList<>();
		for (SideMover sm : sideMoverList) {
			sliders.add((Slider) sm);
		}
		for (Slider s : sliders) {
			for (int i = powerUpList.size() - 1; i >= 0; i--) {
				PowerUp pu = powerUpList.get(i);
				if (pu.getNode().getBoundsInParent().intersects(s.getNode().getBoundsInParent())) {
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
		if (BALLS.isEmpty()) resetBall();
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
		sideMoverList = screen.getSideMoverList();
	}

	@Override
	public void handleKeyInput(KeyCode code) {
		if (!gameOver) {
			for (SideMover sideMover : sideMoverList) {
				Slider slider = (Slider) sideMover;
				slider.handleMovement(code);
			}
		}
		if (code == KeyCode.B) bricks.clearObjects(screen);
		if (code == KeyCode.SPACE) PiercePowerUp.tryActivate();
		handleTestPowerUps(code);
	}

	private void handleTestPowerUps(KeyCode code) {
		double bx = 400, by = 200;
		if (code == KeyCode.Z) addPowerUp(new BiggerSlider(bx, by));
		else if (code == KeyCode.X) addPowerUp(new BallPowerUp(bx, by));
		else if (code == KeyCode.C) addPowerUp(new PiercePowerUp(bx, by));
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
}
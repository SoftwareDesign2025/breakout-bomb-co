package Game;
import javafx.scene.input.KeyCode;
import java.util.Scanner;
import Objects.Ball;
import Objects.BallPowerUp;
import Objects.Brick;
import Objects.Bricks;
import Objects.Slider;
import Powerups.BiggerSlider;
import Powerups.PiercePowerUp;
import Powerups.PowerUp;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameLoop {
	private Ball freshBall;
	private final ArrayList<Ball> BALLS;
	private final Screen screen;
	private ArrayList<Slider> sliderList;
	private ArrayList<PowerUp> powerUpList;
	private int lives = 5;
	private int points = 0;
	private int highScore;
	private int level = 1;
	private boolean movingBall = false;
	private final double RESET_BALL_SPEED = 1;
    private final double RESET_X_DIRECTION = 0.2;
    private  final double RESET_Y_DIRECTION = 2;
    private boolean gameOver = false;
    private final LevelMaker LEVEL_MAKER;
    private final Bricks bricks;
    
	
	public GameLoop( Screen screen) {
        this.screen = screen;
        this.highScore = getHighScore();
        LEVEL_MAKER = screen.getLevelMaker();
        screen.loadLevel(level);
        this.sliderList = screen.getSlider();
        this.BALLS = new ArrayList<>();
        this.powerUpList = new ArrayList<>();
        freshBall = new Ball(10, LEVEL_MAKER.getBallX(), LEVEL_MAKER.getBallY());
        BALLS.add(freshBall);
        screen.getRoot().getChildren().add(freshBall.getBall());
        freshBall.changeSpeed(RESET_BALL_SPEED);
        freshBall.changeXDirection(RESET_X_DIRECTION);
        freshBall.changeYDirection(RESET_Y_DIRECTION);
        bricks = screen.getBricks();
	}
	
	public void handleKeyInput(KeyCode code) {
		if (!gameOver) {
			for (Slider slider : sliderList) {
			    slider.handleMovement(code);
			}
		}
		if (code == KeyCode.B) {
			for (Brick brick : bricks.getBricks()) {
		        screen.getRoot().getChildren().remove(brick.getBrick());
		    }
			bricks.getBricks().clear();
			
		}
		if (code == KeyCode.SPACE) {
		    PiercePowerUp.tryActivate();
		}
		// in GameLoop.handleKeyInput or a temporary mouse handler:
		// --- TEMPORARY TEST POWER-UP KEYS ---

		// B = Spawn Bigger Slider power-up
		if (code == KeyCode.Z) {
		    double bx = 400;
		    double by = 200;
		    PowerUp pu = new BiggerSlider(bx, by);
		    if (powerUpList == null) powerUpList = new ArrayList<>();
		    screen.getRoot().getChildren().add(pu.getNode());
		    powerUpList.add(pu);
		    System.out.println("[TEST] Spawned BiggerSlider at (" + bx + ", " + by + ")");
		}

		// N = Spawn a second ball immediately (BallPowerUp)
		if (code == KeyCode.X) {
		    double bx = 400;
		    double by = 200;
		    BallPowerUp pu = new BallPowerUp(bx, by);
		    pu.setBallPosition(screen, BALLS);
		    if (powerUpList == null) powerUpList = new ArrayList<>();
		    screen.getRoot().getChildren().add(pu.getNode());
		    powerUpList.add(pu);
		    System.out.println("[TEST] Spawned BallPowerUp at (" + bx + ", " + by + ")");
		}

		// M = Spawn Pierce Power-up (pickup will add charge)
		if (code == KeyCode.C) {
		    double bx = 400;
		    double by = 200;
		    PowerUp pu = new PiercePowerUp(bx, by);
		    if (powerUpList == null) powerUpList = new ArrayList<>();
		    screen.getRoot().getChildren().add(pu.getNode());
		    powerUpList.add(pu);
		    System.out.println("[TEST] Spawned PiercePowerUp at (" + bx + ", " + by + ")");
		}

	


    }
	
	public void step() {
		screen.displayScoreBoard(highScore, points, lives);
		if (movingBall && !gameOver) {
			ArrayList<Ball> toRemove = new ArrayList<>();
			for (Ball ball: BALLS) {
				ball.updateBallLocation();
				for (Slider slider: sliderList) {
					slider.checkSliderCollision(ball);
				}
				screen.checkBallToWall(ball);
				points += bricks.checkBrickCollisions(ball);
				// After brick collisions, spawn power-ups from destroyed bricks
				for (Brick b : new ArrayList<>(bricks.getBricks())) {
					if (!b.isBrickActive() && b.getPowerUp() != null) {
						PowerUp p = b.getPowerUp().spawnAt(
								b.getBrick().getX() + b.getBrick().getWidth() / 2.0,
								b.getBrick().getY() + b.getBrick().getHeight() / 2.0
						);
						if (p instanceof BallPowerUp) {
							((BallPowerUp) p).setBallPosition(screen, BALLS);
						}

						screen.getRoot().getChildren().add(p.getNode());
						powerUpList.add(p);

						// Prevent future spawns from this same brick
						b.setPowerUp(null);
					}
				}
				 
				 for (Slider s : sliderList) {
					 for (int i = powerUpList.size() - 1; i >= 0; i--) {
						 PowerUp pu = powerUpList.get(i);
						 if (pu.getNode().getBoundsInParent().intersects(s.getNode().getBoundsInParent())) {
							 pu.onPickup(sliderList);                          // start the effect
							 screen.getRoot().getChildren().remove(pu.getNode()); // hide the circle
							 // DO NOT remove pu from the list here — it still needs to tick
						 }
					 }
					}
				 PiercePowerUp.tickGlobal();

				 for (int i = powerUpList.size() - 1; i >= 0; i--) {
					    PowerUp pu = powerUpList.get(i);

					    // falling (only if not yet picked up)
					    pu.update_position();

					    // if never picked up, and it falls off the screen, remove it
					    if (!pu.isactivated() && pu.getNode().getBoundsInParent().getMinY() > 600) {
					        screen.getRoot().getChildren().remove(pu.getNode());
					        powerUpList.remove(i);
					        continue;
					    }

					    // drive child timers / complete effects
					    if (pu instanceof BiggerSlider) {
					        BiggerSlider biggerSlider = (BiggerSlider) pu;
					        biggerSlider.tick();
					        if (biggerSlider.isPowerUpOver()) {
					            powerUpList.remove(i);
					        }
					    } else if (pu instanceof BallPowerUp) {
					        // BallPowerUp finishes instantly after spawning the ball (see class below)
					        if (pu.isPowerUpOver()) {
					            powerUpList.remove(i);
					        }
					    } else if (pu instanceof PiercePowerUp) {
					        // PierceCharge grants a charge immediately; it’s done right away
					        if (pu.isPowerUpOver()) {
					            powerUpList.remove(i);
					        }
					    }
					}

				
				if (screen.ballOutOfBounds(ball)) {
					toRemove.add(ball);
				}
			}
			BALLS.addAll(screen.consumeQueuedBalls());

			for (Ball ball : toRemove) {
	            screen.getRoot().getChildren().remove(ball.getBall());
	            BALLS.remove(ball);
	        }
			
			if (BALLS.isEmpty()) {
				resetBall();
			}
			if (lives == 0) {
				gameOverLogic();
				screen.gameOverScreen();
			}
			int activeCount = 0;
			for (Brick brick: bricks.getBricks()) {
			    if (brick.isBrickActive() && !brick.isUnbreakable()) {
			        activeCount++;
			        break;
			    }
			}
			

			if (activeCount == 0) {
				level++;
				if (level <= 3) {
					resetLevel();
				}
				else {
					gameOverLogic();
					screen.gameWinScreen();
				}
				
			}
		}
		
	}
	
	public void startMoving() {
		movingBall = true;
	}
	
	public void resetBall() {
		movingBall = false;
		lives -= 1;
		freshBall = new Ball(10, LEVEL_MAKER.getBallX(), LEVEL_MAKER.getBallY());
	    freshBall.changeSpeed(RESET_BALL_SPEED);
	    freshBall.changeXDirection(RESET_X_DIRECTION);
	    freshBall.changeYDirection(RESET_Y_DIRECTION);
	    screen.getRoot().getChildren().add(freshBall.getBall());
		BALLS.add(freshBall);
	}
	
	public void gameOverLogic()  {
		gameOver = true;
		movingBall = false;
		if (points > highScore) {
			setHighScore();
		}
	}
	
	private int getHighScore() {
		try (Scanner in = new Scanner(new File("HighScore.txt"))) {
		    highScore = in.nextInt();
		} catch (IOException e) {
		    highScore = 0;
		}
		return highScore;
	}

	
	public void setHighScore() {
	    try (PrintWriter out = new PrintWriter("HighScore.txt")) {
	        out.println(points);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void resetLevel() {
		movingBall = false;
		for (Ball ball: BALLS) {
			screen.getRoot().getChildren().remove(ball.getBall());
		}
		BALLS.clear();
		for (PowerUp pu: powerUpList) {
			screen.getRoot().getChildren().remove(pu.getNode());
		}
		powerUpList.clear();
		screen.loadLevel(level);
		freshBall = new Ball(10, LEVEL_MAKER.getBallX(), LEVEL_MAKER.getBallY());
		freshBall.changeSpeed(RESET_BALL_SPEED);
		freshBall.changeXDirection(RESET_X_DIRECTION);
		freshBall.changeYDirection(RESET_Y_DIRECTION);
		screen.getRoot().getChildren().add(freshBall.getBall());
		BALLS.add(freshBall);
		sliderList = screen.getSlider();
	}


}
        


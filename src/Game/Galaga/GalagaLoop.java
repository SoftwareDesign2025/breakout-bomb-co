
/*
Authors:
Murph Lennemann

 */

package Game.Galaga;


import Game.GameLoop;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;

import javafx.scene.input.KeyCode;

public class GalagaLoop extends GameLoop {
    private final GalagaEnemies enemies;

    private final Lasers lasers;
    private final CollisionDetector collisionDetector;
    private final Ship ship;

    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen);
        galagaScreen.loadLevel(0);
        this.enemies = galagaScreen.getEnemies();
        this.lasers = galagaScreen.getLasers();
        //this.ship = ship.getShip();
        this.collisionDetector = new CollisionDetector(lasers, enemies);
        this.ship = galagaScreen.getShip();
    }

    @Override
    public void step() {
        showScreen();
        runGame();
        checkLives();
        checkWin();
        lasers.update();
        points += collisionDetector.checkLaserEnemyCollisions();
        
        Laser enemyLaser = enemies.tryShoot();
        if (enemyLaser != null) {
            lasers.addLaser(enemyLaser);
        }
        if (collisionDetector.checkLaserShipCollisions(ship)) {
            lives--;
            System.out.println("Ship hit! Lives remaining: " + lives);
        }
    }

    @Override
    public void handleKeyInput(KeyCode code) {
        if (gameOver) return;
        for (SideMover sideMover : sideMoverList) {
            Ship ship = (Ship) sideMover;
            ship.handleMovement(code);
            ship.shootLaser(code);

            if (code == KeyCode.SPACE) {             
                Laser laser = new Laser(
                    ship.getShip().getLayoutX() + ship.getShip().getFitWidth() / 2,
                    ship.getShip().getLayoutY(),
                    true
                );
                lasers.addLaser(laser);
            } 
        }
        if (code == KeyCode.B) {
            enemies.clearObjects(screen);
        }
       
    }
    

    @Override
    public boolean levelOver() {
        return (enemies.isCleared());
    }


    /**
     * Authors: Murph
     * handles the game portion
     */

    private void runGame() {
        enemies.drop();
        lives -= enemies.enemiesReachedBottom();
    }


    /**
     * Authors: Murph
     * @return gets the file name of the High Score
     */

    public String getFileName() {
        return "GalagaHighScore.txt";
    }


    @Override
    public void resetLevel(){}

    @Override
    public void startMoving(){}

	
    private void checkWin() {
    	if (levelOver()) {
    		gameOverLogic();
    		screen.gameWinScreen();
    	}

    /**
     * Authors: Murph
     * resets the level when level is cleared
     */
    @Override
    public void resetLevel(){}

    /**
     * Authors: Murph
     */
    @Override
    public void startMoving(){
        super.startMoving();
        enemies.drop();
    }

    /**
     * Authors: Murph
     * @return if the game is still being played
     */
    @Override
    public boolean gameOn() {
        return (!moving || gameOver);
    }

    /**
     * Authors: Murph
     * Hanldes what happens on button press
     */
    @Override
    public void handleKeyInput() {
        moveLeftAndRight(ship);
        if (pressedKeys.contains(KeyCode.SPACE)) {
            lastShot(ship);
        }
    }

    /**
     * Authors: Murph
     * Handles shot cooldown
     * @param ship the ship on the screen
     */
    public void lastShot(Ship ship) {
        int laserCooldown = 300;
        now =  System.currentTimeMillis();
        if (now - lastShotTime >= laserCooldown) {
            ship.shootLaser();
            lastShotTime = now;
        }
    }
}

package Game;


import Objects.Ship;
import Objects.GalagaEnemies;
import Objects.SideMover;
import Objects.Lasers;
import Objects.Laser;
import Objects.Ship;
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

    private void runGame() {
        enemies.drop();
        lives -= enemies.enemiesReachedBottom();
    }

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
    }
}

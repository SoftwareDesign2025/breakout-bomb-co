package Game.Galaga;


import Game.GameLoop;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import javafx.scene.input.KeyCode;

public class GalagaLoop extends GameLoop {
    private final GalagaEnemies enemies;
    private long lastShotTime = 0;
    private final Ship ship;

    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen);
        galagaScreen.loadLevel(2);
        this.enemies = galagaScreen.getEnemies();
        this.ship = galagaScreen.getShip();
    }

    @Override
    public void step() {
        showScreen();
        if (moving) {
            runGame();
            handleKeyInput();
        }
        checkLives();
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
    public void startMoving(){
        super.startMoving();
        enemies.drop();
    }

    @Override
    public boolean gameOn() {
        moving = true;
        return moving;
    }

    @Override
    public void handleKeyInput() {
        moveLeftAndRight(ship);
        if (pressedKeys.contains(KeyCode.SPACE)) {
            lastShot(ship);
        }

    }

    public void lastShot(Ship ship) {
        int laserCooldown = 300;
        now =  System.currentTimeMillis();
        if (now - lastShotTime >= laserCooldown) {
            ship.shootLaser();
            lastShotTime = now;
        }
    }
}

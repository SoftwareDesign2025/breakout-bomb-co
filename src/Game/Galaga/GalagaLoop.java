package Game.Galaga;


import Game.GameLoop;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import Objects.SideMover;
import javafx.scene.input.KeyCode;

public class GalagaLoop extends GameLoop {
    private final GalagaEnemies enemies;
    private long lastShotTime = 0;
    private final long LASER_COOLDOWN = 300;

    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen);
        galagaScreen.loadLevel(0);
        this.enemies = galagaScreen.getEnemies();
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
        for (SideMover sideMover : sideMoverList) {
            Ship ship = (Ship) sideMover;
            if (pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A)) {
                ship.moveLeft();
            }
            if (pressedKeys.contains(KeyCode.RIGHT) || pressedKeys.contains(KeyCode.D)) {
                ship.moveRight();
            }
            if (pressedKeys.contains(KeyCode.SPACE)) {
                lastShot(ship);
            }
        }
    }

    public void lastShot(Ship ship) {
        now =  System.currentTimeMillis();
        if (now - lastShotTime >= LASER_COOLDOWN) {
            ship.shootLaser();
            lastShotTime = now;
        }
    }
}

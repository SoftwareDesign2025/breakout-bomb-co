package Game.Galaga;


import Game.GameLoop;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import Objects.SideMover;
import javafx.scene.input.KeyCode;

public class GalagaLoop extends GameLoop {
    private final GalagaEnemies enemies;

    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen);
        galagaScreen.loadLevel(0);
        this.enemies = galagaScreen.getEnemies();
    }

    @Override
    public void step() {
        showScreen();
        runGame();
        checkLives();
    }

    @Override
    public void handleKeyInput(KeyCode code) {
        if (gameOver) return;
        for (SideMover sideMover : sideMoverList) {
            Ship ship = (Ship) sideMover;
            ship.handleMovement(code);
            ship.shootLaser(code);

        }
        if (code == KeyCode.B) {
            clearHittableObjects();
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

    @Override
    public boolean gameOn() {
        return true;
    }
}

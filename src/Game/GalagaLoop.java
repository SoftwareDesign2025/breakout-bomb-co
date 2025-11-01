package Game;

import Game.Levels.GalagaLevel;
import Objects.Ship;
import Objects.GalagaEnemies;
import javafx.scene.input.KeyCode;

public class GalagaLoop extends GameLoop {
    private final GalagaLevelMaker galagaLevelMaker;
    private final GalagaEnemies enemies;
    private final Ship ship;

    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen);
        this.galagaLevelMaker = galagaScreen.getGalagaLevelMaker();
        galagaScreen.loadLevel();
        this.enemies = galagaScreen.getEnemies();
        this.ship = galagaScreen.getShip();
    }



    @Override
    public void step() {
        screen.displayScoreBoard(highScore, points, lives);
        enemies.drop();
        checkLives();
    }

    @Override
    public void handleKeyInput(KeyCode code) {
        if (gameOver) return;


        ship.handleMovement(code);

        ship.shootLaser(code);

        if (code == KeyCode.B) {
            clearHittableObjects();
        }
    }

    @Override
    public boolean levelOver() {
        return (enemies.isCleared());
    }
}

package Game;

import Game.Levels.GalagaLevel;
import Objects.Ship;
import Objects.GalagaEnemies;
import Objects.SideMover;
import javafx.scene.input.KeyCode;

public class GalagaLoop extends GameLoop {
    private final GalagaLevelMaker galagaLevelMaker;
    private final GalagaEnemies enemies;

    public GalagaLoop(GalagaScreen galagaScreen) {
        super(galagaScreen);
        this.galagaLevelMaker = galagaScreen.getGalagaLevelMaker();
        galagaScreen.loadLevel(0);
        this.enemies = galagaScreen.getEnemies();
    }



    @Override
    public void step() {
        screen.displayScoreBoard(highScore, points, lives);
        enemies.drop();
        lives -= enemies.enemiesReachedBottom();
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
}

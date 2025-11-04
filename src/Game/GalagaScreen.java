package Game;

import Objects.Ship;
import Objects.GalagaEnemies;
import Objects.GalagaEnemy;
import Objects.SideMover;
import Objects.Lasers;
import Objects.Bricks;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GalagaScreen extends Screen {
    private Ship ship;
    private GalagaLevelMaker galagaLevelMaker;
    private List<GalagaEnemy> enemyList;
    private GalagaEnemies enemies;
    private Lasers lasers;

    public GalagaScreen() {
        super();
        Rectangle bg = new Rectangle(0, 0, 800, 600);
        bg.setFill(Color.BLACK);
        root.getChildren().add(0, bg);
        enemyList = new ArrayList<>();
        galagaLevelMaker = new GalagaLevelMaker(root, enemyList);
        enemies = new GalagaEnemies(enemyList,60);//edit difficulty with 2nd parameter
        lasers = new Lasers(root);
        
        loadLevel(1);
    }

    public ArrayList<SideMover> getSideMoverList() { return galagaLevelMaker.getSideMoverList(); }

    @Override
    public void loadLevel(int level) {
        galagaLevelMaker.loadLevel(new Game.Levels.GalagaLevel());
    }

    public Ship getShip() {
        return ship;
    }

    public GalagaEnemies getEnemies() {
        return enemies;
    }
    @Override
    public Bricks getBricks() {
        return null;      }
    
    public Lasers getLasers() {
    	return lasers;
    }

    public GalagaLevelMaker getGalagaLevelMaker() {
        return galagaLevelMaker;
    }
    @Override
    public void gameOverScreen() {
        Text over = new Text(300, 300, "GAME OVER LOSER");
        over.setFill(Color.RED);
        over.setFont(Font.font("Impact", 40));
        root.getChildren().add(over);
    }

    @Override
    public void gameWinScreen() {
        Text win = new Text(300, 300, "YOU WIN!");
        win.setFill(Color.GREEN);
        win.setFont(Font.font("Impact", 40));
        root.getChildren().add(win);
    }
}

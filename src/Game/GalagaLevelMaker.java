package Game;

import Game.Levels.GalagaLevel;
import Objects.GalagaEnemy;
import Objects.Slider;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GalagaLevelMaker {

    private final Group ROOT;
    private final List<GalagaEnemy> ENEMIES;
    private final ArrayList<Slider> SLIDER_LIST = new ArrayList<>();
    private final ArrayList<Rectangle> OUT_OF_BOUNDS_LIST = new ArrayList<>();
    private final ArrayList<javafx.scene.Node> NODE_LIST = new ArrayList<>();
    private final Random RAND = new Random();

    public GalagaLevelMaker(Group root, List<GalagaEnemy> enemiesList) {
        this.ROOT = root;
        this.ENEMIES = enemiesList;
    }

    public List<GalagaEnemy> getEnemies() {
        return ENEMIES;
    }

    public ArrayList<Slider> getSliderList() {
        return SLIDER_LIST;
    }

    public ArrayList<Rectangle> getOutOfBounds() {
        return OUT_OF_BOUNDS_LIST;
    }

    public void resetLevel() {
        if (!NODE_LIST.isEmpty()) {
            ROOT.getChildren().removeAll(NODE_LIST);
            NODE_LIST.clear();
        }
        ENEMIES.clear();
        SLIDER_LIST.clear();
        OUT_OF_BOUNDS_LIST.clear();
    }

    public void addSlider(double startX, double startY) {
        Slider s = new Slider(startX, startY);
        SLIDER_LIST.add(s);
        ROOT.getChildren().add(s.getNode());
        NODE_LIST.add(s.getNode());
    }

    public void addOutOfBounds(double x, double y, double width, double height, Color color) {
        Rectangle r = new Rectangle(x, y, width, height);
        r.setFill(color);
        OUT_OF_BOUNDS_LIST.add(r);
        ROOT.getChildren().add(r);
        NODE_LIST.add(r);
    }

    public void addEnemy(GalagaEnemy enemy) {
        ENEMIES.add(enemy);
        ROOT.getChildren().add(enemy.getEnemy());
        NODE_LIST.add(enemy.getEnemy());
    }

    public void loadLevel(GalagaLevel level) {
        resetLevel();
        level.build(this);
    }
}

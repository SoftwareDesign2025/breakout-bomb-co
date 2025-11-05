/*
Authors:
Murph Lennemann
Gavin Collins

 */
package Game;

import Game.Levels.Level;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.ArrayList;
import Objects.SideMover;


public abstract class LevelMaker {
    protected final Group ROOT;
    protected final ArrayList<Rectangle> OUT_OF_BOUNDS_LIST = new ArrayList<>();
    protected final ArrayList<Node> NODE_LIST = new ArrayList<>();
    protected final Random RAND = new Random();

    /**
     * Authors: Oscar Kardon
     * @param root
     */
    public LevelMaker(Group root) {
        this.ROOT = root;
    }

    /**
     * Authors:
     * @return
     */
    public ArrayList<Rectangle> getOutOfBounds() {
        return OUT_OF_BOUNDS_LIST;
    }


    public abstract void resetLevel();

    /**
     * Authors:
     */
    protected void clearGameObjects() {
        if (!NODE_LIST.isEmpty()) {
            ROOT.getChildren().removeAll(NODE_LIST);
            NODE_LIST.clear();
        }
        OUT_OF_BOUNDS_LIST.clear();
    }

    /**
     * Authors:
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     */
    public void addOutOfBounds(double x, double y, double width, double height, Color color) {
        Rectangle r = new Rectangle(x, y, width, height);
        r.setFill(color);
        OUT_OF_BOUNDS_LIST.add(r);
        ROOT.getChildren().add(r);
        NODE_LIST.add(r);
    }

    /**
     * Authors: Oscar Kardon
     * @param level
     */
    public void loadLevel(Level level) {
        resetLevel();
        level.build(this);
    }
}

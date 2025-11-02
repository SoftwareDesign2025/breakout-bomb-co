// Author: Gavin Collins
package Game;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.ArrayList;
import Objects.SideMover;


public abstract class LevelMaker {
    protected final Group ROOT;
    protected final ArrayList<SideMover> SIDE_MOVER_LIST = new ArrayList<>();
    protected final ArrayList<Rectangle> OUT_OF_BOUNDS_LIST = new ArrayList<>();
    protected final ArrayList<Node> NODE_LIST = new ArrayList<>();
    protected final Random RAND = new Random();

    public LevelMaker(Group root) {
        this.ROOT = root;
    }

    public ArrayList<SideMover> getSideMoverList() {
        return SIDE_MOVER_LIST;
    }

    public ArrayList<Rectangle> getOutOfBounds() {
        return OUT_OF_BOUNDS_LIST;
    }

    public abstract void resetLevel();

    protected void clearGameObjects() {
        if (!NODE_LIST.isEmpty()) {
            ROOT.getChildren().removeAll(NODE_LIST);
            NODE_LIST.clear();
        }
        SIDE_MOVER_LIST.clear();
        OUT_OF_BOUNDS_LIST.clear();
    }


    public void addOutOfBounds(double x, double y, double width, double height, Color color) {
        Rectangle r = new Rectangle(x, y, width, height);
        r.setFill(color);
        OUT_OF_BOUNDS_LIST.add(r);
        ROOT.getChildren().add(r);
        NODE_LIST.add(r);
    }
}

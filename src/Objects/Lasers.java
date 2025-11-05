/*
Authors: Ben Farmer

this class is to track all lasers on the screen
test
 */

package Objects;

import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lasers {
    private List<Laser> laserList;
    private Group root;
    private static final double SCREEN_HEIGHT = 600;
    /**
     * Authors: Farmer
     * constructor
     */
    public Lasers(Group root) {
        this.laserList = new ArrayList<>();
        this.root = root;
    }
    /**
     * Authors: Farmer
     * @param laser
     */
    public void addLaser(Laser laser) {
        laserList.add(laser);
        root.getChildren().add(laser.getRectangle());
    }
    /**
     * Authors: Farmer
     */
    public void update() {
        Iterator<Laser> iterator = laserList.iterator();
        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            
            if (!laser.isActive() || laser.isOffScreen(SCREEN_HEIGHT)) {
                root.getChildren().remove(laser.getRectangle());
                iterator.remove();
            } else {
                laser.update();
            }
        }
    }
    /**
     * Authors: Farmer
     */
    public List<Laser> getActiveLasers() {
        return new ArrayList<>(laserList);
    }
    /**
     * Authors: Farmer
     */
    public void clear() {
        for (Laser laser : laserList) {
            root.getChildren().remove(laser.getRectangle());
        }
        laserList.clear();
    }
}

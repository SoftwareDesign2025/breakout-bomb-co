/*
Authors:
Murph Lennemann
Oscar Kardon
 */

package Objects.Breakout;

import java.util.ArrayList;
import java.util.List;
import Game.Screen;
import Objects.HittableObject;
import Objects.HittableObjects;

public class Bricks implements HittableObjects {
    private List<Brick> bricks;

    /**
     * Authors:Oscar Kardon
     * @param bricks
     */
    public Bricks(List<Brick> bricks){
        if (bricks == null) {
            this.bricks = new ArrayList<>();
        } else {
            this.bricks = bricks;
        }
    }

    /**
     * Authors: Murph
     * @return
     */
    public List<HittableObject> getHittableObjects() {
        return new ArrayList<>(bricks);
    }

    /**
     * Authors: Oscar Kardon
     * @return
     */
    public List<Brick> getBricksList() {
        return bricks;
    }

    /**
     * Authors: Oscar Kardon
     * @param ball
     * @return
     */
    public int resolveCollisions(Ball ball){
        int pointsUpdate = 0;
        for (Brick brick : bricks) {
            if(brick.isActive()) {
                pointsUpdate += brick.detectCollisionWithBall(ball);
                if (pointsUpdate > 0){
                    return pointsUpdate;
                }
            }
        }
        return pointsUpdate;
    }

    /**
     * Authors: Murph
     */
    public void drop(){}

    /**
     * Authors: Murph
     * @return
     */
    public boolean isCleared() {
        return bricks.isEmpty();
    }

    /**
     * Authors: Murph
     * @param screen
     */
    public void clearObjects(Screen screen) {
        for (Brick brick: bricks){
            screen.getRoot().getChildren().remove(brick.getBrick());
        }
        bricks.clear();
    }
}

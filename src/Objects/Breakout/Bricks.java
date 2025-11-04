/*
Authors:
Murph Lennemann

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
     * Authors:
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
     * getter
     * @return returns the list Bricks so they can be iterated through
     */
    public List<HittableObject> getHittableObjects() {
        return new ArrayList<>(bricks);
    }

    /**
     * Authors:
     * @return
     */
    public List<Brick> getBricksList() {
        return bricks;
    }

    /**
     * Authors:
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
     * @return if there are no more enemies left
     */
    public boolean isCleared() {
        return bricks.isEmpty();
    }

    /**
     * Authors: Murph
     * Used for easter egg
     * @param screen the screen from which the objects will be removed
     */
    public void clearObjects(Screen screen) {
        for (Brick brick: bricks){
            screen.getRoot().getChildren().remove(brick.getBrick());
        }
        bricks.clear();
    }
}

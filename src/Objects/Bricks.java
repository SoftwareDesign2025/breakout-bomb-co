package Objects;

import java.util.ArrayList;
import java.util.List;
import Game.Screen;

public class Bricks implements HittableObjects {
    private List<Brick> bricks;

    public Bricks(List<Brick> bricks){
        if (bricks == null) {
            this.bricks = new ArrayList<>();
        } else {
            this.bricks = bricks;
        }
    }

    public List<HittableObject> getHittableObjects() {
        return new ArrayList<>(bricks);
    }

    public List<Brick> getBricksList() {
        return bricks;
    }

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

    public void drop(){}

    public boolean isCleared() {
        return bricks.isEmpty();
    }

    public void clearObjects(Screen screen) {
        for (Brick brick: bricks){
            screen.getRoot().getChildren().remove(brick.getBrick());
        }
        bricks.clear();
    }
}

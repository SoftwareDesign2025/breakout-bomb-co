package Objects;
import java.util.ArrayList;
import java.util.List;
import Game.Screen;

//This class contains the Bricks class, a list of Bricks
public class Bricks implements HittableObjects {
    private List<Brick> bricks;

    public Bricks(List<Brick> bricks){
        this.bricks = bricks;
    }

    public List<HittableObject> getHittableObjects() {
        return new ArrayList<>(bricks);
    }

    public int resolveCollisions(Ball ball){
        int pointsUpdate = 0;
        for (Brick brick : bricks) {
            if(brick.isActive()) {
                pointsUpdate+= brick.detectCollisionWithBall(ball);
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
            screen.getRoot().getChildren().remove(brick);
        }
        bricks.clear();
    }
}


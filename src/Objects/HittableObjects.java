package Objects;
import Game.Screen;

import java.util.List;

public interface HittableObjects {
    List<HittableObject> getHittableObjects();
    void drop();
    int resolveCollisions(Ball ball);
    boolean isCleared();
    void clearObjects(Screen screen);
}

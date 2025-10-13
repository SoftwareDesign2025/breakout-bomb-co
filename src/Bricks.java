import java.util.List;

public class Bricks {
    private List<Brick> bricks;

    public Bricks(List<Brick> bricks){
        this.bricks = bricks;
    }

    public List<Brick> getBricks() { return bricks; }


    public int checkBrickCollisions(Ball ball){
        int pointsUpdate = 0;
        for (Brick brick : bricks) {
            if(brick.isBrickActive()) {
                pointsUpdate += brick.detectCollisionWithBall(ball);
                if (pointsUpdate > 0) {
                    return pointsUpdate;
                }
            }
        }
        return pointsUpdate;
    }
}


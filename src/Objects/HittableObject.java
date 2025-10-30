package Objects;

import Powerups.PowerUp;
import javafx.scene.Node;

public abstract class HittableObject {
    protected Node hittableObject;
    protected boolean active;
    protected int pointValue;
    protected PowerUp powerUp;

    public HittableObject(double startX, double startY, int pointValue, PowerUp powerUp){
            active = true;
            this.pointValue = pointValue;
            this.powerUp = powerUp;
    }

    public Node getHittableObject(){
        return hittableObject;
    }

    public boolean isActive(){
        return active;
    }

    public PowerUp getPowerUp(){
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp){
        this.powerUp= powerUp;
    }


}

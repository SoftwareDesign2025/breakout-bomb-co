package Objects;

import Powerups.PowerUp;

public abstract class HittableObject {
    protected boolean active;
    protected int pointValue;
    protected PowerUp powerUp;

    public HittableObject(double startX, double startY, int pointValue, PowerUp powerUp){
            active = true;
            this.pointValue = pointValue;
            this.powerUp = powerUp;
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

/*
Authors:
Oscar Kardon

 */

package Objects;

import Powerups.PowerUp;
import javafx.scene.Node;

public abstract class HittableObject {
    protected Node hittableObject;
    protected boolean active;
    protected int pointValue;
    protected PowerUp powerUp;

    /**
     * Authors:Oscar Kardon
     * @param startX
     * @param startY
     * @param pointValue
     * @param powerUp
     */
    public HittableObject(double startX, double startY, int pointValue, PowerUp powerUp){
            active = true;
            this.pointValue = pointValue;
            this.powerUp = powerUp;
    }

    /**
     * Authors:Oscar Kardon
     * @return
     */
    public boolean isActive(){
        return active;
    }

    /**
     * Authors:Oscar Kardon
     * @return
     */
    public PowerUp getPowerUp(){
        return powerUp;
    }

    /**
     * Authors:Oscar Kardon
     * @param powerUp
     */
    public void setPowerUp(PowerUp powerUp){
        this.powerUp= powerUp;
    }

    /**
     * Authors:Oscar Kardon
     */
    public void deactivate(){
        active = false;
        hittableObject.setVisible(false);
    }
}

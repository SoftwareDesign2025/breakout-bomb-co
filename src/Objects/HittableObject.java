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
     * Authors:
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
     * Authors:
     * @return
     */
    public Node getHittableObject(){
        return hittableObject;
    }

    /**
     * Authors:
     * @return
     */
    public boolean isActive(){
        return active;
    }

    /**
     * Authors:
     * @return
     */
    public PowerUp getPowerUp(){
        return powerUp;
    }

    /**
     * Authors:
     * @param powerUp
     */
    public void setPowerUp(PowerUp powerUp){
        this.powerUp= powerUp;
    }

    /**
     * Authors:
     * @return
     */
    public boolean isUnbreakable(){
        return false;
    }

    /**
     * Authors:
     */
    public void deactivate(){
        active = false;
        hittableObject.setVisible(false);
    }
}

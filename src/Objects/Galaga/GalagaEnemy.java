/*
Authors:
 */

package Objects.Galaga;

import Objects.HittableObject;
import Powerups.PowerUp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GalagaEnemy extends HittableObject {
    private ImageView imageView;
    private double speed = 0.1;

    /**
     * Authors:
     * @param width
     * @param height
     * @param imagePath
     * @param startX
     * @param startY
     * @param pointValue
     * @param powerUp
     * @param speed
     * @param hits
     */
    public GalagaEnemy(double width, double height, String imagePath, double startX, double startY, int pointValue, PowerUp powerUp, double speed, int hits){

        super(startX, startY, pointValue, powerUp);

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        imageView = new ImageView(image);
        hittableObject = imageView;
        imageView.setLayoutX(startX);
        imageView.setLayoutY(startY);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.speed = speed;

    }

    /**
     * Authors:
     * @return
     */
    public ImageView getEnemy(){
        return imageView;
    }

    /**
     * Authors:
     */
    public void moveDown(){
        imageView.setLayoutY(getEnemy().getLayoutY() + speed);
    }


}

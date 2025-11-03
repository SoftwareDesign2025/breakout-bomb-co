package Objects.Galaga;

import Objects.HittableObject;
import Powerups.PowerUp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GalagaEnemy extends HittableObject {
    private ImageView imageView;
    private double speed = 0.1;


    public GalagaEnemy(double width, double height, String imagePath, double startX, double startY, int pointValue, PowerUp powerUp, double speed){

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

    public ImageView getEnemy(){
        return imageView;
    }

    public void moveDown(){
        imageView.setLayoutY(getEnemy().getLayoutY() + speed);
    }


}

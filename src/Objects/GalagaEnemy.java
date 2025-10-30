package Objects;

import Game.GalagaScreen;
import Powerups.PowerUp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GalagaEnemy extends HittableObject{
    private ImageView imageView;


    public GalagaEnemy(double width, double height, String imagePath, double startX, double startY, int pointValue, PowerUp powerUp){

        super(startX, startY, pointValue, powerUp);

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        imageView = new ImageView(image);
        hittableObject = imageView;
        imageView.setLayoutX(startX);
        imageView.setLayoutY(startY);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

    }

    public ImageView getEnemy(){
        return imageView;
    }
}

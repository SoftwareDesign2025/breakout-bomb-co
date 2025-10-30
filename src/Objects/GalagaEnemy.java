package Objects;

import Game.GalagaScreen;
import Powerups.PowerUp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GalagaEnemy extends HittableObject{
    private ImageView imageView;


    public GalagaEnemy(String imagePath, double startX, double startY, int pointValue, PowerUp powerUp){
        super(startX, startY, pointValue, powerUp);
        Image image = new Image(imagePath);
        imageView = new ImageView(image);
        hittableObject = imageView;
    }

    public ImageView getEnemy(){
        return imageView;
    }
}

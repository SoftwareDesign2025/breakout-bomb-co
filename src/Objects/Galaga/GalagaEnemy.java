/*
Authors: Oscar Kardon

 */

package Objects.Galaga;

import Objects.HittableObject;
import Powerups.PowerUp;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GalagaEnemy extends HittableObject {
    private ImageView imageView;
    private double speed = 0.1;
    private String movementPattern = "default";
    private int lives;

    /**
     * Authors:Oscar Kardon
     * @param width
     * @param height
     * @param imagePath
     * @param startX
     * @param startY
     * @param pointValue
     * @param powerUp
     * @param speed
     * @param lives
     */
    public GalagaEnemy(double width, double height, String imagePath, double startX, double startY, int pointValue, PowerUp powerUp, double speed, int lives){

        super(startX, startY, pointValue, powerUp);

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        imageView = new ImageView(image);
        hittableObject = imageView;
        imageView.setLayoutX(startX);
        imageView.setLayoutY(startY);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.speed = speed;
        this.lives = lives;
    }

    /**
     * Authors:Oscar Kardon
     * @return ImageView of enemy
     */
    public ImageView getEnemy(){
        return imageView;
    }

    /**
     * Authors:Oscar Kardon
     * moves enemy down screen
     */
    public void moveDown(){
        imageView.setLayoutY(getEnemy().getLayoutY() + speed);
    }

    /**
     * Authors:Oscar Kardon
     */
    public void setMovementPattern(String pattern) {
        this.movementPattern = pattern;
    }


    //used to determine movement
    private double angle = 0;
    /**
     * Authors:Oscar Kardon
     */
    public void moveWavey(){
        angle += 0.1;
        double offsetX = Math.sin(angle) * 5;
        imageView.setLayoutX(getEnemy().getLayoutX() + offsetX);
        imageView.setLayoutY(imageView.getLayoutY() + speed);
    }
    /**
     * Authors:Oscar Kardon
     */
    public void movePatterned() {
        if(movementPattern.equals("wavey")){
            moveWavey();
        }
        moveDown();
    }
    /**
     * Authors:Oscar Kardon
     */
    public int getPointValue(){
        return pointValue;
    }
    /**
     * Authors:Oscar Kardon
     */
    public void loseLife(){
        lives--;
    }
    /**
     * Authors:Oscar Kardon
     */
    public int getLives(){
        return lives;
    }
    /**
     * Authors:Oscar Kardon
     */
    public void enemeyHit(){
        imageView.setOpacity(0.5);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(e -> imageView.setOpacity(1.0));
        pause.play();
    }

}

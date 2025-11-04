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
    private String movementPattern = "default";
    private int lives;

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

    public void setMovementPattern(String pattern) {
        this.movementPattern = pattern;
    }

    public String getMovementPattern(){
        return movementPattern;
    }

    private double angle = 0;

    public void moveWavey(){
        angle += 0.1;
        double offsetX = Math.sin(angle) * 5;
        imageView.setLayoutX(getEnemy().getLayoutX() + offsetX);
        imageView.setLayoutY(imageView.getLayoutY() + speed);
    }
    public void movePatterned() {
        if(movementPattern.equals("wavey")){
            moveWavey();
        }
        moveDown();
    }

    public int getPointValue(){
        return pointValue;
    }

    public void loseLife(){
        lives--;
    }
    public int getLives(){
        return lives;
    }
}

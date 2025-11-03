package Objects.Galaga;

import Objects.SideMover;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Ship extends SideMover {

    public static final double HEIGHT = 40;
    private ImageView shipImage;

    public Ship(String imagePath, double startX, double startY) {
        super(startX, startY, 40, 20);
        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        shipImage = new ImageView(image);
        shipImage.setFitWidth(100);
        shipImage.setFitHeight(100);
        shipImage.setLayoutX(startX);
        shipImage.setLayoutY(startY);
        xLocation = startX;
        yLocation = startY;
        updateNode();
    }

    @Override
    protected void stopAtEdges() {
        double halfWidth = shipImage.getFitWidth() / 2;
        if (xLocation < halfWidth) xLocation = halfWidth;
        else if (xLocation > 800 - halfWidth) xLocation = 800 - halfWidth;

        double halfHeight = shipImage.getFitHeight() / 2;
        if (yLocation < halfHeight) yLocation = halfHeight;
        else if (yLocation > 600 - halfHeight) yLocation = 600 - halfHeight;
    }


    @Override
    protected void updateNode() {
        shipImage.setLayoutX(xLocation - shipImage.getFitWidth() / 2);
        shipImage.setLayoutY(yLocation - shipImage.getFitHeight() / 2);
    }




    public ImageView getShip() {
        return shipImage;
    }

    public void shootLaser(KeyCode code) {
        if (code == KeyCode.SPACE) {

            System.out.println("Shoot laser!");
        }
    }
}

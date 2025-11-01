package Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

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
        shipImage.setLayoutY(startY);    }

    @Override
    protected void stopAtEdges() {
        if (xLocation < width / 2) xLocation = width / 2;
        else if (xLocation + width / 2 > 800) xLocation = 800 - width / 2;
    }

    @Override
    protected void updateNode() {
        double offsetX = xLocation - shipImage.getLayoutBounds().getCenterX();
        shipImage.setTranslateX(offsetX);
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

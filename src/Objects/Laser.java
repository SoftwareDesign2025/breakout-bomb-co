/*
Authors:
Ben Farmer

 */

package Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Laser {
    private Rectangle rectangle;
    private double speed;
    private boolean isPlayerLaser;
    private boolean active;

    // Laser dimensions
    private static final double LASER_WIDTH = 8;
    private static final double LASER_HEIGHT = 20;

    // Speed constants
    private static final double PLAYER_SPEED = 12;
    private static final double ALIEN_SPEED = 4;

    public Laser(double x, double y) {
        this(x, y, true);
    }

    public Laser(double x, double y, boolean isPlayerLaser) {
        this.isPlayerLaser = isPlayerLaser;
        this.speed = isPlayerLaser ? PLAYER_SPEED : ALIEN_SPEED;
        this.active = true;
        
        // Create visual representation
        rectangle = new Rectangle(x - LASER_WIDTH / 2, y, LASER_WIDTH, LASER_HEIGHT);
        
        if (isPlayerLaser) {
            rectangle.setFill(Color.YELLOW);
            rectangle.setStroke(Color.WHITE);
        } else {
            rectangle.setFill(Color.RED);
            rectangle.setStroke(Color.PINK);
        }
    }

    public void update() {
        if (isPlayerLaser) {
            rectangle.setY(rectangle.getY() - speed);
        } else {
            rectangle.setY(rectangle.getY() + speed);
        }
    }

    public boolean isOffScreen(double screenHeight) {
        return rectangle.getY() + rectangle.getHeight() < 0 || 
               rectangle.getY() > screenHeight;
    }

    public boolean collidesWith(double objX, double objY, double objWidth, double objHeight) {
        if (!active) return false;
        
        return rectangle.getX() < objX + objWidth &&
               rectangle.getX() + rectangle.getWidth() > objX &&
               rectangle.getY() < objY + objHeight &&
               rectangle.getY() + rectangle.getHeight() > objY;
    }

    public void destroy() {
        this.active = false;
    }

    // Getters
    public Rectangle getRectangle() { return rectangle; }
    public double getX() { return rectangle.getX(); }
    public double getY() { return rectangle.getY(); }
    public double getWidth() { return rectangle.getWidth(); }
    public double getHeight() { return rectangle.getHeight(); }
    public boolean isActive() { return active; }
    public boolean isPlayerLaser() { return isPlayerLaser; }
}

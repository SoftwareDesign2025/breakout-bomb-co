package Objects;

import javafx.scene.paint.Color;
import java.awt.*;
import javafx.scene.shape.Rectangle;

/**
 * Laser projectile used by both player ship and aliens
 * Travels vertically (up for player, down for enemies)
 */
public class Laser {
    private int x, y;
    private int width, height;
    private int speed;
    private boolean isPlayerLaser;
    private boolean active;
    
    // Laser dimensions
    private static final int LASER_WIDTH = 4;
    private static final int LASER_HEIGHT = 15;
    
    // Speed constants
    private static final int PLAYER_SPEED = 12;
    private static final int ALIEN_SPEED = 6;
    
    /**
     * Constructor for player laser (shoots upward)
     * @param x X position (center of ship)
     * @param y Y position (top of ship)
     */
    public Laser(int x, int y) {
        this(x, y, true);
    }
    
    /**
     * Constructor specifying laser owner
     * @param x X position
     * @param y Y position
     * @param isPlayerLaser true if fired by player, false if by alien
     */
    public Laser(int x, int y, boolean isPlayerLaser) {
        this.x = x - LASER_WIDTH / 2; // Center the laser
        this.y = y;
        this.width = LASER_WIDTH;
        this.height = LASER_HEIGHT;
        this.isPlayerLaser = isPlayerLaser;
        this.speed = isPlayerLaser ? PLAYER_SPEED : ALIEN_SPEED;
        this.active = true;
    }
    
    /* Update laser position based on direction
     */
    public void update() {
        if (isPlayerLaser) {
            y -= speed; // Move upward
        } else {
            y += speed; // Move downward
        }
    }
    
 
    /*public void draw(Graphics g) {
        if (!active) return;
        
        if (isPlayerLaser) {
            // Player laser: bright yellow with white core
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, width, height);
            g.setColor(Color.WHITE);
            g.fillRect(x + 1, y, width - 2, height);
        } else {
            // Alien laser: red with pink core
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
            g.setColor(new Color(255, 100, 100));
            g.fillRect(x + 1, y, width - 2, height);
        }
    }
    */
    
    public boolean isOffScreen(int screenHeight) {
        return y + height < 0 || y > screenHeight;
    }
    
    
    public boolean collidesWith(int objX, int objY, int objWidth, int objHeight) {
        if (!active) return false;
        
        return x < objX + objWidth &&
               x + width > objX &&
               y < objY + objHeight &&
               y + height > objY;
    }
   
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    
 /*   public boolean collidesWith(Rectangle other) {
        return active && getBounds().intersects(other);
    }
   */ 
    /**
     * Deactivate this laser (e.g., after hitting something)
     */
    public void destroy() {
        this.active = false;
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isActive() { return active; }
    public boolean isPlayerLaser() { return isPlayerLaser; }
}
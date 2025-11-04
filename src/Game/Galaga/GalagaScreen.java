
package Game.Galaga;

import Game.Levels.*;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import Objects.Galaga.GalagaEnemy;
import Objects.Lasers;
/*
Authors:
Murph Lennemann

 */

import Game.Screen;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import Objects.Galaga.GalagaEnemy;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.ArrayList;
import java.util.List;

import Game.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GalagaScreen extends Screen {
    private Ship ship;
    private GalagaLevelMaker galagaLevelMaker;
    private List<GalagaEnemy> enemyList;
    private GalagaEnemies enemies;
    // Canvas and variables for star background
    private Canvas starCanvas;
    private List<Star> stars = new ArrayList<>();
    private Random rand = new Random();
    private Lasers lasers = new Lasers(root);





    /**
     * Authors:
     */
    public GalagaScreen(int level) {
        super(level);

        // Black background rectangle
        Rectangle bg = new Rectangle(0, 0, 800, 600);
        bg.setFill(Color.BLACK);
        root.getChildren().add(0, bg);

        // Star canvas (drawn behind gameplay elements)
        starCanvas = new Canvas(800, 600);
        root.getChildren().add(1, starCanvas);

        // Create and start star animation
        initStars(200);
        animateStars();

        // Original Galaga setup
        enemyList = new ArrayList<>();
        galagaLevelMaker = new GalagaLevelMaker(root, enemyList);
        enemies = new GalagaEnemies(enemyList);
        loadLevel(level);
    }



    /**
     * Authors:
     * Simple class to store each star's data
     */
    private class Star {
        double x, y, speed, size;
        Color color;
        Star(double x, double y, double speed, double size, Color color) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.size = size;
            this.color = color;
        }
    }

    /**
     * Authors:
     * Create a list of randomly placed colorful stars
     * @param count
     */
    private void initStars(int count) {
        for (int i = 0; i < count; i++) {
            stars.add(new Star(
                    rand.nextDouble() * 800,
                    rand.nextDouble() * 600,
                    1 + rand.nextDouble() * 3, // vertical speed
                    1 + rand.nextDouble() * 3, // size
                    getRandomColor()            // random color
            ));
        }
    }

    /**
     * Authors:
     * Return a random star color from a set of bright colors
     * @return
     */
    private Color getRandomColor() {
        Color[] colors = {
                Color.WHITE, Color.CYAN, Color.YELLOW, Color.LIGHTPINK,
                Color.LIGHTBLUE, Color.ORANGE, Color.VIOLET, Color.LIME
        };
        return colors[rand.nextInt(colors.length)];
    }

    /**
     * Authors:
     * Continuously animate stars falling to create space effect
     */
    private void animateStars() {
        GraphicsContext gc = starCanvas.getGraphicsContext2D();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 800, 600);
                for (Star s : stars) {
                    s.y += s.speed;
                    if (s.y > 600) {
                        s.y = 0;
                        s.x = rand.nextDouble() * 800;
                        s.color = getRandomColor();
                    }
                    gc.setFill(s.color);
                    gc.fillOval(s.x, s.y, s.size, s.size);
                }
            }
        }.start();
    }

    /**
     * Authors:
     * @param level
     */
    @Override
    public void loadLevel(int level) {
        int levelIndex = level - 1;
        galagaLevelMaker.loadLevel(levels.get(levelIndex));
        ship = galagaLevelMaker.getShip();
    }

    /**
     * Authors: Murph
     * @return returns the ship used in this game
     */

    public Ship getShip() {
        return ship;
    }

    public GalagaEnemies getEnemies() {
        return enemies;
    }

    public Lasers getLasers() {
    	return lasers;
    }

    public GalagaLevelMaker getGalagaLevelMaker() {
        return galagaLevelMaker;
    }


    @Override
    public void gameOverScreen() {
        Text over = new Text(300, 300, "GAME OVER LOSER");
        over.setFill(Color.RED);
        over.setFont(Font.font("Impact", 40));
        root.getChildren().add(over);
    }

    /**
     * Authors: Murph
     * creates a list of levels to be moved through
     */
    public void createLevelList() {
        levels = new ArrayList<>();
        levels.add(new GalagaLevelOne());
        levels.add(new GalagaLevelTwo());
    }


    /**
     * Authors:
     */

    @Override
    public void gameWinScreen() {
        Text win = new Text(300, 300, "YOU WIN!");
        win.setFill(Color.GREEN);
        win.setFont(Font.font("Impact", 40));
        root.getChildren().add(win);
    }
}

package Game.Galaga;

import Game.Levels.*;
import Objects.Galaga.Ship;
import Objects.Galaga.GalagaEnemies;
import Objects.Galaga.GalagaEnemy;
import Objects.Lasers;
import Game.Screen;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private List<double[]> stars = new ArrayList<>(); // Each star: {x, y, speed, size}
    private List<Color> starColors = new ArrayList<>(); // Colors of each star
    private Random rand = new Random();
    private Lasers lasers = new Lasers(root);

    /**
     * Constructor
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
     * Initialize stars with random positions, speeds, sizes, and colors
     * @param count number of stars
     */
    private void initStars(int count) {
        for (int i = 0; i < count; i++) {
            double x = rand.nextDouble() * 800;
            double y = rand.nextDouble() * 600;
            double speed = 1 + rand.nextDouble() * 3; // vertical speed
            double size = 1 + rand.nextDouble() * 3;  // size of star
            stars.add(new double[]{x, y, speed, size});
            starColors.add(getRandomColor()); // assign a random color
        }
    }

    /**
     * Return a random star color from a set of bright colors
     * @return Color
     */
    private Color getRandomColor() {
        Color[] colors = {
                Color.WHITE, Color.CYAN, Color.YELLOW, Color.LIGHTPINK,
                Color.LIGHTBLUE, Color.ORANGE, Color.VIOLET, Color.LIME
        };
        return colors[rand.nextInt(colors.length)];
    }

    /**
     * Continuously animate stars falling to create space effect
     */
    private void animateStars() {
        GraphicsContext gc = starCanvas.getGraphicsContext2D();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 800, 600); // clear previous frame
                for (int i = 0; i < stars.size(); i++) {
                    double[] s = stars.get(i);
                    s[1] += s[2]; // move star down by speed
                    if (s[1] > 600) { // reset star to top if it goes off screen
                        s[1] = 0;
                        s[0] = rand.nextDouble() * 800;
                        starColors.set(i, getRandomColor());
                    }
                    gc.setFill(starColors.get(i));
                    gc.fillOval(s[0], s[1], s[3], s[3]); // draw star
                }
            }
        }.start();
    }

    /**
     * Load a level
     * @param level level number
     */
    @Override
    public void loadLevel(int level) {
        int levelIndex = level - 1;
        galagaLevelMaker.loadLevel(levels.get(levelIndex));
        ship = galagaLevelMaker.getShip();
    }

    /**
     * Return the player ship
     * @return ship
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

    @Override
    public void gameOverScreen() {
        Text over = new Text(300, 300, "GAME OVER LOSER");
        over.setFill(Color.RED);
        over.setFont(Font.font("Impact", 40));
        root.getChildren().add(over);
    }

    /**
     * Create a list of levels to be moved through
     */
    public void createLevelList() {
        levels = new ArrayList<>();
        levels.add(new GalagaLevelOne());
        levels.add(new GalagaLevelTwo());
        levels.add(new GalagaLevelThree());
    }

    @Override
    public void gameWinScreen() {
        Text win = new Text(300, 300, "YOU WIN!");
        win.setFill(Color.GREEN);
        win.setFont(Font.font("Impact", 40));
        root.getChildren().add(win);
    }
}

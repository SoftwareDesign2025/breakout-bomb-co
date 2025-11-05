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

    private final double SCREEN_WIDTH = 800;
    private final double SCREEN_HEIGHT = 600;

    // Canvas and variables for star background
    private Canvas starCanvas;
    private List<Double> starX = new ArrayList<>();
    private List<Double> starY = new ArrayList<>();
    private List<Double> starSpeed = new ArrayList<>();
    private List<Double> starSize = new ArrayList<>();
    private List<Color> starColor = new ArrayList<>();

    private Random rand = new Random();
    private Lasers lasers = new Lasers(root);

    /**
     * Constructor
     */
    public GalagaScreen(int level) {
        super(level);
        createBackground();
        // Original Galaga setup
        enemyList = new ArrayList<>();
        galagaLevelMaker = new GalagaLevelMaker(root, enemyList);
        enemies = new GalagaEnemies(enemyList);
        loadLevel(level);

    }

    protected void createBackground() {
        // Black background rectangle
        int starCount = 200;
        Rectangle background = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        background.setFill(Color.BLACK);
        root.getChildren().add(0, background);

        // Star canvas (drawn behind gameplay elements)
        starCanvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(1, starCanvas);

        // Create and start star animation
        createStars(starCount);
        animateStars();
    }

    /**
     * Author: Gavin
     * Initialize stars with random positions, speeds, sizes, and colors
     *
     */
    protected void createStars(int numberOfStars) {
        double starSpeedVariation = 3;
        double starSizeVariation = 3;
        double starMinSpeed = 1;
        double starMinSize = 2;
        for (int i = 0; i < numberOfStars; i++) {
            starX.add(rand.nextDouble() * SCREEN_WIDTH);
            starY.add(rand.nextDouble() * SCREEN_HEIGHT);
            starSpeed.add(starMinSpeed + rand.nextDouble() * starSpeedVariation); // Random speed between 1–4
            starSize.add(starMinSize + rand.nextDouble() * starSizeVariation); // Random size between 2–5
            starColor.add(getRandomColor());
        }
    }


    /**
     * Author: Gavin
     * Return a random star color from a set of bright colors
     * @return Color
     */
    protected Color getRandomColor() {
        Color[] colors = {
                Color.WHITE, Color.CYAN, Color.YELLOW, Color.LIGHTPINK,
                Color.LIGHTBLUE, Color.ORANGE, Color.VIOLET, Color.LIME
        };
        return colors[rand.nextInt(colors.length)];
    }

    /**
     * Author: Gavin
     * Continuously animate stars falling to create space effect
     */
    // Animates stars falling down the screen
    protected void animateStars() {
        GraphicsContext graphics = starCanvas.getGraphicsContext2D();

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                graphics.clearRect(0, 0, 800, 600);
                // Moves the stars downwards
                for (int i = 0; i < starX.size(); i++) {
                    starY.set(i, starY.get(i) + starSpeed.get(i));

                    // If the star moves off the bottom, reset it to the top
                    if (starY.get(i) > 600) {
                        starY.set(i, 0.0);
                        starX.set(i, rand.nextDouble() * 800); // rand position
                        starColor.set(i, getRandomColor());
                    }

                    // Draw the star
                    graphics.setFill(starColor.get(i));
                    graphics.fillOval(starX.get(i), starY.get(i), starSize.get(i), starSize.get(i));
                }
            }
        }.start();
    }


    /**
     * Load a level
     * @param level level number
     */
    @Override
    protected void loadLevel(int level) {
        int levelIndex = level - 1;
        galagaLevelMaker.loadLevel(levels.get(levelIndex));
        ship = galagaLevelMaker.getShip();
    }

    /**
     * Return the player ship
     * @return ship
     */
    protected Ship getShip() {
        return ship;
    }

    /**
     * Author: Gavin
     *  Returns the enemies
     * @return enemies
     */


    protected GalagaEnemies getEnemies() {
        return enemies;
    }

    protected Lasers getLasers() {
        return lasers;
    }

    /**
     * Author: Gavin
     */

    @Override
    protected void gameOverScreen() {
        Text over = new Text(300, 300, "GAME OVER LOSER");
        over.setFill(Color.RED);
        over.setFont(Font.font("Impact", 40));
        root.getChildren().add(over);
    }

    /**
     * Create a list of levels to be moved through
     */
    @Override
    protected void createLevelList() {
        levels = new ArrayList<>();
        levels.add(new GalagaLevelOne());
        levels.add(new GalagaLevelTwo());
        levels.add(new GalagaLevelThree());
    }

    /**
     * Author: Gavin
     */

    @Override
    protected void gameWinScreen() {
        Text win = new Text(300, 300, "YOU WIN!");
        win.setFill(Color.GREEN);
        win.setFont(Font.font("Impact", 40));
        root.getChildren().add(win);
    }
}

/**
 * Main entry for our arcade
 * Gives the user a menu to choose a game
 * Then loads the chosen game
 *
 * @Authors Murph Lennemann
 */

package Game;

import Game.Breakout.BreakoutLoop;
import Game.Breakout.BreakoutScreen;
import Game.Galaga.GalagaLoop;
import Game.Galaga.GalagaScreen;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SetUpProject extends Application {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final int LEVEL = 1;
    private static final int STAR_COUNT = 150;

    private static final int TITLE_Y = 150;
    private static final int TITLE_FONT_SIZE = 96;

    private static final int MENU_OPTION_WIDTH = 300;
    private static final int MENU_OPTION_HEIGHT = 60;
    private static final int MENU_OPTION_HALF_WIDTH = MENU_OPTION_WIDTH / 2;
    private static final int OPTION_FONT_SIZE = 36;

    private static final int BREAKOUT_OPTION_Y = 300;
    private static final int GALAGA_OPTION_Y = 400;

    private Stage stage;
    private GameLoop gameLoop;

    // Star background fields
    private Canvas starCanvas;
    private List<double[]> stars = new ArrayList<>();
    private Random rand = new Random();

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showMainMenu();
        stage.setTitle("BOMB CO Arcade");
        stage.show();
    }

    private void setUpStarBackground(Group root) {
        // Add star canvas behind everything
        starCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().add(starCanvas);
        initStars(STAR_COUNT);
        animateStars();

    }

    private void addBreakoutButton(Group root) {
        Rectangle breakoutBackground = createBackground(BREAKOUT_OPTION_Y);
        Text breakoutText = new Text("PLAY BREAKOUT");
        handleText(breakoutText, 300);
        breakoutBackground.setOnMouseClicked(e -> startBreakout());
        breakoutText.setOnMouseClicked(e -> startBreakout());
        root.getChildren().addAll(breakoutBackground, breakoutText);
    }

    public void addGalagaButton(Group root) {
        Rectangle galagaBackground = createBackground(GALAGA_OPTION_Y);
        Text galagaText = new Text("PLAY GALAGA");
        handleText(galagaText, 400);
        galagaBackground.setOnMouseClicked(e -> startGalaga());
        galagaText.setOnMouseClicked(e -> startGalaga());
        root.getChildren().addAll(galagaBackground, galagaText);
    }

    private void addMenuTitle(Group root) {
        Text title = new Text("BOMB CO ARCADE");
        title.setFill(Color.BLUE);
        title.setFont(Font.font("Impact", TITLE_FONT_SIZE));
        title.setX(GAME_WIDTH / 11);
        title.setY(TITLE_Y);
        root.getChildren().add(title);
    }

    private void showMainMenu() {
        Group menuRoot = new Group();
        setUpStarBackground(menuRoot);
        addMenuTitle(menuRoot);
        addBreakoutButton(menuRoot);
        addGalagaButton(menuRoot);
        Scene menuScene = new Scene(menuRoot, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);
        stage.setScene(menuScene);
    }

    private void initStars(int count) {
        for (int i = 0; i < count; i++) {
            stars.add(new double[]{
                    rand.nextDouble() * GAME_WIDTH,
                    rand.nextDouble() * GAME_HEIGHT,
                    1 + rand.nextDouble() * 3,
                    1 + rand.nextDouble() * 3
            });
        }
    }

    private void animateStars() {
        GraphicsContext gc = starCanvas.getGraphicsContext2D();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                gc.setFill(Color.DEEPSKYBLUE);
                for (double[] star : stars) {
                    star[1] += star[2];
                    if (star[1] > GAME_HEIGHT) {
                        star[1] = 0;
                        star[0] = rand.nextDouble() * GAME_WIDTH;
                    }
                    gc.fillOval(star[0], star[1], star[3], star[3]);
                }
            }
        }.start();
    }

    private void handleText(Text text, int yPosition) {
        text.setFont(Font.font("Impact", OPTION_FONT_SIZE));
        text.setFill(Color.BLACK);
        text.setX(GAME_WIDTH / 2.0 - text.getLayoutBounds().getWidth() / 2);
        text.setY(yPosition + 45);
    }

    private Rectangle createBackground(int yPosition) {
        Rectangle background = new Rectangle(MENU_OPTION_WIDTH, MENU_OPTION_HEIGHT, Color.BLUE);
        background.setX(GAME_WIDTH / 2.0 - MENU_OPTION_HALF_WIDTH);
        background.setY(yPosition);
        return background;
    }

    private void startBreakout() {
        BreakoutScreen breakoutScreen = new BreakoutScreen(LEVEL);
        gameLoop = new BreakoutLoop(breakoutScreen);
        startGame(breakoutScreen);
    }

    private void startGalaga() {
        GalagaScreen galagaScreen = new GalagaScreen(LEVEL);
        gameLoop = new GalagaLoop(galagaScreen);
        startGame(galagaScreen);
    }

    private void startGame(Screen screen) {
        Scene gameScene = new Scene(screen.getRoot(), GAME_WIDTH, GAME_HEIGHT);
        gameScene.setOnKeyPressed(e -> gameLoop.keyPressed(e.getCode()));
        gameScene.setOnKeyReleased(e -> gameLoop.keyReleased(e.getCode()));
        gameScene.setOnMouseClicked(e -> gameLoop.startMoving());
        stage.setScene(gameScene);
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> gameLoop.step());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public static void runGame() {
        launch();
    }
}

/*
Authors:
Murph Lennemann
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

    private void showMainMenu() {
        Group menuRoot = new Group();

        // Add star canvas behind everything
        starCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        menuRoot.getChildren().add(starCanvas);
        initStars(150);
        animateStars();

        Rectangle breakoutBackground = createBackground(300);
        Rectangle galagaBackground = createBackground(400);

        Text title = new Text("BOMB CO ARCADE");
        title.setFill(Color.BLUE);
        title.setFont(Font.font("Impact", 96));
        title.setX(GAME_WIDTH / 11);
        title.setY(150);
        menuRoot.getChildren().add(title);

        Text breakoutText = new Text("PLAY BREAKOUT");
        handleText(breakoutText, 300);
        breakoutBackground.setOnMouseClicked(e -> startBreakout());
        breakoutText.setOnMouseClicked(e -> startBreakout());
        menuRoot.getChildren().addAll(breakoutBackground, breakoutText);

        Text galagaText = new Text("PLAY GALAGA");
        handleText(galagaText, 400);
        galagaBackground.setOnMouseClicked(e -> startGalaga());
        galagaText.setOnMouseClicked(e -> startGalaga());
        menuRoot.getChildren().addAll(galagaBackground, galagaText);

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

    public void handleText(Text text, int yNum) {
        text.setFont(Font.font("Impact", 36));
        text.setFill(Color.BLACK);
        text.setX(GAME_WIDTH / 2.0 - text.getLayoutBounds().getWidth() / 2);
        text.setY(yNum + 45);
    }

    public Rectangle createBackground(int yInt) {
        Rectangle background = new Rectangle(300, 60, Color.BLUE);
        background.setX(GAME_WIDTH / 2.0 - 150);
        background.setY(yInt);
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

    public void startGame(Screen screen) {
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

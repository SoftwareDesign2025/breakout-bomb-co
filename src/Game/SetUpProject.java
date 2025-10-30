package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import Objects.Ball;

public class SetUpProject extends Application {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private Stage stage;
    private GameLoop gameLoop;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showMainMenu();
        stage.setTitle("BOMB CO Arcade");
        stage.show();
    }

    private void showMainMenu() {
        Group menuRoot = new Group();

        Rectangle breakoutBackground = new Rectangle(300, 60, Color.BLUE);
        breakoutBackground.setX(GAME_WIDTH / 2.0 - 150);
        breakoutBackground.setY(300);

        Text title = new Text("BOMB CO ARCADE");
        title.setFill(Color.BLUE);
        title.setFont(Font.font("Impact", 96));
        title.setX(GAME_WIDTH / 11);
        title.setY(150);
        menuRoot.getChildren().add(title);

        Text breakoutText = new Text("PLAY BREAKOUT");
        breakoutText.setFont(Font.font("Impact", 36));
        breakoutText.setFill(Color.BLACK);
        breakoutText.setX(GAME_WIDTH / 2.0 - breakoutText.getLayoutBounds().getWidth() / 2);
        breakoutText.setY(300 + 45);
        breakoutBackground.setOnMouseClicked(e -> startBreakout());
        breakoutText.setOnMouseClicked(e -> startBreakout());
        menuRoot.getChildren().addAll(breakoutBackground, breakoutText);

        Rectangle galagaBackground = new Rectangle(300, 60, Color.BLUE);
        galagaBackground.setX(GAME_WIDTH / 2.0 - 150);
        galagaBackground.setY(400);

        Text galagaText = new Text("PLAY GALAGA");
        galagaText.setFont(Font.font("Impact", 36));
        galagaText.setFill(Color.BLACK);
        galagaText.setX(GAME_WIDTH / 2.0 - galagaText.getLayoutBounds().getWidth() / 2);
        galagaText.setY(400 + 45);
        galagaBackground.setOnMouseClicked(e -> startGalaga());
        galagaText.setOnMouseClicked(e -> startGalaga());
        menuRoot.getChildren().addAll(galagaBackground, galagaText);

        Scene menuScene = new Scene(menuRoot, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);
        stage.setScene(menuScene);
    }

    private void startBreakout() {
        Ball ball = new Ball(10, 400, 400);
        Screen screen = new Screen(ball);
        gameLoop = new BreakoutLoop(screen);
        Scene gameScene = new Scene(screen.getRoot(), GAME_WIDTH, GAME_HEIGHT);
        gameScene.setOnKeyPressed(e -> gameLoop.handleKeyInput(e.getCode()));
        gameScene.setOnMouseClicked(e -> gameLoop.startMoving());
        stage.setScene(gameScene);
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> gameLoop.step());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void startGalaga() {
        Ball ball = new Ball(10, 400, 400);
        Screen screen = new Screen(ball);
        gameLoop = new GalagaLoop(screen);
        Scene gameScene = new Scene(screen.getRoot(), GAME_WIDTH, GAME_HEIGHT);
        gameScene.setOnKeyPressed(e -> gameLoop.handleKeyInput(e.getCode()));
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

/*
Authors:
Murph Lennemann

 */

package Game;

import Game.Breakout.BreakoutLoop;
import Game.Breakout.BreakoutScreen;
import Game.Galaga.GalagaLoop;
import Game.Galaga.GalagaScreen;
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

public class SetUpProject extends Application {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private Stage stage;
    private GameLoop gameLoop;

    /**
     * Authors:
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showMainMenu();
        stage.setTitle("BOMB CO Arcade");
        stage.show();
    }
    // Starting at the method allows you to see the menu and has the buttons that allow the game to start, with the methods
    // below. You can add more buttons or change anything in this method as long as you start from this method

    /**
     * Authors: Murph
     */
    private void showMainMenu() {
        Group menuRoot = new Group();


        // Background for the breakout and galaga button, can change color and size here
        Rectangle breakoutBackground = createBackground(300);

        Rectangle galagaBackground = createBackground(400);


        // Adds the top text and centers and lets you change font, size and color
        Text title = new Text("BOMB CO ARCADE");
        title.setFill(Color.BLUE);
        title.setFont(Font.font("Impact", 96));
        title.setX(GAME_WIDTH / 11);
        title.setY(150);
        menuRoot.getChildren().add(title);


        // Actual text for the button, adds the on mouse clicked which sends to the methods below to start the games
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

    /**
     * Authors: Murph
     * @param text
     * @param yNum
     */
    public void handleText(Text text, int yNum) {
        text.setFont(Font.font("Impact", 36));
        text.setFill(Color.BLACK);
        text.setX(GAME_WIDTH / 2.0 - text.getLayoutBounds().getWidth() / 2);
        text.setY(yNum + 45);
    }

    /**
     * Authors: Murph
     * @param yInt
     * @return
     */
    public Rectangle createBackground(int yInt) {
        Rectangle background = new Rectangle(300, 60, Color.BLUE);
        background.setX(GAME_WIDTH / 2.0 - 150);
        background.setY(yInt);
        return background;
    }

    /**
     * Authors: Murph
     */
    private void startBreakout() {
        BreakoutScreen breakoutScreen = new BreakoutScreen();
        gameLoop = new BreakoutLoop(breakoutScreen);
        startGame(breakoutScreen);
    }

    /**
     * Authors: Murph
     */
    private void startGalaga() {
        GalagaScreen galagaScreen = new GalagaScreen();
        gameLoop = new GalagaLoop(galagaScreen);
        startGame(galagaScreen);
        }

    /**
     * Authors: Murph
     * @param screen
     */
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


    /**
     * Authors: Murph
     */
    public static void runGame() {
        launch();
    }
}

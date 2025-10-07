import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    @Override
    public void start(Stage primaryStage) {
        // 1. Create the screen object, which sets up the scene graph
        Screen screen = new Screen();
        
        // Use the Screen's root group for the scene
        Scene scene = new Scene(screen.getRoot(), GAME_WIDTH, GAME_HEIGHT, Color.AZURE);

        // 2. Instantiate the other game objects
        // (You'll need a Ball constructor that matches your class)
        Ball mockBall = new Ball(10, 250, 250); 
        Slider slider = screen.getSlider();
        
        
        // 🚨 ADD THIS LINE 🚨 to add the ball's shape to the scene graph
        screen.getRoot().getChildren().add(mockBall.getBall());

        // 3. Create your GameLoop instance, passing in the objects
        GameLoop gameLoop = new GameLoop(mockBall, slider, screen);
        
        scene.setOnMouseClicked(e -> {
            gameLoop.startMoving();
        });
        
        scene.setOnKeyPressed(e -> gameLoop.handleKeyInput(e.getCode()));
        // 4. Set up the game loop using Timeline
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> gameLoop.step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        // 5. Set up the stage and show the window
        primaryStage.setTitle("Project: Breakout!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
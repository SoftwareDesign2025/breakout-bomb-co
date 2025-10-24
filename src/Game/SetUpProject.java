package Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import Objects.Ball;

public class SetUpProject extends Application {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final String TITLE = "Breakout";
    

    private GameLoop gameLoop;

    @Override
    public void start(Stage stage) {
        Scene myScene;
    	Ball ball = new Ball(10, 400, 400);
        Screen screen = new Screen(ball);
        gameLoop = new GameLoop(screen);
        Group root = screen.getRoot();
        myScene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.AZURE);
        myScene.setOnKeyPressed(e -> gameLoop.handleKeyInput(e.getCode()));
        myScene.setOnMouseClicked(e -> {
            gameLoop.startMoving();
        });
        stage.setScene(myScene);
		stage.setTitle(TITLE);
		stage.show();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> gameLoop.step());
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
    }
    
  

    public void runGame() {
        launch();
    }
}

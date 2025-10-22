import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class SetUpProject extends Application {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String TITLE = "Breakout";
    
    private Scene myScene;
    private GameLoop gameLoop;

    @Override
    public void start(Stage stage) {
    	Ball ball = new Ball(10, 400, 400);
        Screen screen = new Screen(ball);
        ArrayList<PowerUp> powerUpList = new ArrayList<>();
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
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> gameLoop.step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
    }
    
  

    public void runGame() {
        launch();
    }
}

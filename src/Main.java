import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Screen screen = new Screen();

        Scene scene = new Scene(screen.getRoot(), 800, 600);

        
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            screen.getSlider().handleMovement(code);
        });

        primaryStage.setTitle("Slider Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

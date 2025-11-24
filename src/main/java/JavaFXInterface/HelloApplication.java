package JavaFXInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PhysicsLabGUI.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 320, 240);
        log.debug("Main Scene created!");
        stage.setTitle("Virtual Physics Lab");
        stage.setScene(mainScene);
        stage.show();
    }
}

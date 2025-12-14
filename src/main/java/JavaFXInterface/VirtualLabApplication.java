package JavaFXInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class VirtualLabApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualLabApplication.class.getResource("PhysicsLabGUI.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        stage.setTitle("Virtual Physics Lab");
//        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(mainScene);
        stage.setMaximized(true);
        stage.show();
    }
}

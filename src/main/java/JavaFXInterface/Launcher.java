package JavaFXInterface;
import lombok.extern.slf4j.Slf4j;

import javafx.application.Application;

@Slf4j
public class Launcher {
    public static void main(String[] args) {
        log.info("Application launched!");
        Application.launch(VirtualLabApplication.class, args);
    }
}

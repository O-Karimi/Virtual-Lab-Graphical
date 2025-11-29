module advancedprogramming.virtuallabgraphical {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires org.slf4j;         // <--- ALLOWS ACCESS TO LOGGER
    requires static lombok;
    requires java.desktop;

    opens JavaFXInterface to javafx.fxml;
    exports JavaFXInterface;
    exports JavaFXInterface.PopUpControllers;
    opens JavaFXInterface.PopUpControllers to javafx.fxml;
}
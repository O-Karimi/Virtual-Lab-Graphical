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
    requires static lombok;     // <--- ALLOWS ACCESS TO ANNOTATIONS

    opens JavaFXInterface to javafx.fxml;
    exports JavaFXInterface;
}
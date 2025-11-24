package JavaFXInterface;

import Logic.logicMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class HelloController {

    private logicMain logicMain;

    private void setupCustomListeners() {
        this.logicMain =  new logicMain();
    }

    @FXML
    private Pane mainPane;
    private Label propertyOneLabel;
    private Label propertyTwoLabel;
    private Label propertyThreeLabel;

    @FXML
    public void initialize() {
        System.out.println("Initializing logicMain!");
        setupCustomListeners();
    }

    @FXML
    void  particleButtonClicked(ActionEvent event) {
        // Defining circle in JavaFX
        Circle particleCircle = new Circle(50,50,2);
        particleCircle.setFill(Color.CORAL);
        particleCircle.setStroke(Color.BLACK);
        mainPane.getChildren().add(particleCircle);
        // Adding the circle in Initiallizer
        this.logicMain.getInitializer().addParticleMass(50, 50,5);
        System.out.println("Particle Mass has been added.");
    }
}

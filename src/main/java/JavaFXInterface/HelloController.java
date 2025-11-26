package JavaFXInterface;

import Logic.logicMain;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

@Slf4j
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
    private Button submitButton;
    private Button cancelButton;

    @FXML
    public void initialize() {
        log.debug("Initializing logicMain!");
        setupCustomListeners();
    }

    @FXML
    void  particleButtonClicked(ActionEvent event) {
        // Defining circle in JavaFX

        try {
            // 1. Create the Dialog
            Dialog<PopupController.ParticleData> dialog = new Dialog<>();
            dialog.setTitle("Particle Data Entry");

            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ParticlePopUp.fxml"));
            Parent root = loader.load(); // Load the view
            PopupController controller = loader.getController(); // Get the controller instance

            // 3. Set the FXML loaded content into the Dialog
            dialog.getDialogPane().setContent(root);

            // 4. Add the Buttons (Create/Cancel) via Java
            // We do this here so the Dialog recognizes them as control buttons
            ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

            // 5. Convert the result
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == createButtonType) {
                    // ASK THE CONTROLLER for the data!
                    return controller.getCollectedData();
                }
                return null;
            });

            // 6. Show and Wait
            Optional<PopupController.ParticleData> result = dialog.showAndWait();

            result.ifPresent(data -> {
                float particleX = data.particleX();
                float particleY = data.particleY();
                float weight = data.weight();
                Circle particleCircle = new Circle(particleX,particleY,2);
                particleCircle.setFill(Color.CORAL);
                particleCircle.setStroke(Color.BLACK);
                mainPane.getChildren().add(particleCircle);
                this.logicMain.getInitializer().addParticleMass(particleX,particleY,weight);
                log.debug("Particle Mass has been added.");
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        propertyOneLabel.setText("Input X :");
        propertyTwoLabel.setText("Input Y :");
        propertyThreeLabel.setText("Input Weight :");
    }
}

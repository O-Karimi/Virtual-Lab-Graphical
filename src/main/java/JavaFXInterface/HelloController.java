package JavaFXInterface;

import JavaFXInterface.PopUpControllers.CirclePopupController;
import JavaFXInterface.PopUpControllers.ParticlePopupController;
import JavaFXInterface.PopUpControllers.SquarePopupController;
import Logic.Systems.MassSystem.Masses.Mass;
import Logic.logicMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class HelloController {

    @FXML
    private Pane mainPane;
    @FXML
    private TreeView<String> hierarchyTreeView;
    @FXML
    private Label objectsCountLabel;

    private logicMain logicMain;

    private void setupNewSim() {
        this.logicMain =  new logicMain();
    }

    @FXML
    public void initialize() {
        log.debug("Initializing logicMain!");
        setupNewSim();

        TreeItem<String> rootItem = new TreeItem<>("Root");
        rootItem.setExpanded(true);
        hierarchyTreeView.setRoot(rootItem);
        hierarchyTreeView.setShowRoot(false); // Hide root to look like a list

        getMassStatus();
    }

    @FXML
    void  particleButtonClicked(ActionEvent event) {
        try {
            // 1. Create the Dialog
            Dialog<ParticlePopupController.ParticleData> dialog = new Dialog<>();
            dialog.setTitle("Particle Data Entry");

            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ParticlePopUp.fxml"));
            Parent root = loader.load(); // Load the view
            ParticlePopupController controller = loader.getController(); // Get the controller instance

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
            Optional<ParticlePopupController.ParticleData> result = dialog.showAndWait();

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
                this.getMassStatus();
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void  circleButtonClicked(ActionEvent event) {
        try {
            // 1. Create the Dialog
            Dialog<CirclePopupController.CircleData> dialog = new Dialog<>();
            dialog.setTitle("Circle Data Entry");

            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CirclePopUp.fxml"));
            Parent root = loader.load(); // Load the view
            CirclePopupController controller = loader.getController(); // Get the controller instance

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
            Optional<CirclePopupController.CircleData> result = dialog.showAndWait();

            result.ifPresent(data -> {
                float circleX = data.circleX();
                float circleY = data.circleY();
                float radius = data.radius();
                float weight = data.weight();
                Circle circle = new Circle(circleX,circleY,radius);
                circle.setFill(Color.CORAL);
                circle.setStroke(Color.BLACK);
                mainPane.getChildren().add(circle);
                this.logicMain.getInitializer().addCircleMass(circleX,circleY,weight, radius);
                log.debug("Circle Mass has been added.");
                this.getMassStatus();
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void  squareButtonClicked(ActionEvent event) {
        try {
            // 1. Create the Dialog
            Dialog<SquarePopupController.SquareData> dialog = new Dialog<>();
            dialog.setTitle("Square Data Entry");

            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SquarePopUp.fxml"));
            Parent root = loader.load(); // Load the view
            SquarePopupController controller = loader.getController(); // Get the controller instance

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
            Optional<SquarePopupController.SquareData> result = dialog.showAndWait();

            result.ifPresent(data -> {
                float squareX = data.squareX();
                float squareY = data.squareY();
                float length = data.length();
                float weight = data.weight();
                Rectangle square = new Rectangle(length, length);
                square.setX(squareX - length/2);
                square.setY(squareY - length/2);
                square.setFill(Color.CORAL);
                square.setStroke(Color.BLACK);
                mainPane.getChildren().add(square);
                this.logicMain.getInitializer().addSquareMass(squareX,squareY,weight, length);
                log.debug("Square Mass has been added.");
                this.getMassStatus();
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void getMassStatus(){

        hierarchyTreeView.getRoot().getChildren().clear();
        Mass[] masses = this.logicMain.getInitializer().massList().toArray(new Mass[0]);
        int massNum = masses.length;
        objectsCountLabel.setText(String.valueOf(massNum));

        for (int i = 0; i < massNum; i++) {
            MassTreeItem item = new MassTreeItem(masses[i]);
            hierarchyTreeView.getRoot().getChildren().add(item);
        }
    }
}

package JavaFXInterface;

import JavaFXInterface.PopUpControllers.CirclePopupController;
import JavaFXInterface.PopUpControllers.ParticlePopupController;
import JavaFXInterface.PopUpControllers.SpringPopupController;
import JavaFXInterface.PopUpControllers.SquarePopupController;
import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import Logic.Systems.MassSystem.Masses.CircleMass;
import Logic.Systems.MassSystem.Masses.Mass;
import Logic.LogicMain;

import Logic.Systems.MassSystem.Masses.Particle;
import Logic.Systems.MassSystem.Masses.SquareMass;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

@Slf4j
public class HelloController {

    @FXML
    private Pane mainPane;
    @FXML
    private TreeView<String> hierarchyTreeView;
    @FXML
    private Label objectsCountLabel;

    private LogicMain logicMain;
    private Map<Mass, Node> viewMap = new HashMap<>();

    private void setupNewSim() {
        this.logicMain =  new LogicMain();
    }

    @FXML
    public void initialize() {
        log.debug("Initializing logicMain!");
        setupNewSim();

        TreeItem<String> rootItem = new TreeItem<>("Root");
        rootItem.setExpanded(true);
        hierarchyTreeView.setRoot(rootItem);
        hierarchyTreeView.setShowRoot(false); // Hide root to look like a list

        getStatus();
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
                Circle particleCircle = new Circle(0,0,2);
                particleCircle.setTranslateX(particleX);
                particleCircle.setTranslateY(particleY);
                particleCircle.setFill(Color.CORAL);
                particleCircle.setStroke(Color.BLACK);
                mainPane.getChildren().add(particleCircle);
                Mass mass = new Particle(particleX,particleY,weight);
                this.addMass(mass, particleCircle);
                log.debug("Particle Mass has been added.");
                this.getStatus();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMass(Mass mass, Node node) {
        this.logicMain.getMassSystem().addMass(mass);
        viewMap.put(mass, node);
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
                Circle circle = new Circle(0,0,radius);
                circle.setTranslateX(circleX);
                circle.setTranslateY(circleY);
                circle.setFill(Color.CORAL);
                circle.setStroke(Color.BLACK);
                mainPane.getChildren().add(circle);
                Mass mass = new CircleMass(circleX,circleY,radius,weight);
                this.addMass(mass, circle);
                log.debug("Circle Mass has been added.");
                this.getStatus();
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
                square.setX(0);
                square.setTranslateX(squareX - length/2);
                square.setY(0);
                square.setTranslateY(squareY - length/2);
                square.setFill(Color.CORAL);
                square.setStroke(Color.BLACK);
                mainPane.getChildren().add(square);
                Mass mass = new SquareMass(squareX,squareY,length,weight);
                this.addMass(mass, square);
                log.debug("Square Mass has been added.");
                this.getStatus();
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void springButtonClicked(ActionEvent event) {
        try {
            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SpringPopUp.fxml"));
            Parent root = loader.load(); // Load the view
            SpringPopupController controller = loader.getController(); // Get the controller instance

            // 3. Set the FXML loaded content into the Dialog
            controller.setSystem(this.logicMain.getMassSystem());

            Dialog<SpringPopupController.SpringData> dialog = new Dialog<>();
            dialog.setTitle("Spring Data Entry");
            dialog.getDialogPane().setContent(root);

            // 4. Add the Buttons (Create/Cancel) via Java
            // We do this here so the Dialog recognizes them as control buttons
            ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

            // 5. Convert the result
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == createButtonType) {
                    // ASK THE CONTROLLER for the data!
                    return controller.getData();
                }
                return null;
            });

            // 6. Show and Wait
            Optional<SpringPopupController.SpringData> result = dialog.showAndWait();

            result.ifPresent(data -> {
                Mass m1 = data.massOne();
                Mass m2 = data.massTwo();
                double k = data.springConstant();
                Line line = new Line(m1.getCenterX(),m1.getCenterY(),m2.getCenterX(),m2.getCenterY());
                line.setStroke(Color.CORAL);
                mainPane.getChildren().add(line);
                this.logicMain.getConnectorSystem().getSpringSystem().addSpring(m1,m2,k);
                log.debug("Circle Mass has been added.");
                this.getStatus();
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMassStatus(TreeItem massesGroup){
        List<Mass> masses = this.logicMain.getMassSystem().massList();
        int massNum = masses.size();
        objectsCountLabel.setText(String.valueOf(massNum));

        if (masses != null) {
            for (Mass mass : masses) {
                TreeItem<String> item = new TreeItem<>(mass.toString());
                massesGroup.getChildren().add(item);
            }
        }
    }

    private void getSpringStatus(TreeItem springsGroup){
        List<Spring> springs = this.logicMain.getConnectorSystem().getSpringSystem().getSpringsList();

        if (springs != null) {
            for (Spring spring : springs) {
                TreeItem<String> item = new TreeItem<>(spring.toString());
                springsGroup.getChildren().add(item);
            }
        }
    }

    private void getStatus(){
        hierarchyTreeView.getRoot().getChildren().clear();
        TreeItem<String> massesGroup = new TreeItem<>("Masses");
        TreeItem<String> springsGroup = new TreeItem<>("Springs");
        massesGroup.setExpanded(true);
        springsGroup.setExpanded(true);
        this.getMassStatus(massesGroup);
        this.getSpringStatus(springsGroup);
        hierarchyTreeView.getRoot().getChildren().addAll(massesGroup, springsGroup);
    }
    @FXML
    private void startSimulationLoop() {

        AnimationTimer timer = new AnimationTimer() {

            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // 1. Calculate Delta Time (Time since last frame)
                // 'now' is in nanoseconds. Convert to seconds.
                if (lastUpdate == 0) { lastUpdate = now; return; }

                double dt = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;

                // Cap dt to prevent "explosions" if the computer lags
                if (dt > 0.1) dt = 0.1;

                // -------------------------------------
                // STEP A: Update Logic (The Physics)
                // -------------------------------------
                logicMain.iterate();

                // -------------------------------------
                // STEP B: Update Visuals (The Motion)
                // -------------------------------------
                for (Map.Entry<Mass, Node> entry : viewMap.entrySet()) {
                    Mass mass = entry.getKey();
                    Node node = entry.getValue();

                    // Synchronize positions
                    // We use 'setTranslate' because it is faster for animation
                    // than setting LayoutX/LayoutY constantly.

                    // If your node was created at (0,0), translate moves it to (x,y)
                    node.setTranslateX(mass.getCenterX());
                    node.setTranslateY(mass.getCenterY());

                    // Rotation (optional, if you calculate torque)
                    // node.setRotate(mass.getAngle());
                }
            }
        };

        timer.start();
    }
}

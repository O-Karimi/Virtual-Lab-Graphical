package JavaFXInterface;

import JavaFXInterface.PopUpControllers.*;
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
    @FXML
    private Label timerLabel;

    private LogicMain logicMain;
    private Map<Mass, Node> massMap = new HashMap<>();
    private Map<Spring, Line> springMap = new HashMap<>();

    private void setupNewSim() {
        this.logicMain =  new LogicMain();
    }

    private Map<TreeItem<String>, Mass> massLookup = new HashMap<>();
    private Map<TreeItem<String>, Spring> springLookup = new HashMap<>();

    @FXML
    public void initialize() {
        log.debug("Initializing logicMain!");
        setupNewSim();

        TreeItem<String> rootItem = new TreeItem<>("Root");
        rootItem.setExpanded(true);
        hierarchyTreeView.setRoot(rootItem);
        hierarchyTreeView.setShowRoot(false); // Hide root to look like a list

        hierarchyTreeView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                handleSelection(newVal);
            }
        });

        Circle particleCircle = new Circle(0,0,2);
        particleCircle.setTranslateX(100);
        particleCircle.setTranslateY(100);
        particleCircle.setFill(Color.CORAL);
        particleCircle.setStroke(Color.BLACK);
        mainPane.getChildren().add(particleCircle);
        Mass m1 = new Particle(100,100,20);
        this.addMass(m1, particleCircle);

        Circle particleCircle1 = new Circle(0,0,2);
        particleCircle1.setTranslateX(400);
        particleCircle1.setTranslateY(400);
        particleCircle1.setFill(Color.CORAL);
        particleCircle1.setStroke(Color.BLACK);
        mainPane.getChildren().add(particleCircle1);
        Mass m2 = new Particle(400,400,90);
        this.addMass(m2, particleCircle1);

        Line line = new Line(m1.getCenterX(),m1.getCenterY(),m2.getCenterX(),m2.getCenterY());
        line.setStroke(Color.CORAL);
        mainPane.getChildren().add(line);
        line.toBack();
        Spring spring = new Spring(m1,m2,1800,100);
        this.logicMain.getConnectorSystem().getSpringSystem().addSpring(spring);
        springMap.put(spring, line);

        getStatus();
    }

    private void handleSelection(TreeItem<String> item) {
        if (item == null) return;

        // Check if it is a Mass
        if (massLookup.containsKey(item)) {
            Mass selectedMass = massLookup.get(item);
            log.debug("User selected Mass: " + selectedMass.toString());
            // highlightMass(selectedMass);
        }
        // Check if it is a Spring
        else if (springLookup.containsKey(item)) {
            Spring selectedSpring = springLookup.get(item);
            log.debug("User selected a Spring");
        }
        else {
            log.debug("User selected a folder (Masses/Springs group)");
        }
    }

    private <T> void PopUp(Parent root, Dialog<T> dialog, PopUpController controller) {
        dialog.getDialogPane().setContent(root);

        // 4. Add the Buttons (Create/Cancel) via Java
        // We do this here so the Dialog recognizes them as control buttons
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // 5. Convert the result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                // ASK THE CONTROLLER for the data!
                return (T) controller.getCollectedData();
            }
            return null;
        });
    }

    @FXML
    void  particleButtonClicked(ActionEvent event) {
        try {
            // 1. Create the Dialog
            Dialog<ParticlePopupController.Data> dialog = new Dialog<>();
            dialog.setTitle("Particle Data Entry");

            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ParticlePopUp.fxml"));
            Parent root = loader.load(); // Load the view
            ParticlePopupController controller = loader.getController(); // Get the controller instance

            // 3. Set the FXML loaded content into the Dialog
            this.PopUp(root, dialog, controller);
            // 6. Show and Wait
            Optional<ParticlePopupController.Data> result = dialog.showAndWait();

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
        massMap.put(mass, node);
    }
    @FXML
    void  circleButtonClicked(ActionEvent event) {
        try {
            // 1. Create the Dialog
            Dialog<CirclePopupController.Data> dialog = new Dialog<>();
            dialog.setTitle("Circle Data Entry");

            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CirclePopUp.fxml"));
            Parent root = loader.load(); // Load the view
            CirclePopupController controller = loader.getController(); // Get the controller instance

            // 3. Set the FXML loaded content into the Dialog
            this.PopUp(root,dialog,controller);
            // 6. Show and Wait
            Optional<CirclePopupController.Data> result = dialog.showAndWait();

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
            Dialog<SquarePopupController.Data> dialog = new Dialog<>();
            dialog.setTitle("Square Data Entry");

            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SquarePopUp.fxml"));
            Parent root = loader.load(); // Load the view
            SquarePopupController controller = loader.getController(); // Get the controller instance

            // 3. Set the FXML loaded content into the Dialog
            this.PopUp(root,dialog,controller);
            // 6. Show and Wait
            Optional<SquarePopupController.Data> result = dialog.showAndWait();

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
            Dialog<SpringPopupController.Data> dialog = new Dialog<>();
            dialog.setTitle("Spring Data Entry");
            // 2. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SpringPopUp.fxml"));
            Parent root = loader.load(); // Load the view
            SpringPopupController controller = loader.getController(); // Get the controller instance
            controller.setSystem(this.logicMain.getMassSystem());

            // 3. Set the FXML loaded content into the Dialog
            this.PopUp(root,dialog,controller);
            // 6. Show and Wait
            Optional<SpringPopupController.Data> result = dialog.showAndWait();

            result.ifPresent(data -> {
                Mass m1 = data.massOne();
                Mass m2 = data.massTwo();
                double k = data.springConstant();
                Line line = new Line(m1.getCenterX(),m1.getCenterY(),m2.getCenterX(),m2.getCenterY());
                line.setStroke(Color.CORAL);
                mainPane.getChildren().add(line);
                line.toBack();
                Spring spring = new Spring(m1,m2,k,100);
                this.logicMain.getConnectorSystem().getSpringSystem().addSpring(spring);
                springMap.put(spring, line);
                log.debug("Spring has been added.");
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
        massLookup.clear();
        log.debug(String.valueOf(massLookup.size()));

        if (masses != null) {
            for (Mass mass : masses) {
                TreeItem<String> item = new TreeItem<>(mass.toString());
                massesGroup.getChildren().add(item);
                massLookup.put(item,mass);
            }
        }
    }

    private void getSpringStatus(TreeItem springsGroup){
        List<Spring> springs = this.logicMain.getConnectorSystem().getSpringSystem().getSpringsList();
        springLookup.clear();

        if (springs != null) {
            for (Spring spring : springs) {
                TreeItem<String> item = new TreeItem<>(spring.toString());
                springsGroup.getChildren().add(item);
                springLookup.put(item,spring);
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

            @Override
            public void handle(long now) {
                // 1. Calculate Delta Time (Time since last frame)
                // 'now' is in nanoseconds. Convert to seconds.
                // Cap dt to prevent "explosions" if the computer lags

                // -------------------------------------
                // STEP A: Update Logic (The Physics)
                // -------------------------------------
                timerLabel.setText(String.format("%.3f",logicMain.getSimulator().getTime()));
                logicMain.iterate();

                // -------------------------------------
                // STEP B: Update Visuals (The Motion)
                // -------------------------------------
                for (Map.Entry<Mass, Node> entry : massMap.entrySet()) {
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
                for (Map.Entry<Spring, Line> entry : springMap.entrySet()) {
                    Spring s = entry.getKey();
                    Line line = entry.getValue();

                    // 1. Get the Logic Masses connected to this spring
                    Mass m1 = s.getMasses().get(0);
                    Mass m2 = s.getMasses().get(1);

                    // 2. Snap the line endpoints to the mass positions
                    // Note: If you used (0,0) initialization for masses, getCenterX() works perfect.
                    line.setStartX(m1.getCenterX());
                    line.setStartY(m1.getCenterY());
                    line.setEndX(m2.getCenterX());
                    line.setEndY(m2.getCenterY());
                }
            }
        };

        timer.start();
    }
}

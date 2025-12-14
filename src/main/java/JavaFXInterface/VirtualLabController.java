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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@Slf4j
public class VirtualLabController {

    @FXML
    private Pane mainPane;
    @FXML
    private TreeView<String> hierarchyTreeView;
    @FXML
    private Label objectsCountLabel;
    @FXML
    private Label timerLabel;
    @FXML
    private GridPane propertiesGrid;
    @FXML
    private Button updateButton;
    @FXML
    private Menu examplesMenu;
    @FXML
    private CheckBox gravityCheckbox;

    private AnimationTimer timer;

    private Examples example;

    private LogicMain logicMain;
    private final Map<Mass, Shape> massMap = new HashMap<>();
    private final Map<Spring, Line> springMap = new HashMap<>();

    private void setupNewSim() {
        this.logicMain =  new LogicMain();
    }

    private final Map<TreeItem<String>, Mass> massLookup = new HashMap<>();
    private final Map<TreeItem<String>, Spring> springLookup = new HashMap<>();

    @FXML
    public void initialize() {
        log.debug("Initializing logicMain!");
        this.setupNewSim();
        this.initiateHierarchy();
        example = new Examples(mainPane);
        this.exampleMenuItemsGenerator(example.getExamplesMap());
    }

    public void exampleMenuItemsGenerator(Map<String, Consumer<Examples.Data>> commandMap) {
        // Optional: Clear existing buttons if you run this multiple times
        examplesMenu.getItems().clear();

        // Loop through every entry in the Map
        for (Map.Entry<String, Consumer<Examples.Data>> entry : commandMap.entrySet()) {

            String labelText = entry.getKey();
            Consumer<Examples.Data> action = entry.getValue();

            MenuItem item = new MenuItem(labelText);
            // 2. Attach the Action
            // When clicked, run the 'run()' method of the Runnable
            item.setOnAction(e -> {
                this.restartButton();
                Examples.Data data = new Examples.Data(this.logicMain, this.massMap, this.springMap);
                action.accept(data);
                this.gravityCheckbox.setSelected(logicMain.getSimulator().isGravityOn());
                getStatus();
            });

            // 3. Add to the Layout
            examplesMenu.getItems().add(item);
        }
    }

    private void initiateHierarchy() {
        TreeItem<String> rootItem = new TreeItem<>("Root");
        rootItem.setExpanded(true);
        hierarchyTreeView.setRoot(rootItem);
        hierarchyTreeView.setShowRoot(false);

        hierarchyTreeView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            Object selectedObject = null;
            if (massLookup.containsKey(newVal)) {
                Mass selectedMass = massLookup.get(newVal);
                selectedObject = selectedMass;
                massMap.get(selectedMass).setFill(Color.RED);
            } else if (springLookup.containsKey(newVal)) {
                Spring selectedSpring = springLookup.get(newVal);
                selectedObject = selectedSpring;
                massMap.get(selectedSpring).setFill(Color.RED);
            }

            if (massLookup.containsKey(oldVal)) {
                Mass selectedMass = massLookup.get(oldVal);
                massMap.get(selectedMass).setFill(Color.CORAL);
            } else if (springLookup.containsKey(oldVal)) {
                Spring selectedSpring = springLookup.get(oldVal);
                massMap.get(selectedSpring).setFill(Color.CORAL);
            }


            if (selectedObject != null) {
                PropertyGenerator.populateGrid(propertiesGrid, selectedObject);
                updateButton.setDisable(false);
            } else {
                propertiesGrid.getChildren().clear();
                updateButton.setDisable(true);
            }
        });
    }

    @FXML
    private void restartButton(){
        this.stopButton();
        initialize();
        mainPane.getChildren().clear();
        Mass.setCounter(0);
        Spring.setCounter(0);
    }

//    @FXML
//    private void testExample() {
//        this.restartButton();
//
//        example.testExample();
//        example.export(logicMain, massMap, springMap);
//
//        getStatus();
//    }
//
//    @FXML
//    private void doublePendulumExample() {
//        this.restartButton();
//
//        example.doublePendulumExample();
//        example.export(logicMain, massMap, springMap);
//
//        this.gravityCheckbox.setSelected(true);
//        getStatus();
//    }
//
//    @FXML
//    private void pendulumDanceExample() {
//        this.restartButton();
//
//        example.pendulumDanceExample();
//        example.export(logicMain, massMap, springMap);
//
//
//        this.gravityCheckbox.setSelected(true);
//        getStatus();
//    }
//
    @FXML
    private void gravityCheckboxControl(){
        this.logicMain.getSimulator().setGravity(this.gravityCheckbox.isSelected());
    }

    private Mass addParticleMass(double x, double y, double w){
        Circle particleCircle = new Circle(0,0,2);
        particleCircle.setTranslateX(x);
        particleCircle.setTranslateY(y);
        particleCircle.setFill(Color.CORAL);
        particleCircle.setStroke(Color.BLACK);
        mainPane.getChildren().add(particleCircle);
        Mass m = new Particle(x,y,w);
        this.addMassNodeMap(m, particleCircle);
        return m;
    }

    private Spring addPendulum(Mass m1, Mass m2){
        Line line = new Line(m1.getCenterX(),m1.getCenterY(),m2.getCenterX(),m2.getCenterY());
        line.setStroke(Color.CORAL);
        mainPane.getChildren().add(line);
        line.toBack();
        Spring spring = new Spring(m1,m2,1800);
        this.logicMain.getConnectorSystem().getSpringSystem().addSpring(spring);
        springMap.put(spring, line);
        return spring;
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
                boolean xConst = data.xConst();
                boolean yConst = data.yConst();
                Circle particleCircle = new Circle(0,0,2);
                particleCircle.setTranslateX(particleX);
                particleCircle.setTranslateY(particleY);
                particleCircle.setFill(Color.CORAL);
                particleCircle.setStroke(Color.BLACK);
                mainPane.getChildren().add(particleCircle);
                Mass mass = new Particle(particleX,particleY,weight);
                mass.setxConst(xConst);
                mass.setyConst(yConst);
                this.addMassNodeMap(mass, particleCircle);
                log.debug("Particle Mass has been added.");
                this.getStatus();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMassNodeMap(Mass mass, Shape shape) {
        this.logicMain.getMassSystem().addMass(mass);
        massMap.put(mass, shape);
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
                this.addMassNodeMap(mass, circle);
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
                this.addMassNodeMap(mass, square);
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
        List<Mass> masses = this.logicMain.getMassSystem().getMassList();
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
    private void startButton() {

        this.timer = new AnimationTimer() {

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
                updateGraphics();
            }
        };

        timer.start();
    }

    @FXML
    private void stopButton(){
        this.timer.stop();
    }

    @FXML
    private void resetButton(){
        timer.stop();
        this.logicMain.getSimulator().setTime(0);
        for (Mass m : logicMain.getMassSystem().getMassList()){
            m.setCenterX(m.getInitialCenterX());
            m.setCenterY(m.getInitialCenterY());
            m.setVelX(m.getInitialVelX());
            m.setVelY(m.getInitialVelY());
        }
        this.updateGraphics();
    }

    @FXML
    public void onUpdatePropertiesClicked(ActionEvent actionEvent) {
        // Run the list of tasks we collected
        PropertyGenerator.applyChanges();
        this.updateGraphics();

        // Optional: Refresh the TreeView text in case the Name changed
        hierarchyTreeView.refresh();
    }

    private void updateGraphics(){
        for (Map.Entry<Mass, Shape> entry : massMap.entrySet()) {
            Mass mass = entry.getKey();
            Node node = entry.getValue();
            node.setTranslateX(mass.getCenterX());
            node.setTranslateY(mass.getCenterY());
        }
        for (Map.Entry<Spring, Line> entry : springMap.entrySet()) {
            Spring spring = entry.getKey();
            Line line = entry.getValue();
            Mass m1 = spring.getMasses().get(0);
            Mass m2 = spring.getMasses().get(1);

            // 2. Snap the line endpoints to the mass positions
            // Note: If you used (0,0) initialization for masses, getCenterX() works perfect.
            line.setStartX(m1.getCenterX());
            line.setStartY(m1.getCenterY());
            line.setEndX(m2.getCenterX());
            line.setEndY(m2.getCenterY());

        }
    }
}

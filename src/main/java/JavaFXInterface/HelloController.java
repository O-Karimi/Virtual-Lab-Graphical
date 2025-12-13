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
import javafx.fxml.Initializable;
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
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

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
    @FXML
    private GridPane propertiesGrid;
    @FXML
    private Button updateButton;
    @FXML
    private Menu examplesMenu;
    @FXML
    private CheckBox gravityCheckbox;

    private LogicMain logicMain;
    private Map<Mass, Node> massMap = new HashMap<>();
    private Map<Spring, Line> springMap = new HashMap<>();

    public <T> void populateExamplesMenu(List<T> items, Consumer<T> action) {
        // 1. Clear old items (optional, prevents duplicates if called twice)
        examplesMenu.getItems().clear();

        // 2. Loop through the unknown list
        for (T item : items) {
            // A. Create the visual menu item
            // Uses toString() by default, or you can pass a name extractor
            MenuItem menuItem = new MenuItem(item.toString());

            // B. Add the Click Action
            menuItem.setOnAction(event -> {
                // When clicked, run the action with the SPECIFIC item
                action.accept(item);
            });

            // C. Add to the Menu
            examplesMenu.getItems().add(menuItem);
        }
    }

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
            // 1. Determine the object
            Object selectedObject = null;
            if (massLookup.containsKey(newVal)) {
                Mass selectedMass = massLookup.get(newVal);
                selectedObject = selectedMass;
            } else if (springLookup.containsKey(newVal)) {
                Spring selectedSpring = springLookup.get(newVal);
                selectedObject = selectedSpring;
            }

            // 2. Ask the generator to fill the grid
            if (selectedObject != null) {
                PropertyGenerator.populateGrid(propertiesGrid, selectedObject);
                updateButton.setDisable(false); // Enable the button
            } else {
                propertiesGrid.getChildren().clear();
                updateButton.setDisable(true); // Disable if nothing selected
            }
        });

//        this.test();
    }

    @FXML
    private void testExample() {
        initialize();
        Mass m1 = this.addParticleMass(300,100,10);
        Mass m2 = this.addParticleMass(300,300,10);

        Line line = new Line(m1.getCenterX(),m1.getCenterY(),m2.getCenterX(),m2.getCenterY());
        line.setStroke(Color.CORAL);
        mainPane.getChildren().add(line);
        line.toBack();
        Spring spring = new Spring(m1,m2,1800,100);
        this.logicMain.getConnectorSystem().getSpringSystem().addSpring(spring);
        springMap.put(spring, line);

        getStatus();
    }

    @FXML
    private void doublePendulumExample() {
        initialize();
        Mass m1 = this.addParticleMass(300,100,10);
        Mass m2 = this.addParticleMass(350,200,10);
        Mass m3 = this.addParticleMass(600,200,10);

        m1.setxConst(true);
        m1.setyConst(true);

        this.addPendulum(m1,m2);
        this.addPendulum(m2,m3);

        this.logicMain.getSimulator().setGravity(true);
        getStatus();
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

    private void addMassNodeMap(Mass mass, Node node) {
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
    private void startSimulationLoop() {

        if (gravityCheckbox.isSelected()){
            this.logicMain.getSimulator().setGravity(true);
        }
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
                updateGraphics();
            }
        };

        timer.start();
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
        for (Map.Entry<Mass, Node> entry : massMap.entrySet()) {
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

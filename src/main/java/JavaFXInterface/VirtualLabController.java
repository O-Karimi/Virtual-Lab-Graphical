package JavaFXInterface;

import JavaFXInterface.Utils.*;
import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import Logic.Systems.MassSystem.Masses.CircleMass;
import Logic.Systems.MassSystem.Masses.Mass;
import Logic.LogicMain;

import Logic.Systems.MassSystem.Masses.Particle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import lombok.extern.slf4j.Slf4j;

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
    @FXML
    private TextField dTTextField;

    private AnimationTimer timer;

    private Examples example;

    private LogicMain logicMain;

    private final Map<Mass, Shape> massMap = new HashMap<>();
    private final Map<Spring, Line> springMap = new HashMap<>();
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

    private void setupNewSim() {
        this.logicMain =  new LogicMain();
    }

    public void exampleMenuItemsGenerator(Map<String, Consumer<Examples.Data>> commandMap) {
        examplesMenu.getItems().clear();

        for (Map.Entry<String, Consumer<Examples.Data>> entry : commandMap.entrySet()) {

            String labelText = entry.getKey();
            Consumer<Examples.Data> action = entry.getValue();

            MenuItem item = new MenuItem(labelText);
            item.setOnAction(e -> {
                this.restartButton();
                Examples.Data data = new Examples.Data(this.logicMain, this.massMap, this.springMap);
                action.accept(data);
                this.gravityCheckbox.setSelected(logicMain.getSimulator().isGravityOn());
                getStatus();
            });

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
                springMap.get(selectedSpring).setStroke(Color.RED);
            }

            if (massLookup.containsKey(oldVal)) {
                Mass selectedMass = massLookup.get(oldVal);
                massMap.get(selectedMass).setFill(Color.CORAL);
            } else if (springLookup.containsKey(oldVal)) {
                Spring selectedSpring = springLookup.get(oldVal);
                springMap.get(selectedSpring).setStroke(Color.CORAL);
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

    @FXML private void dTApplyButton(){
        String text = dTTextField.getText();
        if(text.matches("\\d*(\\.\\d*)?")){
            this.logicMain.getSimulator().getIterator().setDt(Double.parseDouble(text));
        }
    }

    @FXML
    private void gravityCheckboxControl(){
        this.logicMain.getSimulator().setGravity(this.gravityCheckbox.isSelected());
    }

    @FXML
    void  particleButtonClicked(ActionEvent event) {
        List<PopupController.InputRequest> form = List.of(
                new PopupController.InputRequest("x", "X Position:", PopupController.InputType.NUMBER, 0.0),
                new PopupController.InputRequest("y", "Y Position:", PopupController.InputType.NUMBER, 0.0),
                new PopupController.InputRequest("w", "Weight of Mass:", PopupController.InputType.NUMBER, 0.0),
                new PopupController.InputRequest("xFixed", "Is X Fixed?", PopupController.InputType.BOOLEAN, false),
                new PopupController.InputRequest("yFixed", "Is Y Fixed?", PopupController.InputType.BOOLEAN, false)
        );
        Map<String, Object> answers = PopupController.show("Create Particle Mass", form);

        if (answers != null) {
            double x = (Double) answers.get("x");
            double y = (Double) answers.get("y");
            double w = (Double) answers.get("w");
            boolean xFixed = (Boolean) answers.get("xFixed");
            boolean yFixed = (Boolean) answers.get("yFixed");

            Circle particleCircle = new Circle(0,0,2);
            particleCircle.setTranslateX(x);
            particleCircle.setTranslateY(y);
            particleCircle.setFill(Color.CORAL);
            particleCircle.setStroke(Color.BLACK);
            mainPane.getChildren().add(particleCircle);
            Mass mass = new Particle(x,y,w);
            mass.setxConst(xFixed);
            mass.setyConst(yFixed);
            this.addMassNodeMap(mass, particleCircle);
            log.debug("Particle Mass has been added.");
            this.getStatus();
        }
    }

    @FXML
    void  circleButtonClicked(ActionEvent event) {
        List<PopupController.InputRequest> form = List.of(
                new PopupController.InputRequest("x", "X Position:", PopupController.InputType.NUMBER, 0.0),
                new PopupController.InputRequest("y", "Y Position:", PopupController.InputType.NUMBER, 0.0),
                new PopupController.InputRequest("r", "Radius of Circle:", PopupController.InputType.NUMBER, 0.0),
                new PopupController.InputRequest("w", "Weight of Mass:", PopupController.InputType.NUMBER, 0.0),
                new PopupController.InputRequest("xFixed", "Is X Fixed?", PopupController.InputType.BOOLEAN, false),
                new PopupController.InputRequest("yFixed", "Is Y Fixed?", PopupController.InputType.BOOLEAN, false)
        );
        Map<String, Object> answers = PopupController.show("Create Circle Mass", form);

        if (answers != null) {
            double x = (Double) answers.get("x");
            double y = (Double) answers.get("y");
            double r = (Double) answers.get("r");
            double w = (Double) answers.get("w");
            boolean xFixed = (Boolean) answers.get("xFixed");
            boolean yFixed = (Boolean) answers.get("yFixed");

            Circle circle = new Circle(0,0,r);
            circle.setTranslateX(x);
            circle.setTranslateY(y);
            circle.setFill(Color.CORAL);
            circle.setStroke(Color.BLACK);
            mainPane.getChildren().add(circle);
            Mass mass = new CircleMass(x,y,r,w);
            mass.setxConst(xFixed);
            mass.setyConst(yFixed);
            this.addMassNodeMap(mass, circle);
            log.debug("Circle Mass has been added.");
            this.getStatus();
        }
    }

    @FXML
    void springButtonClicked(ActionEvent event) {
        List<Mass> availableMasses = this.logicMain.getMassSystem().getMassList();

        if (availableMasses.size() < 2) {
            this.alert("Not Enough Mass!","There is not enough mass to have spring!");
            return;
        }

        List<PopupController.InputRequest> form = List.of(
                new PopupController.InputRequest("massOne", "Mass One:", PopupController.InputType.CHOICE, availableMasses),
                new PopupController.InputRequest("massTwo", "Mass Two:", PopupController.InputType.CHOICE, availableMasses),
                new PopupController.InputRequest("k", "Stiffness of Spring:", PopupController.InputType.NUMBER, 0.0),
                new PopupController.InputRequest("l", "Rest length( -1 if it's rested):", PopupController.InputType.NUMBER, -1)
        );
        Map<String, Object> answers = PopupController.show("Create Circle Mass", form);

        if (answers != null) {
            Mass m1 = (Mass) answers.get("massOne");
            Mass m2 = (Mass) answers.get("massTwo");
            double k = (Double) answers.get("k");
            double l = (Double) answers.get("l");

            if(m1 == m2){
                this.alert("Same Masses!", "You chose the same Mass for two ends of the Spring!");
                return;
            }

            Line line = new Line(m1.getCenterX(),m1.getCenterY(),m2.getCenterX(),m2.getCenterY());
            line.setStroke(Color.CORAL);
            mainPane.getChildren().add(line);
            line.toBack();
            Spring spring = new Spring(m1,m2,k,l);
            this.logicMain.getConnectorSystem().getSpringSystem().addSpring(spring);
            springMap.put(spring, line);
            log.debug("Spring has been added.");
            this.getStatus();
        }
    }

    @FXML
    private void startButton() {

        this.timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                timerLabel.setText(String.format("%.3f",logicMain.getSimulator().getTime()));
                logicMain.iterate();
                updateGraphics();
            }
        };

        timer.start();
    }

    @FXML
    private void stopButton(){
        if (this.timer != null){
            this.timer.stop();
        }
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

    private void addMassNodeMap(Mass mass, Shape shape) {
        this.logicMain.getMassSystem().addMass(mass);
        massMap.put(mass, shape);
    }

    private void alert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null); // Optional: Cleans up the look
        alert.setContentText(message);
        alert.showAndWait();
        return;
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

            line.setStartX(m1.getCenterX());
            line.setStartY(m1.getCenterY());
            line.setEndX(m2.getCenterX());
            line.setEndY(m2.getCenterY());

        }
    }
}

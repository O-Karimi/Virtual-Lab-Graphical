package JavaFXInterface.Utils;

import Logic.LogicMain;
import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import Logic.Systems.MassSystem.Masses.Mass;
import Logic.Systems.MassSystem.Masses.Particle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class Examples {
    private final Map<String, Consumer<Data>> examplesMap = new HashMap<>();

    private LogicMain logicMain;
    private final Map<Mass, Shape> massMap = new HashMap<>();
    private final Map<Spring, Line> springMap = new HashMap<>();

    private final Pane mainPane;

    public record Data (LogicMain logic, Map<Mass,Shape> targetMassMap, Map<Spring,Line> targetSpringMap){};

    public Examples(Pane mainPane){
        this.mainPane = mainPane;
        examplesMap.put("Test", (Data data) -> {
            this.testExample();
            this.export(data);
        });
        examplesMap.put("Double Pendulum", (Data data) -> {
            this.doublePendulumExample();
            this.export(data);
        });
        examplesMap.put("Pendulum Dance", (Data data) -> {
            this.pendulumDanceExample();
            this.export(data);
        });
    }

    private void reset(){
        logicMain = new LogicMain();
        this.massMap.clear();
        this.springMap.clear();
    }

    public void testExample(){
        this.reset();

        Mass m1 = this.addParticleMass(300,100,10);
        Mass m2 = this.addParticleMass(300,300,10);

        Line line = new Line(m1.getCenterX(),m1.getCenterY(),m2.getCenterX(),m2.getCenterY());
        line.setStroke(Color.CORAL);
        this.mainPane.getChildren().add(line);
        line.toBack();
        Spring spring = new Spring(m1,m2,1800,100);
        this.logicMain.getConnectorSystem().getSpringSystem().addSpring(spring);
        springMap.put(spring, line);
    }

    public void doublePendulumExample() {
        this.reset();

        Mass m1 = this.addParticleMass(300,100,10);
        Mass m2 = this.addParticleMass(350,200,10);
        Mass m3 = this.addParticleMass(600,200,10);

        m1.setxConst(true);
        m1.setyConst(true);

        this.addPendulum(m1,m2);
        this.addPendulum(m2,m3);

        this.logicMain.getSimulator().setGravity(true);
    }

    public void pendulumDanceExample() {
        this.reset();

        double angle = 25;
        Mass m0 = this.addParticleMass(400,100,10);
        Mass m1 = this.addParticleMass(400 + 223.7 * Math.sin(angle),100 + 223.7 * Math.cos(angle),10);
        Mass m2 = this.addParticleMass(400 + 202.9 * Math.sin(angle),100 + 202.9 * Math.cos(angle),10);
        Mass m3 = this.addParticleMass(400 + 184.8 * Math.sin(angle),100 + 184.8 * Math.cos(angle),10);
        Mass m4 = this.addParticleMass(400 + 169.1 * Math.sin(angle),100 + 169.1 * Math.cos(angle),10);
        Mass m5 = this.addParticleMass(400 + 155.3 * Math.sin(angle),100 + 155.3 * Math.cos(angle),10);
        Mass m6 = this.addParticleMass(400 + 143.1 * Math.sin(angle),100 + 143.1 * Math.cos(angle),10);
        Mass m7 = this.addParticleMass(400 + 132.3 * Math.sin(angle),100 + 132.3 * Math.cos(angle),10);
        Mass m8 = this.addParticleMass(400 + 122.7 * Math.sin(angle),100 + 122.7 * Math.cos(angle),10);
        Mass m9 = this.addParticleMass(400 + 114.1 * Math.sin(angle),100 + 114.1 * Math.cos(angle),10);
        Mass m10 = this.addParticleMass(400 + 106.4 * Math.sin(angle),100 + 106.4 * Math.cos(angle),10);
        Mass m11 = this.addParticleMass(400 + 99.4 * Math.sin(angle),100 + 99.4 * Math.cos(angle),10);
        Mass m12 = this.addParticleMass(400 + 93.1 * Math.sin(angle),100 + 93.1 * Math.cos(angle),10);
        Mass m13 = this.addParticleMass(400 + 87.4 * Math.sin(angle),100 + 87.4 * Math.cos(angle),10);
        Mass m14 = this.addParticleMass(400 + 82.2 * Math.sin(angle),100 + 82.2 * Math.cos(angle),10);
        Mass m15 = this.addParticleMass(400 + 77.4 * Math.sin(angle),100 + 77.4 * Math.cos(angle),10);

        m0.setxConst(true);
        m0.setyConst(true);

        this.addPendulum(m0,m1);
        this.addPendulum(m0,m2);
        this.addPendulum(m0,m3);
        this.addPendulum(m0,m4);
        this.addPendulum(m0,m5);
        this.addPendulum(m0,m6);
        this.addPendulum(m0,m7);
        this.addPendulum(m0,m8);
        this.addPendulum(m0,m9);
        this.addPendulum(m0,m10);
        this.addPendulum(m0,m11);
        this.addPendulum(m0,m12);
        this.addPendulum(m0,m13);
        this.addPendulum(m0,m14);
        this.addPendulum(m0,m15);

        log.debug(this.massMap.toString());

        this.logicMain.getSimulator().setGravity(true);
    }

    private Mass addParticleMass(double x, double y, double w) {
        Circle particleCircle = new Circle(0,0,2);
        particleCircle.setTranslateX(x);
        particleCircle.setTranslateY(y);
        particleCircle.setFill(Color.CORAL);
        particleCircle.setStroke(Color.BLACK);
        this.mainPane.getChildren().add(particleCircle);
        Mass m = new Particle(x,y,w);
        this.addMassNodeMap(m, particleCircle);
        return m;
    }

    private Spring addPendulum(Mass m1, Mass m2){
        Line line = new Line(m1.getCenterX(),m1.getCenterY(),m2.getCenterX(),m2.getCenterY());
        line.setStroke(Color.CORAL);
        this.mainPane.getChildren().add(line);
        line.toBack();
        Spring spring = new Spring(m1,m2,1800);
        this.logicMain.getConnectorSystem().getSpringSystem().addSpring(spring);
        this.springMap.put(spring, line);
        return spring;
    }

    private void addMassNodeMap(Mass mass, Shape shape) {
        this.logicMain.getMassSystem().addMass(mass);
        this.massMap.put(mass, shape);
    }

    public void export(Data data){
        log.debug(this.massMap.toString());

        data.targetMassMap.clear();
        data.targetSpringMap.clear();

        log.debug(this.massMap.toString());

        data.targetMassMap.putAll(this.massMap);
        data.targetSpringMap.putAll(this.springMap);

        data.logic.overwriteWith(this.logicMain);
    }

    public Map<String, Consumer<Data>> getExamplesMap(){
        return this.examplesMap;
    }

    public LogicMain getLogicMain() {
        return logicMain;
    }

    public Map<Mass, Shape> getMassMap() {
        return this.massMap;
    }

    public Map<Spring, Line> getSpringMap() {
        return this.springMap;
    }
}

package Logic.Simulator;

import Logic.Simulator.Dynamics.ForceCalculator;
import Logic.Simulator.Kinematics.Iterator;
import Logic.Systems.ConnectorsSystem.ConnectorSystem;
import Logic.Systems.MassSystem.MassSystem;
import Logic.Systems.MassSystem.Masses.Mass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Simulator {

    private MassSystem massSystem;
    private ConnectorSystem connectorSystem;
    private ForceCalculator forceCalculator;
    private Iterator iterator;
    private double time;

    public Simulator(MassSystem massSystem, ConnectorSystem connectorSystem) {
        this.massSystem = massSystem;
        this.connectorSystem = connectorSystem;
        this.forceCalculator = new ForceCalculator(this.massSystem, this.connectorSystem);
        this.iterator = new Iterator(massSystem);
        this.time = 0;
    }

    public void iterate(){
        List<Mass> massList = this.massSystem.massList();
        Mass m;
        for (Mass mass : massList) {
            m = mass;
            m.setForceX(0);
            m.setForceY(0);
        }
        m = massList.get(0);
        this.forceCalculator.applyForces();
        this.iterator.iterate();
        this.time += this.iterator.getDt();
        log.debug("Mass 1 @ {} : {} , {}", this.time, m.getCenterX(), m.getCenterY());
    }
    public void start() {}
    public void stop() {}
    public void reset() {}
}

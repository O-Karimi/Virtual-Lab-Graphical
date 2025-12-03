package Logic.Simulator.Dynamics;

import Logic.Simulator.Dynamics.Forces.SpringForce;
import Logic.Systems.ConnectorsSystem.ConnectorSystem;
import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import Logic.Systems.MassSystem.MassSystem;
import Logic.Systems.MassSystem.Masses.Mass;

import java.util.List;

public class ForceCalculator {
    private MassSystem massSystem;
    private ConnectorSystem connectorSystem;
    public ForceCalculator(MassSystem massSystem, ConnectorSystem connectorSystem) {
        this.massSystem = massSystem;
        this.connectorSystem = connectorSystem;
    }

    public void applyForces() {
        this.calculateSpringForce();
    }

    public void calculateSpringForce() {
        List<Spring> springList = this.connectorSystem.getSpringSystem().getSpringsList();
        SpringForce springForce = new SpringForce();
        double springForceX;
        double springForceY;
        double directionalForceX;
        double directionalForceY;
        Mass m1;
        Mass m2;
        for (Spring currentSpring : springList) {
            springForce.setData(currentSpring);
            springForceX = springForce.xForceCalculator();
            springForceY = springForce.yForceCalculator();
            m1 = currentSpring.getMasses().get(0);
            m2 = currentSpring.getMasses().get(1);
            directionalForceX = springForceX;// * Math.signum(m2.getCenterX() - m1.getCenterX());
            directionalForceY = springForceY;// * Math.signum(m2.getCenterY() - m1.getCenterY());
            m1.setForceX(m1.getForceX() - directionalForceX);
            m1.setForceY(m1.getForceY() - directionalForceY);
            m2.setForceX(m2.getForceX() + directionalForceX);
            m2.setForceY(m2.getForceY() + directionalForceY);
        }
    }
}

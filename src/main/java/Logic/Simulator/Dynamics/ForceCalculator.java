package Logic.Simulator.Dynamics;

import Logic.Simulator.Dynamics.Forces.SpringForce;
import Logic.Systems.ConnectorsSystem.ConnectorSystem;
import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import Logic.Systems.MassSystem.MassSystem;
import Logic.Systems.MassSystem.Masses.Mass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ForceCalculator {
    private MassSystem massSystem;
    private ConnectorSystem connectorSystem;
    private boolean withGravity;
    public ForceCalculator(MassSystem massSystem, ConnectorSystem connectorSystem) {
        this.massSystem = massSystem;
        this.connectorSystem = connectorSystem;
    }

    public void applyForces() {
        this.calculateSpringForce();
//        log.debug(String.valueOf(this.withGravity));
        if (isWithGravity()){
            this.calculateGravity();
        }
    }

    private void calculateGravity() {
        List<Mass> massList = this.massSystem.getMassList();
        for (Mass mass : massList){
            mass.setForceY(mass.getForceY() + mass.getWeight()*10);
        }
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

    public void setWithGravity(boolean withGravity) {
        this.withGravity = withGravity;
    }
    public boolean isWithGravity() {
        return withGravity;
    }
}

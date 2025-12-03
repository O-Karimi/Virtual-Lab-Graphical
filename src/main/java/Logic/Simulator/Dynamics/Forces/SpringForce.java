package Logic.Simulator.Dynamics.Forces;

import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import Logic.Systems.MassSystem.Masses.Mass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringForce extends Force<Spring> {
    private Spring spring;
    Mass  m1;
    Mass m2;
    double currentL;

    @Override
    public void setData(Spring spring) {
        this.spring = spring;
        m1 = spring.getMasses().get(0);
        m2 = spring.getMasses().get(1);
        currentL = this.distance(m1.getCenterX(), m1.getCenterY(), m2.getCenterX(), m2.getCenterY());
    }

    @Override
    public double forceTotal() {
        return -this.spring.getSpringConstant()*(this.spring.getInitialLength() - currentL);
    }

    @Override
    public double xForceCalculator() {
        return this.forceTotal() * (m1.getCenterX() - m2.getCenterX()) / currentL;
    }

    @Override
    public double yForceCalculator() {
        return this.forceTotal() * (m1.getCenterY() - m2.getCenterY()) / currentL;
    }
}

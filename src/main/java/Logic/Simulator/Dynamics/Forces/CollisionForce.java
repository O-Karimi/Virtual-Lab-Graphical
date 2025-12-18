package Logic.Simulator.Dynamics.Forces;

import Logic.Systems.MassSystem.Masses.Mass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollisionForce extends Force{
    Mass m1;
    Mass m2;
    private static double stiffness = 3000;
    private double currentL;

    public void setData(Mass massOne, Mass massTwo) {
        this.m1 = massOne;
        this.m2 = massTwo;
        this.currentL = this.distance(m1.getCenterX(), m1.getCenterY(),
                m2.getCenterX(), m2.getCenterY());
    }

    @Override
    public double forceTotal() {
        double allowedDistance = m1.getCharacteristicLength() + m2.getCharacteristicLength();
        double Force = 0;
        if (this.currentL < allowedDistance){
            log.debug("Masses Collision : {} , {}", m1.toString(), m2.toString());
            Force = CollisionForce.getStiffness() * (this.currentL - allowedDistance);
        }
        return Force;
    }

    @Override
    public double xForceCalculator() {
        return this.forceTotal() * (m1.getCenterX() - m2.getCenterX()) / currentL;
    }

    @Override
    public double yForceCalculator() {
        return this.forceTotal() * (m1.getCenterY() - m2.getCenterY()) / currentL;
    }

    public static double getStiffness() {
        return stiffness;
    }
    public static void setStiffness(double stiffness) {
        CollisionForce.stiffness = stiffness;
    }
}

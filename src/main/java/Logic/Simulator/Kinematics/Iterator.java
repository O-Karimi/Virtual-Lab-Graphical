package Logic.Simulator.Kinematics;

import Logic.Systems.MassSystem.MassSystem;
import Logic.Systems.MassSystem.Masses.Mass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Iterator {
    private MassSystem massSystem;
    private double dt;

    public Iterator(MassSystem massSystem) {
        this.massSystem = massSystem;
    };

    public void iterate() {
        this.calculateAccelerations();
        this.calculateVelocity();
        this.calculateDisplacement();
        this.dt = 0.02;
    }

    public void calculateAccelerations() {
        for(Mass m : this.massSystem.getMassList()){
            if (m.isxConst()) {
//                log.debug(m.toString());
                m.setAccelX(0);
            }else {
                m.setAccelX(m.getForceX() / m.getWeight());
            }
            if (m.isyConst()) {
//                log.debug(m.toString());
                m.setAccelY(0);
            }else {
                m.setAccelY(m.getForceY() / m.getWeight());
            }
        }
    }
    public void calculateVelocity() {
        for(Mass m : this.massSystem.getMassList()){
            m.setVelX(m.getVelX() + m.getAccelX()*dt);
            m.setVelY(m.getVelY() + m.getAccelY()*dt);
        }
    }
    public void calculateDisplacement() {
        for(Mass m : this.massSystem.getMassList()){
            m.setCenterX(m.getCenterX() + m.getVelX()*dt);
            m.setCenterY(m.getCenterY() + m.getVelY()*dt);
        }
    }

    public double getDt() {
        return dt;
    }
    public void setDt(double dt) {
        this.dt = dt;
    }
}

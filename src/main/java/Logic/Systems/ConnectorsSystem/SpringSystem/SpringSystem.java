package Logic.Systems.ConnectorsSystem.SpringSystem;

import Logic.Systems.MassSystem.MassSystem;
import Logic.Systems.MassSystem.Masses.Mass;

import java.util.ArrayList;
import java.util.List;

public class SpringSystem {

    private List<Spring> springsList =  new ArrayList<>();
    private MassSystem masSystem;

    public SpringSystem(MassSystem massSystem) {
        this.masSystem = massSystem;
    }

    public void addSpring(Spring spring) {
        this.springsList.add(spring);
    }

    public Object springExists(Mass massOne, Mass massTwo) {
        for (Spring spring : this.springsList) {
            if (spring.getMasses().containsAll(List.of(massOne, massTwo))) {
                return spring;
            }
        }
        return null;
    }

    public void deleteSpring(Spring spring) {
        springsList.remove(spring);
    }

    public MassSystem getMasSystem() {
        return masSystem;
    }

    public void setMasSystem(MassSystem masSystem) {
        this.masSystem = masSystem;
    }

    public List<Spring> getSpringsList() {
        return springsList;
    }
}
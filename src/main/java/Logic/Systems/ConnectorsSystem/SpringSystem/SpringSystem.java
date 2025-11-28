package Logic.Systems.ConnectorsSystem.SpringSystem;

import Logic.Systems.MassSystem.MassInitializer;
import Logic.Systems.MassSystem.Masses.Mass;

import java.util.List;

public class SpringSystem {

    private List<Spring> springsList;

    MassInitializer masSystem;
    SpringSystem(MassInitializer massInitializer) {
        this.masSystem = massInitializer;

    }
    public void addSpring(Mass massOne, Mass massTwo, double springConstant, double initialLength) {
        Spring spring = new Spring(massOne, massTwo, springConstant, initialLength);
        this.springsList.add(spring);
    }

    public Object getSpring(Mass massOne, Mass massTwo) {
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
}

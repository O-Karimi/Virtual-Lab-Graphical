package Logic.Systems.ConnectorsSystem;

import Logic.Systems.ConnectorsSystem.SpringSystem.SpringSystem;
import Logic.Systems.MassSystem.MassSystem;

public class ConnectorSystem {
    private SpringSystem springSystem;

    public ConnectorSystem(MassSystem massSystem) {
        this.springSystem = new SpringSystem(massSystem);
    }

    public SpringSystem getSpringSystem() {
        return springSystem;
    }

    public void setSpringSystem(SpringSystem springSystem) {
        this.springSystem = springSystem;
    }
}
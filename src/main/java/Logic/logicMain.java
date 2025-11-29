package Logic;

import Logic.Systems.ConnectorsSystem.ConnectorSystem;
import Logic.Systems.MassSystem.MassSystem;

public class logicMain {

    private ConnectorSystem connectorSystem;
    private MassSystem massSystem;

    public logicMain(){
        this.massSystem = new MassSystem();
        this.connectorSystem = new ConnectorSystem(this.massSystem);
    }

    public void setMassSystem(MassSystem massSystem){
        this.massSystem = new MassSystem();
    }
    public MassSystem getMassSystem(){
        return this.massSystem;
    }

    public ConnectorSystem getConnectorSystem() {
        return this.connectorSystem;
    }

    public void setConnectorSystem(ConnectorSystem connectorSystem) {
        this.connectorSystem = connectorSystem;
    }
}

package Logic;

import Logic.Systems.ConnectorsSystem.ConnectorInitializer;
import Logic.Systems.MassSystem.MassInitializer;

public class logicMain {

    private ConnectorInitializer connectorInitializer;
    private MassInitializer massInitializer;

    public logicMain(){
        this.massInitializer = new MassInitializer();
        this.connectorInitializer = new ConnectorInitializer();
    }

    public void setInitializer(MassInitializer massInitializer){
        this.massInitializer = new MassInitializer();
    }
    public MassInitializer getInitializer(){
        return this.massInitializer;
    }

    public ConnectorInitializer getConnectorInitializer() {
        return connectorInitializer;
    }

    public void setConnectorInitializer(ConnectorInitializer connectorInitializer) {
        this.connectorInitializer = connectorInitializer;
    }
}

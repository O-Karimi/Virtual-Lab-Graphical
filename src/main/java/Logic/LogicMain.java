package Logic;

import Logic.Simulator.Simulator;
import Logic.Systems.ConnectorsSystem.ConnectorSystem;
import Logic.Systems.MassSystem.MassSystem;

public class LogicMain {

    private ConnectorSystem connectorSystem;
    private MassSystem massSystem;
    private Simulator simulator;

    public LogicMain(){
        this.massSystem = new MassSystem();
        this.connectorSystem = new ConnectorSystem(this.massSystem);
        this.simulator = new Simulator(this.massSystem, this.connectorSystem);
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

    public void iterate(){
        this.simulator.iterate();
    }
    public void stop(){}

    public Simulator getSimulator() {
        return simulator;
    }
    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }
}
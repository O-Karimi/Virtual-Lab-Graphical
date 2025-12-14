package Logic;

import Logic.Simulator.Simulator;
import Logic.Systems.ConnectorsSystem.ConnectorSystem;
import Logic.Systems.Environment.EnvironmentSystem;
import Logic.Systems.MassSystem.MassSystem;

public class LogicMain {

    private MassSystem massSystem;
    private ConnectorSystem connectorSystem;
    private EnvironmentSystem environmentSystem;
    private Simulator simulator;

    public LogicMain(){
        this.massSystem = new MassSystem();
        this.connectorSystem = new ConnectorSystem(this.massSystem);
        this.environmentSystem = new EnvironmentSystem();
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

    public EnvironmentSystem getEnvironmentSystem() {
        return environmentSystem;
    }
    public void setEnvironmentSystem(EnvironmentSystem environmentSystem) {
        this.environmentSystem = environmentSystem;
    }
    public void overwriteWith(LogicMain source){
        this.setMassSystem(source.getMassSystem());
        this.setConnectorSystem(source.getConnectorSystem());
        this.setEnvironmentSystem(source.getEnvironmentSystem());
        this.setSimulator(source.getSimulator());
    }
}
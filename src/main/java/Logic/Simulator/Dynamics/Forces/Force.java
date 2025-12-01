package Logic.Simulator.Dynamics.Forces;

import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;

public abstract class Force<T> {

    abstract void setData(Spring spring);

    abstract public double forceTotal();
    abstract public double xForceCalculator();
    abstract public double yForceCalculator();
    abstract public void setData(T object);
    public double distance(double x1, double y1, double x2, double y2) {
        return Math.hypot(x1 - x2, y1 - y2);
    }
}

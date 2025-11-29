package Logic.Systems.ConnectorsSystem.SpringSystem;

import Logic.Systems.MassSystem.Masses.Mass;

import java.util.List;


public class Spring {
    private double springConstant;
    private double initialLength;
    private Mass massOne;
    private Mass massTwo;

    public Spring(Mass massOne, Mass massTwo, double springConstant, double initialLength) {
        this.massOne = massOne;
        this.massTwo = massTwo;
        this.springConstant = springConstant;
        this.initialLength = initialLength;
        massOne.addSpringConnection(massTwo);
        massTwo.addSpringConnection(massOne);
    }

    public List<Mass> getMasses() {
        // List.of creates a fixed list instantly
        return List.of(this.massOne, this.massTwo);
    }

    public double getSpringConstant() {
        return springConstant;
    }

    public void setSpringConstant(double springConstant) {
        this.springConstant = springConstant;
    }

    public double getInitialLength() {
        return initialLength;
    }

    public void setInitialLength(double initialLength) {
        this.initialLength = initialLength;
    }
}

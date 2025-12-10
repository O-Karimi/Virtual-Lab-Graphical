package Logic.Systems.ConnectorsSystem.SpringSystem;

import Logic.Systems.MassSystem.Masses.Mass;

import java.util.List;


public class Spring {
    private double springConstant;
    private double initialLength;
    private Mass massOne;
    private Mass massTwo;
    private static int counter = 0;
    protected int id;

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Spring.counter = counter;
    }

    public Spring(Mass massOne, Mass massTwo, double springConstant, double initialLength) {
        this.massOne = massOne;
        this.massTwo = massTwo;
        this.springConstant = springConstant;
        this.initialLength = initialLength;
        massOne.addSpringConnection(massTwo);
        massTwo.addSpringConnection(massOne);
        setCounter(getCounter()+1);
        this.id = getCounter();
    }

    public Spring(Mass massOne, Mass massTwo, double springConstant) {
        this.massOne = massOne;
        this.massTwo = massTwo;
        this.springConstant = springConstant;
        this.initialLength = Math.hypot(massOne.getCenterX()-massTwo.getCenterX(), massOne.getCenterY()-massTwo.getCenterY());
        massOne.addSpringConnection(massTwo);
        massTwo.addSpringConnection(massOne);
        setCounter(getCounter()+1);
        this.id = getCounter();
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
        if(initialLength == -1){
            this.initialLength = Math.hypot(massOne.getCenterX()-massTwo.getCenterX(), massOne.getCenterY()-massTwo.getCenterY());
        }else {
            this.initialLength = initialLength;
        }
    }

    @Override
    public String toString() {
        return String.format("Spring %d - Mass %d & Mass %d",this.id, this.getMasses().get(0).getId(),
                this.getMasses().get(1).getId());
    }}

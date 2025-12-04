package Logic.Systems.MassSystem.Masses;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Mass {
    private double centerX;
    private double centerY;
    private double velX;
    private double velY;
    private double accelX;
    private double accelY;
    private double forceX;
    private double forceY;
    private double weight;
    private double characteristicLength;
    private boolean hasFriction;
    private boolean fixedRestriction;
    private List<Mass> springConnectedMasses = new ArrayList<Mass>();
    private static int counter = 0;
    private int id;

    private double initialCenterX;
    private double initialCenterY;
    private double initialVelX;
    private double initialVelY;

    protected void massInit(){
        setCounter(getCounter()+1);
        this.id = getCounter();
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Mass.counter = counter;
    }

    @Override
    public String toString() {
        return "Mass " + this.id
                + ", type: " + this.getClass().getSimpleName();
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCharacteristicLength(){
        return characteristicLength;
    }
    public void setCharacteristicLength(double characteristicLength){
        this.characteristicLength = characteristicLength;
    }

    public double[] getCoordinates() {
        return new double[]{this.centerX, this.centerY};
    }
    public void  setCoordinates(@NotNull double[] coordinates) {
        this.centerX = coordinates[0];
        this.centerY = coordinates[1];
    }
    public double getCenterX() {
        return this.centerX;
    }
    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }
    public double getCenterY() {
        return this.centerY;
    }
    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public abstract boolean isContact(double x, double y);
    public abstract double[] contactDirection(double x, double y);

    public boolean getFriction(){
        return this.hasFriction;
    }
    public void setFriction(boolean hasFriction){
        this.hasFriction = hasFriction;
    }

    public void setFixedRestriction(boolean isRestricted){
        this.fixedRestriction = isRestricted;
    }
    public boolean getFixedRestriction(){
        return this.fixedRestriction;
    }

    public void addSpringConnection(Mass mass) {
        this.springConnectedMasses.add(mass);
    }

    public double getInitialCenterX() {
        return initialCenterX;
    }
    public void setInitialCenterX(double initialCenterX) {
        this.initialCenterX = initialCenterX;
    }
    public double getInitialCenterY() {
        return initialCenterY;
    }
    public void setInitialCenterY(double initialCenterY) {
        this.initialCenterY = initialCenterY;
    }
    public double getInitialVelX() {
        return initialVelX;
    }
    public void setInitialVelX(double initialVelX) {
        this.initialVelX = initialVelX;
    }
    public double getInitialVelY() {
        return initialVelY;
    }
    public void setInitialVelY(double initialVelY) {
        this.initialVelY = initialVelY;
    }
    public double getAccelX() {
        return accelX;
    }
    public void setAccelX(double accelX) {
        this.accelX = accelX;
    }
    public double getAccelY() {
        return accelY;
    }
    public void setAccelY(double accelY) {
        this.accelY = accelY;
    }
    public double getForceX() {
        return forceX;
    }
    public void setForceX(double forceX) {
        this.forceX = forceX;
    }
    public double getForceY() {
        return forceY;
    }
    public void setForceY(double forceY) {
        this.forceY = forceY;
    }
    public double getVelX() {
        return velX;
    }
    public void setVelX(double velX) {
        this.velX = velX;
    }
    public double getVelY() {
        return velY;
    }
    public void setVelY(double velY) {
        this.velY = velY;
    }

    public int getId() {
        return this.id;
    }
}

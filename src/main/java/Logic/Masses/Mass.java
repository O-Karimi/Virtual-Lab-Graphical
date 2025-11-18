package Logic.Masses;

import org.jetbrains.annotations.NotNull;

public abstract class Mass {
    private double centerX;
    private double centerY;
    private double weight;
    private double characteristicLength;
    private boolean hasFriction;
    private boolean fixedRestriction;

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
}

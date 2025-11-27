package Logic.Masses;

public class SquareMass extends Mass {

    private double length;

    public void setLength(double length) {
        this.length = length;
    }
    public double getLength() {
        return this.length;
    }

    public SquareMass(double centerX, double centerY, double weight, double length){
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setWeight(weight);
        this.setCharacteristicLength(length);
        setCounter(getCounter()+1);
        this.id = getCounter();

    }

    @Override
    public boolean isContact(double x, double y) {
        return Math.hypot(this.getCenterX() - x, this.getCenterY() - y) < this.getLength();
    }

    @Override
    public double[] contactDirection(double x, double y) {
        double[] direction = new  double[2];
        direction[0] = Math.atan(this.getCenterY() - y / this.getCenterX() - x);
        direction[1] = Math.atan(this.getCenterX() - x / this.getCenterY() - y);
        return direction;
    }
}

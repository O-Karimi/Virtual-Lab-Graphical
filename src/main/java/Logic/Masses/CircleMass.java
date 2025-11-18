package Logic.Masses;

public class CircleMass extends Mass {

    public CircleMass(double centerX, double centerY, double weight, double radius){
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setWeight(weight);
        this.setCharacteristicLength(radius);
    }

    @Override
    public boolean isContact(double x, double y) {
        return false;
    }

    @Override
    public double[] contactDirection(double x, double y) {
        list direction =
        return new double[0];
    }
}

package Logic.Systems.MassSystem.Masses;

public class CircleMass extends Mass {

    private double radius;

    public void setRadius(double radius) {
        this.radius = radius;
    }
    public double getRadius() {
        return this.radius;
    }

    public CircleMass(double centerX, double centerY, double weight, double radius){
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setWeight(weight);
        this.setCharacteristicLength(radius);

        this.setInitialCenterX(centerX);
        this.setInitialCenterY(centerY);
        this.setInitialVelX(0);
        this.setInitialVelY(0);
        this.massInit();
    }

    @Override
    public boolean isContact(double x, double y) {
        return Math.hypot(this.getCenterX() - x, this.getCenterY() - y) < this.getRadius();
    }

    @Override
    public double[] contactDirection(double x, double y) {
        double[] direction = new  double[2];
        direction[0] = Math.atan(this.getCenterY() - y / this.getCenterX() - x);
        direction[1] = Math.atan(this.getCenterX() - x / this.getCenterY() - y);
        return direction;
    }
}

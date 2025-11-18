package Logic.Masses;

public class Particle extends Mass {

    public Particle(double centerX, double centerY, double mass){
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setWeight(mass);
    }

    @Override
    public boolean isContact(double x, double y) {
        return false;
    }

    @Override
    public double[] contactDirection(double x, double y) {
        double[] direction = new  double[2];
        direction[0] = Math.acos(x/y);
        direction[1] = Math.asin(x/y);
        return direction;
    }
}

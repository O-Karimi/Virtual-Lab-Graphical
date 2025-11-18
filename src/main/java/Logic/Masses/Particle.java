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
        return new double[0];
    }
}

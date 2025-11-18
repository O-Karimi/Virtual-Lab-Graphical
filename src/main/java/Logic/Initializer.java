package Logic;

import Logic.Masses.CircleMass;
import Logic.Masses.Mass;
import Logic.Masses.Particle;

import java.util.ArrayList;
import java.util.List;

public class Initializer {

    List<Mass> massList = new ArrayList<Mass>();

    public void addParticleMass(double centerX,  double centerY, double weight){
        this.massList.add(new Particle(centerX,centerY,weight));
    }
    public void addCircleMass(double centerX,  double centerY, double weight, double radius){
        this.massList.add(new CircleMass(centerX,centerY,weight,radius));
    }

    public void deleteMass(Mass mass){
        this.massList.remove(mass);
    }

    public List<Mass> massList(){
        return this.massList;
    }
}

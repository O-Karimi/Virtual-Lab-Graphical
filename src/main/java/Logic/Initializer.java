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
    public void addSquareMass(double centerX,  double centerY, double weight, double length){
        this.massList.add(new CircleMass(centerX,centerY,weight,length));
    }

    public void deleteMass(Mass mass){
        this.massList.remove(mass);
    }

    public List<Mass> massList(){
        return this.massList;
    }

    public String toString() {
        String str = "";
        for(Mass mass : this.massList){
            str += mass.toString();
            str += "\n";
        }
        return str;
    }

}

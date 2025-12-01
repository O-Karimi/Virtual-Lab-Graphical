package Logic.Systems.MassSystem;

import Logic.Systems.MassSystem.Masses.CircleMass;
import Logic.Systems.MassSystem.Masses.Mass;
import Logic.Systems.MassSystem.Masses.Particle;

import java.util.ArrayList;
import java.util.List;

public class MassSystem {

    List<Mass> massList = new ArrayList<Mass>();

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

    public Mass  getMass(int id){
        return this.massList.get(id);
    }

    public void addMass(Mass mass) {
        this.massList.add(mass);
    }
}

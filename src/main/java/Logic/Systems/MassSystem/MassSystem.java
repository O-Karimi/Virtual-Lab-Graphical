package Logic.Systems.MassSystem;

import Logic.Systems.MassSystem.Masses.CircleMass;
import Logic.Systems.MassSystem.Masses.Mass;
import Logic.Systems.MassSystem.Masses.Particle;

import java.util.ArrayList;
import java.util.List;

public class MassSystem {

    List<Mass> getMassList = new ArrayList<Mass>();

    public void deleteMass(Mass mass){
        this.getMassList.remove(mass);
    }

    public List<Mass> getMassList(){
        return this.getMassList;
    }

    public String toString() {
        String str = "";
        StringBuilder strBuilder = new StringBuilder(str);
        for(Mass mass : this.getMassList){

            strBuilder.append(mass.toString());
            strBuilder.append("\n");
        }
        return str;
    }

    public Mass getMass(int id){
        return this.getMassList.get(id);
    }

    public void addMass(Mass mass) {
        this.getMassList.add(mass);
    }
}

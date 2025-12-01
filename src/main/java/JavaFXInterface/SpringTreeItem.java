package JavaFXInterface;

import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import Logic.Systems.MassSystem.Masses.Mass;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import javafx.scene.control.TreeItem;
import lombok.Getter;

import java.util.List;

@Getter
public class SpringTreeItem {

    // A helper method to get the data back later
    private final Spring spring;

    public SpringTreeItem(Spring spring) {
        // Format the text: "Spring (MassA <-> MassB)"
        List<Mass> masses = spring.getMasses();
        super();
        this.spring = spring;
    }

}

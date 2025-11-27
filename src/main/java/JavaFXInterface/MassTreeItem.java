package JavaFXInterface;
import Logic.Masses.Mass;
import javafx.scene.control.TreeItem;
import lombok.Getter;

// Extends TreeItem<String> so it works with your TreeView<String>
@Getter
public class MassTreeItem extends TreeItem<String> {

    // A helper method to get the data back later
    private final Mass mass;

    public MassTreeItem(Mass mass) {
        // 1. Format the string exactly how you want it here
        super("Mass: " + mass.toString());
        this.mass = mass;
    }

}
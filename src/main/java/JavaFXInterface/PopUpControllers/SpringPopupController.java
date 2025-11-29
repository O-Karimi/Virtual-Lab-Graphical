package JavaFXInterface.PopUpControllers;

import Logic.Systems.MassSystem.MassSystem;
import Logic.Systems.MassSystem.Masses.Mass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.lang.reflect.Type;

public class SpringPopupController {

    @FXML
    private ComboBox<Mass> MassOneSelector;
    @FXML
    private ComboBox<Mass> MassTwoSelector;
    @FXML
    private TextField SpringConstantField;

    private MassSystem massSystem;

    public record SpringData(Mass massOne, Mass massTwo, double springConstant) {}
    // Helper method to package the data
    public void initialize(){
        MassOneSelector.setOnShowing(event -> {
            MassOneSelector.setItems(FXCollections.observableArrayList(this.massSystem.massList()));
        });
        MassTwoSelector.setOnShowing(event -> {
            MassTwoSelector.setItems(FXCollections.observableArrayList(this.massSystem.massList()));
        });
    }
    public void setSystem(MassSystem system) {
        this.massSystem = system;
    }
    public SpringData getData() {
        Object m1 = MassOneSelector.getValue();
        Object m2 = MassTwoSelector.getValue();
        String k = SpringConstantField.getText();
        if (m1 != null && m2 != null) {
            if(k.matches("[0-9]*([.,][0-9]*)?")) {
                return new SpringData(
                        MassOneSelector.getValue(),
                        MassTwoSelector.getValue(),
                        Double.parseDouble(k)
                        );
            }
            return null;
        }
        return null;
    }
}

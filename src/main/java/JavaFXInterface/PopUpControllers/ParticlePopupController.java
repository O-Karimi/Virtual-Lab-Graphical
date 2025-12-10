package JavaFXInterface.PopUpControllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ParticlePopupController extends PopUpController{

    @FXML
    private TextField particleXField;
    @FXML
    private TextField particleYField;
    @FXML
    private TextField weightField;
    @FXML
    private CheckBox xConstCheckbox;
    @FXML
    private CheckBox yConstCheckbox;

    public record Data(float particleX, float particleY, float weight, boolean xConst, boolean yConst) {}
    // Helper method to package the data
    public Record getCollectedData() {
        String x = particleXField.getText();
        String y = particleYField.getText();
        String w = weightField.getText();
        boolean xC = xConstCheckbox.isSelected();
        boolean yC = yConstCheckbox.isSelected();
        if (x.matches("[0-9]*([.,][0-9]*)?") &&
        y.matches("[0-9]*([.,][0-9]*)?") &&
        w.matches("[0-9]*([.,][0-9]*)?")) {
            float particleX = Float.parseFloat(x);
            float particleY = Float.parseFloat(y);
            float weight = Float.parseFloat(w);
            return new Data(
                    particleX,
                    particleY,
                    weight,
                    xC,
                    yC
            );
        }
        return null;
    }
}
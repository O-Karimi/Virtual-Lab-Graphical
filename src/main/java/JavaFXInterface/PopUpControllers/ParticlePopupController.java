package JavaFXInterface.PopUpControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ParticlePopupController extends PopUpController{

    @FXML
    private TextField particleXField;
    @FXML
    private TextField particleYField;
    @FXML
    private TextField weightField;

    public record Data(float particleX, float particleY, float weight) {}
    // Helper method to package the data
    public Record getCollectedData() {
        String x = particleXField.getText();
        String y = particleYField.getText();
        String w = weightField.getText();
        if (x.matches("[0-9]*([.,][0-9]*)?") &&
        y.matches("[0-9]*([.,][0-9]*)?") &&
        w.matches("[0-9]*([.,][0-9]*)?")) {
            float particleX = Float.parseFloat(x);
            float particleY = Float.parseFloat(y);
            float weight = Float.parseFloat(w);
            return new Data(
                    particleX,
                    particleY,
                    weight
            );
        }
        return null;
    }
}
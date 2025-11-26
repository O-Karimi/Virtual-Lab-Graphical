package JavaFXInterface;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PopupController {

    @FXML
    private TextField particleXField;
    @FXML
    private TextField particleYField;
    @FXML
    private TextField weightField;

    public record ParticleData(float particleX, float particleY, float weight) {}
    // Helper method to package the data
    public ParticleData getCollectedData() {
        String x = particleXField.getText();
        String y = particleYField.getText();
        String w = weightField.getText();
        if (x.matches("[0-9]*([.,][0-9]*)?") &&
        y.matches("[0-9]*([.,][0-9]*)?") &&
        w.matches("[0-9]*([.,][0-9]*)?")) {
            float particleX = Float.parseFloat(x);
            float particleY = Float.parseFloat(y);
            float weight = Float.parseFloat(w);
            return new ParticleData(
                    particleX,
                    particleY,
                    weight
            );
        }
        return null;
    }
}
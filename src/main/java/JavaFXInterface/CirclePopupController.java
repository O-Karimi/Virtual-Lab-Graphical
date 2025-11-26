package JavaFXInterface;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CirclePopupController {

    @FXML
    private TextField circleXField;
    @FXML
    private TextField circleYField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField circleRadiusField;

    public record CircleData(float circleX, float circleY, float radius, float weight) {}
    // Helper method to package the data
    public CircleData getCollectedData() {
        String x = circleXField.getText();
        String y = circleYField.getText();
        String r = circleRadiusField.getText();
        String w = weightField.getText();
        if (x.matches("[0-9]*([.,][0-9]*)?") &&
        y.matches("[0-9]*([.,][0-9]*)?") &&
        r.matches("[0-9]*([.,][0-9]*)?") &&
        w.matches("[0-9]*([.,][0-9]*)?")) {
            float circleX = Float.parseFloat(x);
            float circleY = Float.parseFloat(y);
            float circleR = Float.parseFloat(r);
            float weight = Float.parseFloat(w);
            return new CircleData(
                    circleX,
                    circleY,
                    circleR,
                    weight
            );
        }
        return null;
    }
}
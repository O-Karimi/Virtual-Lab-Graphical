package JavaFXInterface;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SquarePopupController {

    @FXML
    private TextField squareXField;
    @FXML
    private TextField squareYField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField squareLengthField;

    public record SquareData(float squareX, float squareY, float length, float weight) {}
    // Helper method to package the data
    public SquareData getCollectedData() {
        String x = squareXField.getText();
        String y = squareYField.getText();
        String l = squareLengthField.getText();
        String w = weightField.getText();
        if (x.matches("[0-9]*([.,][0-9]*)?") &&
        y.matches("[0-9]*([.,][0-9]*)?") &&
        l.matches("[0-9]*([.,][0-9]*)?") &&
        w.matches("[0-9]*([.,][0-9]*)?")) {
            float squareX = Float.parseFloat(x);
            float squareY = Float.parseFloat(y);
            float squareL = Float.parseFloat(l);
            float weight = Float.parseFloat(w);
            return new SquareData(
                    squareX,
                    squareY,
                    squareL,
                    weight
            );
        }
        return null;
    }
}
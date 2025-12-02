package JavaFXInterface.PopUpControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SquarePopupController extends PopUpController {

    @FXML
    private TextField squareXField;
    @FXML
    private TextField squareYField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField squareLengthField;

    public record Data(float squareX, float squareY, float length, float weight) {}
    // Helper method to package the data
    public Record getCollectedData() {
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
            return new Data(
                    squareX,
                    squareY,
                    squareL,
                    weight
            );
        }
        return null;
    }
}
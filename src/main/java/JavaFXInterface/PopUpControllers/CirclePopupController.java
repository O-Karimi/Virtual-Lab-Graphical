package JavaFXInterface.PopUpControllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CirclePopupController extends PopUpController{

    @FXML
    private TextField circleXField;
    @FXML
    private TextField circleYField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField circleRadiusField;
    @FXML
    private CheckBox xConstCheckbox;
    @FXML
    private CheckBox yConstCheckbox;

    public record Data(float circleX, float circleY, float radius, float weight, boolean xConst, boolean yConst) {}
    // Helper method to package the data
    public Record getCollectedData() {
        String x = circleXField.getText();
        String y = circleYField.getText();
        String r = circleRadiusField.getText();
        String w = weightField.getText();
        boolean xC = xConstCheckbox.isSelected();
        boolean yC = yConstCheckbox.isSelected();
        if (x.matches("[0-9]*([.,][0-9]*)?") &&
        y.matches("[0-9]*([.,][0-9]*)?") &&
        r.matches("[0-9]*([.,][0-9]*)?") &&
        w.matches("[0-9]*([.,][0-9]*)?")) {
            float circleX = Float.parseFloat(x);
            float circleY = Float.parseFloat(y);
            float circleR = Float.parseFloat(r);
            float weight = Float.parseFloat(w);
            return new Data(
                    circleX,
                    circleY,
                    circleR,
                    weight,
                    xC,
                    yC
            );
        }
        return null;
    }
}
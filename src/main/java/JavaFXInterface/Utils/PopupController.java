package JavaFXInterface.Utils;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PopupController {

    public record InputRequest(
            String key,          // The ID to retrieve the answer later (e.g., "mass_weight")
            String label,        // What the user sees (e.g., "Enter Weight:")
            InputType type,      // What kind of input?
            Object defaultValue  // Initial value (optional)
    ) {}

    public enum InputType {
        TEXT, NUMBER, BOOLEAN, CHOICE
    }

    public static Map<String, Object> show(String title, List<InputRequest> requests) {

        Dialog<Map<String, Object>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("Please configure the Properties:");

        ButtonType okButton = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // This map stores the "Node" (TextField/CheckBox) so we can read it later
        Map<String, Control> inputControls = new HashMap<>();

        int row = 0;
        for (InputRequest req : requests) {

            grid.add(new Label(req.label()), 0, row);

            Control inputControl = createControl(req);
            grid.add(inputControl, 1, row);

            inputControls.put(req.key(), inputControl);

            row++;
        }

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == okButton) {
                return collectResults(inputControls, requests);
            }
            return null;
        });

        Optional<Map<String, Object>> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private static Control createControl(InputRequest req) {
        switch (req.type()) {
            case BOOLEAN:
                CheckBox cb = new CheckBox();
                cb.setSelected((Boolean) req.defaultValue());
                return cb;

            case NUMBER:
                TextField tfNum = new TextField(req.defaultValue().toString());
                // Force numeric (optional simple validation)
                tfNum.textProperty().addListener((obs, old, neu) -> {
                    if (!neu.matches("-?\\d*(\\.\\d*)?")) tfNum.setText(old);
                });
                return tfNum;

            case CHOICE:
                // If it's a choice, we assume defaultValue is the List of options
                ComboBox<Object> box = new ComboBox<>();
                if (req.defaultValue() instanceof List<?> list) {
                    box.getItems().addAll(list);
                    if (!list.isEmpty()) box.getSelectionModel().select(0);
                }
                return box;

            default: // TEXT
                return new TextField(req.defaultValue().toString());
        }
    }

    private static Map<String, Object> collectResults(Map<String, Control> controls, List<InputRequest> requests) {
        Map<String, Object> results = new HashMap<>();

        for (InputRequest req : requests) {
            Control control = controls.get(req.key());
            Object value = null;

            if (req.type() == InputType.BOOLEAN) {
                value = ((CheckBox) control).isSelected();
            }
            else if (req.type() == InputType.NUMBER) {
                String text = ((TextField) control).getText();
                value = text.isEmpty() ? 0.0 : Double.parseDouble(text);
            }
            else if (req.type() == InputType.CHOICE) {
                value = ((ComboBox<?>) control).getValue();
            }
            else {
                value = ((TextField) control).getText();
            }

            results.put(req.key(), value);
        }
        return results;
    }
}

package JavaFXInterface;

import Logic.Systems.MassSystem.Masses.Mass;
import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PropertyGenerator {

    private static List<Runnable> pendingUpdates = new ArrayList<>();

    /**
     * Main method: Clears the grid and repopulates it based on the object type.
     */
    public static void populateGrid(GridPane grid, Object item) {
        // 1. Clear old fields
        grid.getChildren().clear();

        // Use AtomicInteger to keep track of which row we are on
        AtomicInteger row = new AtomicInteger(0);

        if (item instanceof Mass mass) {
            // Standard Mass Properties
            addRow(grid, row, "Mass ID", mass::getId, null); // Read-only
            addRow(grid, row, "X Pos", mass::getCenterX, mass::setCenterX);
            addRow(grid, row, "Y Pos", mass::getCenterY, mass::setCenterY);
            addRow(grid, row, "Weight", mass::getWeight, mass::setWeight);
        }
        else if (item instanceof Spring spring) {
            // Standard Spring Properties
            addRow(grid, row, "Spring ID", spring::toString, null);
            addRow(grid, row, "Rest Len", spring::getInitialLength, spring::setInitialLength);
            addRow(grid, row, "Rest Len", spring::getInitialLength, spring::setInitialLength);
            addRow(grid, row, "Stiffness", spring::getSpringConstant, spring::setSpringConstant);

        }
    }
    /**
     * Execute all the saved tasks (Called when button is clicked)
     */
    public static void applyChanges() {
        for (Runnable task : pendingUpdates) {
            task.run();
        }

        pendingUpdates.clear();
        System.out.println("All properties updated!");
    }

    private static void addRow(GridPane grid, AtomicInteger rowIndex,
                               String labelText,
                               Supplier<Object> getter,
                               Consumer<Double> setter) {

        int r = rowIndex.getAndIncrement();
        grid.add(new Label(labelText + ":"), 0, r);

        TextField field = new TextField();
        field.setText(String.valueOf(getter.get()));

        if (setter != null) {
            // INSTEAD of updating immediately, we add a task to the list
            Runnable saveTask = () -> {
                try {
                    double val = Double.parseDouble(field.getText());
                    setter.accept(val);
                } catch (Exception e) {
                    System.out.println("Invalid number for " + labelText);
                }
            };

            // Add to our list so the button can run it later
            pendingUpdates.add(saveTask);

        } else {
            field.setDisable(true);
        }
        grid.add(field, 1, r);
    }
    /**
     * Helper to add a Label (Col 0) and TextField (Col 1) at the specific row.
     */
//    private static void addRow(GridPane grid, AtomicInteger rowIndex,
//                               String labelText,
//                               Supplier<Object> getter,
//                               Consumer<Double> setter) {
//
//        int r = rowIndex.getAndIncrement(); // Get row, then add 1
//
//        // 1. Create Label
//        Label label = new Label(labelText + ":");
//        grid.add(label, 0, r); // Column 0, Row r
//
//        // 2. Create Field
//        TextField field = new TextField();
//        field.setText(String.valueOf(getter.get()));
//
//        if (setter != null) {
//            field.setOnAction(e -> {
//                try {
//                    double val = Double.parseDouble(field.getText());
//                    setter.accept(val);
//                    // Optional: Request focus back to main pane if needed
//                } catch (NumberFormatException ex) {
//                    field.setText(String.valueOf(getter.get())); // Reset on error
//                }
//            });
//
//            // Auto-save on focus lost
//            field.focusedProperty().addListener((obs, oldVal, newVal) -> {
//                if (!newVal && setter != null) {
//                    try {
//                        setter.accept(Double.parseDouble(field.getText()));
//                    } catch (Exception ignored) {}
//                }
//            });
//        } else {
//            field.setEditable(false);
//            field.setDisable(true);
//        }
//
//        grid.add(field, 1, r); // Column 1, Row r
//    }
}
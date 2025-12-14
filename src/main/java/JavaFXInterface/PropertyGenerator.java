package JavaFXInterface;

import Logic.Systems.MassSystem.Masses.Mass;
import Logic.Systems.ConnectorsSystem.SpringSystem.Spring;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
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

    public static void populateGrid(GridPane grid, Object item) {
        grid.getChildren().clear();

        AtomicInteger row = new AtomicInteger(0);

        if (item instanceof Mass mass) {
            // Standard Mass Properties
            addRow(grid, row, "Mass ID", mass::getId, null); // Read-only
            addRow(grid, row, "X Pos", mass::getCenterX, mass::setCenterX);
            addRow(grid, row, "Y Pos", mass::getCenterY, mass::setCenterY);
            addRow(grid, row, "Weight", mass::getWeight, mass::setWeight);
            addBooleanRow(grid, row, "X Const", mass::isxConst, mass::setxConst);
            addBooleanRow(grid, row, "Y Const", mass::isyConst, mass::setyConst);
        }
        else if (item instanceof Spring spring) {
            // Standard Spring Properties
            addRow(grid, row, "Spring ID", spring::toString, null);
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

    private static void addBooleanRow(GridPane grid, AtomicInteger rowIndex,
                                      String labelText,
                                      Supplier<Boolean> getter,
                                      Consumer<Boolean> setter) {
        int r = rowIndex.getAndIncrement();
        grid.add(new Label(labelText + ":"), 0, r);
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(getter.get());
        if (setter != null) {
            // Option A: If you are using the "Update Button" (Deferred Save)
            // We add a task that checks the box status *later* when button is clicked
            pendingUpdates.add(() -> {
                boolean finalState = checkBox.isSelected();
                setter.accept(finalState);
            });
            // Option B: If you want Instant Updates (No Update Button)
            // checkBox.setOnAction(e -> setter.accept(checkBox.isSelected()));

        } else {
            checkBox.setDisable(true); // Read-only
        }

        grid.add(checkBox, 1, r);    }

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
}
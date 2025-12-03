import JavaFXInterface.HelloController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FullSystemTest extends ApplicationTest {

    private HelloController controller; // Reference to your main controller

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Launch the App
        FXMLLoader loader = new FXMLLoader(getClass().getResource("JavaFXInterface/PhysicsLabGUI.fxml"));
        Parent root = loader.load();
        controller = loader.getController(); // Grab controller so we can check logic later!
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testSimpleSpring() {
        // 1. Automate UI Interaction (The Ghost User)
        clickOn("#particleButton");  // Click the button with fx:id="particleButton"

        // The popup is open now. Type data.
        clickOn(".dialog-pane .text-field"); // Click the first text field found
        write("100"); // X
        type(KeyCode.TAB);
        write("100"); // Y
        type(KeyCode.TAB);
        write("50");  // Weight
        clickOn("Create");

        clickOn("#particleButton");  // Click the button with fx:id="particleButton"

        clickOn(".dialog-pane .text-field"); // Click the first text field found
        write("400"); // X
        type(KeyCode.TAB);
        write("400"); // Y
        type(KeyCode.TAB);
        write("80");  // Weight
        clickOn("Create"); // Click the button with text "Create"

        clickOn("#springButtom");  // Click the button with fx:id="particleButton"

        clickOn("#MassOneSelector"); // Click the first text field found
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#MassTwoSelector"); // Click the first text field found
        type(KeyCode.UP);
        type(KeyCode.ENTER);
        clickOn("Create"); // Click the button with text "Create"
        // 2. CHECK THE LOGIC (The crucial part!)
        // Access your logic engine through the controller to verify data


        // 3. CHECK THE VISUALS
        // Verify the label on screen updated
        //verifyThat("#objectsCountLabel", hasText("1"));

        try { Thread.sleep(10000); } catch (InterruptedException e) {}
    }
}
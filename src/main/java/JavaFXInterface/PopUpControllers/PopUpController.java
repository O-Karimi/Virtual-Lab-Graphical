package JavaFXInterface.PopUpControllers;

import java.util.List;


public abstract class PopUpController {
    public record Data(List<Object> items){};
    public abstract Record getCollectedData();

}

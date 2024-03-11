package EMS.GUI.utilities;

import EMS.System.Elevator.Elevator;
import EMS.System.utilities.ElevatorStatus;
import EMS.System.utilities.MoveDirection;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class GuiElementLoader {
    public GuiElementLoader () {}

    public Rectangle getElevatorRectangle(MoveDirection direction, ElevatorStatus status) {
        Color color = Color.GREEN;
        if (direction == MoveDirection.STOP) {
            color = Color.RED;
        } else if (status == ElevatorStatus.OUT_OF_SERVICE) {
            color = Color.BLACK;
        } else if (direction == MoveDirection.IDLE) {
            color = Color.BLUE;
        }
        return new Rectangle(30, 30, color);
    }
}

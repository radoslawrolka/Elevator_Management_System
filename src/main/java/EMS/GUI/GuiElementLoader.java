package EMS.GUI;

import EMS.System.MoveDirection;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class GuiElementLoader {
    GuiElementLoader() {}

    public Rectangle getElevatorRectangle(MoveDirection direction) {
        Color color = Color.GREEN;
        switch (direction) {
            case STOP -> color = Color.RED;
            case IDLE -> color = Color.BLUE;
        }
        return new Rectangle(30, 30, color);
    }
}

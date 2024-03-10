package EMS.System.utilities;

import EMS.System.Elevator.Elevator;

import java.util.List;

public interface ChangeObserver {
    void refreshView(List<Elevator> elevators);
}

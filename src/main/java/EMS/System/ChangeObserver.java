package EMS.System;

import java.util.List;

public interface ChangeObserver {
    void refreshView(List<Elevator> elevators);
}

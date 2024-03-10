package EMS.System.Engine;

import EMS.System.Elevator.Elevator;
import EMS.System.utilities.Call;
import EMS.System.utilities.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchedulerTest {
    @Test
    public void scheduleTest() {
        Elevator elevator1 = createElevator(3);
        Elevator elevator2 = createElevator(8);
        Elevator elevator3 = createElevator(5);
        Scheduler scheduler = new Scheduler(List.of(elevator1, elevator2, elevator3));

        scheduler.addCall(new Call(4, MoveDirection.DOWN));
        scheduler.addCall(new Call(7, MoveDirection.UP));

        scheduler.schedule();
        elevator1.move();elevator1.move();
        elevator2.move();elevator2.move();
        elevator3.move();elevator3.move();


        assertEquals(4, elevator1.getCurrentFloor());
        assertEquals(7, elevator2.getCurrentFloor());
        assertEquals(5, elevator3.getCurrentFloor());
    }

    private Elevator createElevator(int destination) {
        Elevator elevator = new Elevator();
        elevator.addStop(destination);
        while (elevator.getCurrentFloor() != destination) {
            elevator.move();
        }
        elevator.move();
        return elevator;
    }
}

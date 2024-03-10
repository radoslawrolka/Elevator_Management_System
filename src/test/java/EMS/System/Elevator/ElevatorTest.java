package EMS.System.Elevator;

import org.junit.jupiter.api.Test;
import EMS.System.utilities.MoveDirection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorTest {
    @Test
    public void tripWithOneStopTest() {
        Elevator elevator = new Elevator();
        elevator.addStop(5);
        elevator.addStop(3);
        elevator.addStop(7);

        elevator.move();
        assertEquals(MoveDirection.UP, elevator.getDirection());
        assertEquals(1, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.UP, elevator.getDirection());
        assertEquals(2, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.STOP, elevator.getDirection());
        assertEquals(3, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.UP, elevator.getDirection());
        assertEquals(3, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.UP, elevator.getDirection());
        assertEquals(4, elevator.getCurrentFloor());
    }

    @Test
    public void tripWithAllStops() {
        Elevator elevator = new Elevator();
        elevator.addStop(2);
        elevator.addStop(3);
        elevator.addStop(4);

        elevator.move();
        assertEquals(MoveDirection.UP, elevator.getDirection());
        assertEquals(1, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.STOP, elevator.getDirection());
        assertEquals(2, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.UP, elevator.getDirection());
        assertEquals(2, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.STOP, elevator.getDirection());
        assertEquals(3, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.UP, elevator.getDirection());
        assertEquals(3, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.STOP, elevator.getDirection());
        assertEquals(4, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.IDLE, elevator.getDirection());
        assertEquals(4, elevator.getCurrentFloor());
    }

    @Test
    public void tripIdle() {
        Elevator elevator = new Elevator();

        elevator.move();
        assertEquals(MoveDirection.IDLE, elevator.getDirection());
        assertEquals(1, elevator.getCurrentFloor());

        elevator.move();
        assertEquals(MoveDirection.IDLE, elevator.getDirection());
        assertEquals(1, elevator.getCurrentFloor());
    }
}

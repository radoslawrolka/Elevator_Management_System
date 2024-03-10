package EMS.System.utilities;

import EMS.System.Elevator.Elevator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ElevatorListTest {

    @Test
    public void isCloserTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Elevator elevator1 = createElevator(5);
        Elevator elevator2 = createElevator(3);
        Elevator elevator3 = createElevator(7);
        ElevatorList elevatorList = new ElevatorList();
        elevatorList.add(elevator1);
        elevatorList.add(elevator2);
        elevatorList.add(elevator3);

        Method method = ElevatorList.class.getDeclaredMethod("isCloser", Elevator.class, Optional.class, int.class);
        method.setAccessible(true);

        assertTrue((boolean) method.invoke(elevatorList, elevator1, Optional.empty(), 4));
        assertFalse((boolean) method.invoke(elevatorList, elevator2, Optional.of(elevator1), 4));
        assertFalse((boolean) method.invoke(elevatorList, elevator2, Optional.of(elevator1), 4));
    }

    @Test
    public void isOnWayTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Elevator elevator1 = createElevator(5);
        Elevator elevator2 = createElevator(3);
        Elevator elevator3 = createElevator(7);
        ElevatorList elevatorList = new ElevatorList();
        elevatorList.add(elevator1);
        elevatorList.add(elevator2);
        elevatorList.add(elevator3);

        Method method = ElevatorList.class.getDeclaredMethod("isOnWay", Elevator.class, MoveDirection.class, int.class);
        method.setAccessible(true);

        assertTrue((boolean) method.invoke(elevatorList, elevator1, MoveDirection.UP, 6));
        assertFalse((boolean) method.invoke(elevatorList, elevator1, MoveDirection.UP, 4));
        assertTrue((boolean) method.invoke(elevatorList, elevator2, MoveDirection.DOWN, 2));
        assertFalse((boolean) method.invoke(elevatorList, elevator2, MoveDirection.DOWN, 4));
    }

    @Test
    public void getElevatorTest() {
        Elevator elevator1 = createElevator(5);
        Elevator elevator2 = createElevator(3);
        Elevator elevator3 = createElevator(7);
        ElevatorList elevatorList = new ElevatorList();
        elevatorList.add(elevator1);
        elevatorList.add(elevator2);
        elevatorList.add(elevator3);

        Call call1 = new Call(4, MoveDirection.UP);
        Call call2 = new Call(6, MoveDirection.DOWN);
        Call call3 = new Call(2, MoveDirection.UP);
        Call call4 = new Call(8, MoveDirection.DOWN);

        assertEquals(elevator1, elevatorList.getElevator(call1).orElse(null));
        assertEquals(elevator1, elevatorList.getElevator(call2).orElse(null));
        assertEquals(elevator2, elevatorList.getElevator(call3).orElse(null));
        assertEquals(elevator3, elevatorList.getElevator(call4).orElse(null));
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

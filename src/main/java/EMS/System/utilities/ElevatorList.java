package EMS.System.utilities;

import EMS.System.Elevator.Elevator;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Optional;

public class ElevatorList extends ArrayList<Elevator>{
    public Optional<Elevator> getElevator(Call call) {
        MoveDirection direction = call.direction();
        int floor = call.floor();
        Optional<Elevator> best = Optional.empty();

        for (Elevator elevator : this) {
            if (elevator.getStatus() != ElevatorStatus.RUNNING) {continue;}
            if (elevator.getDirection() == direction) {
                if (isOnWay(elevator, direction, floor) && isCloser(elevator, best, floor)) {
                    best = Optional.of(elevator);
                }
            } else if (elevator.isIdle() && isCloser(elevator, best, floor)) {
                best = Optional.of(elevator);
            }
        }
        return best;
    }

    private boolean isCloser(Elevator elevator, Optional<Elevator> best, int floor) {
        return best.isEmpty() || abs(elevator.getCurrentFloor() - floor) < abs(best.get().getCurrentFloor() - floor);
    }

    private boolean isOnWay(Elevator elevator, MoveDirection direction, int floor) {
        return direction.getValue() * (floor - elevator.getCurrentFloor()) > 0 && (direction == elevator.getDirection() || elevator.isIdle());
    }
}

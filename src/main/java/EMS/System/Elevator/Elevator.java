package EMS.System.Elevator;

import EMS.System.utilities.MoveDirection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Elevator {
    private Integer currentFloor = 1;
    private MoveDirection direction = MoveDirection.IDLE;
    private final Set<Integer> currentStops = new HashSet<>();
    private final Set<Integer> bufferStops = new HashSet<>();

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public void addStop(Integer floor) {
        (direction.getValue()*(floor - currentFloor) >= 0 ? currentStops : bufferStops).add(floor);
    }

    public void addStop(Integer floor, MoveDirection direction) {
        if (this.direction == MoveDirection.IDLE) {
            currentStops.add(floor);
        } else {
            (direction == this.direction ? currentStops : bufferStops).add(floor);
        }
    }

    public void move() {
        switch (direction) {
            case UP, DOWN -> go();
            case STOP -> start();
            case IDLE -> check();
        }
    }

    private void go() {
        currentFloor += direction.getValue();
        if (currentStops.contains(currentFloor)) {
            stop();
        }
    }

    private void stop() {
        this.direction = MoveDirection.STOP;
        currentStops.remove(currentFloor);
        if (currentStops.isEmpty()) {
            currentStops.addAll(bufferStops);
            bufferStops.clear();
        }
    }

    private void start() {
        if (currentStops.isEmpty()) {
            direction = MoveDirection.IDLE;
        } else {
            direction = currentStops.iterator().next() > currentFloor ? MoveDirection.UP : MoveDirection.DOWN;
        }
    }

    private void check() {
        if (!currentStops.isEmpty()) {
            if (Objects.equals(currentStops.iterator().next(), currentFloor)) {
                stop();
            } else {
                direction = currentStops.iterator().next() > currentFloor ? MoveDirection.UP : MoveDirection.DOWN;
            }
        }
    }

    public boolean isIdle() {
        return direction == MoveDirection.IDLE;
    }
}

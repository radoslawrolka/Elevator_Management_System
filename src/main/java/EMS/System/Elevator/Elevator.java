package EMS.System.Elevator;

import EMS.System.utilities.MoveDirection;

import java.util.TreeSet;

public class Elevator {
    private Integer currentFloor = 1;
    private MoveDirection direction = MoveDirection.IDLE;
    private final TreeSet<Integer> stops = new TreeSet<>();

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public void addStop(Integer floor) {
        stops.add(floor);
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
        if (stops.contains(currentFloor)) {
            stop();
        }
    }

    private void stop() {
        this.direction = MoveDirection.STOP;
        stops.remove(currentFloor);
    }

    private void start() {
        if (stops.isEmpty()) {
            direction = MoveDirection.IDLE;
        } else {
            direction = stops.first() > currentFloor ? MoveDirection.UP : MoveDirection.DOWN;
        }
    }

    private void check() {
        if (!stops.isEmpty()) {
            direction = stops.first() > currentFloor ? MoveDirection.UP : MoveDirection.DOWN;
        }
    }

    public boolean isIdle() {
        return direction == MoveDirection.IDLE;
    }
}

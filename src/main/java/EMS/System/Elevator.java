package EMS.System;

import java.util.Set;

public class Elevator {
    private Integer currentFloor = 1;
    private MoveDirection direction = MoveDirection.IDLE;
    private Set<Integer> stops;

    public Elevator() {}

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
            case UP -> {
                currentFloor++;
                if (stops.contains(currentFloor)) {
                    stop();
                }
            }
            case DOWN -> {
                currentFloor--;
                if (stops.contains(currentFloor)) {
                    stop();
                }
            }
            case STOP -> start();
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
            int nextFloor = stops.iterator().next();
            if (nextFloor > currentFloor) {
                direction = MoveDirection.UP;
            } else if (nextFloor < currentFloor) {
                direction = MoveDirection.DOWN;
            }
        }
    }
}

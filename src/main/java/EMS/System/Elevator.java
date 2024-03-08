package EMS.System;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class Elevator {
    private Integer currentFloor = 1;
    private MoveDirection direction = MoveDirection.IDLE;
    private final List<Integer> stops = new LinkedList<>();

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
        System.out.println("move:");
        for (Integer stop : stops) {
            System.out.println(stop);
        }
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
            case STOP, IDLE -> start();
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
            stops.remove(currentFloor);
            if (stops.isEmpty()) {
                direction = MoveDirection.IDLE;
                return;
            }
            Integer nextFloor = stops.get(0);
            if (nextFloor > currentFloor) {
                direction = MoveDirection.UP;
            } else if (nextFloor < currentFloor) {
                direction = MoveDirection.DOWN;
            }
        }
    }
}

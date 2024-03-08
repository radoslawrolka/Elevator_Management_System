package EMS.System;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {
    private final List<Elevator> elevators = new ArrayList<>();
    private final List<Call> calls = new ArrayList<>();
    private final List<ChangeObserver> observers = new ArrayList<>();
    //private final Comparator<Elevator> =

    public Engine(int elevators) {
        for (int i = 0; i < elevators; i++) {
            this.elevators.add(new Elevator());
        }
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void addObserver(ChangeObserver observer) {
        observers.add(observer);
    }

    public void addCall(Call call) {
        calls.add(call);
    }

    public void internalCall(Integer elevator, Integer floor) {
        elevators.get(elevator).addStop(floor);
    }

    public void run() {
        while (true) {
            updateObservers();
            update();
            moveElevators();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateObservers() {
        for (ChangeObserver observer : observers) {
            observer.refreshView(elevators);
        }
    }

    private void moveElevators() {
        for (Elevator elevator : elevators) {
            elevator.move();
        }
    }

    private void update() {
        for (Call call : calls) {

        }
    }
}

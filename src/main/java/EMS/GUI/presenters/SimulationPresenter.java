package EMS.GUI.presenters;

import EMS.GUI.utilities.Config;
import EMS.GUI.utilities.GuiElementLoader;
import EMS.System.Elevator.Elevator;
import EMS.System.Engine.Engine;
import EMS.System.utilities.Call;
import EMS.System.utilities.ChangeObserver;
import EMS.System.utilities.MoveDirection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class SimulationPresenter implements ChangeObserver {
    private final int width = 30;
    private final int height = 30;
    private final GuiElementLoader elementLoader = new GuiElementLoader();
    private int floors;
    private Engine engine;
    private Thread engineThread;

    @FXML
    private GridPane buttonGrid;
    @FXML
    private GridPane elevatorGrid;
    @FXML
    private GridPane innerGrid;

    public void initData(Config config) {
        this.floors = config.floors();
        this.engine = new Engine(config.elevators());
        setupButtons(floors);
        setupElevators(config.elevators());
        setupInner(config.elevators());
        engine.addObserver(this);
        engineThread = new Thread(engine);
        engineThread.start();
    }

    @Override
    public void refreshView(List<Elevator> elevators) {
        Platform.runLater(() -> {
            clearGrid();
            setupElevators(elevators.size());
            for (int i = 0; i < elevators.size(); i++) {
                Elevator elevator = elevators.get(i);
                Rectangle rect = elementLoader.getElevatorRectangle(elevator.getDirection(), elevator.getStatus());
                elevatorGrid.add(rect, i+1, floors-elevator.getCurrentFloor()+1);
            }
        });
    }

    public void stopSimulation() {
        engine.breakSimulation();
        engineThread.interrupt();
    }

    private void setupButtons(int floors) {
        for (int i = 0; i < 3; i++) {
            buttonGrid.getColumnConstraints().add(new ColumnConstraints(width));
        }

        for (int i = 0; i < floors; i++) {
            buttonGrid.getRowConstraints().add(new RowConstraints(height));
            Label label = new Label(Integer.toString(floors-i));
            buttonGrid.add(label, 0, i);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = 1; i < floors; i++) {
            Button button = new Button("^");
            int finalI = floors-i;
            button.setOnAction(event -> callElevator(finalI, MoveDirection.UP));
            buttonGrid.add(button, 1, i);
            GridPane.setHalignment(button, HPos.CENTER);
        }
        for (int i = 0; i < floors-1; i++) {
            Button button = new Button("v");
            int finalI = floors-i;
            button.setOnAction(event -> callElevator(finalI, MoveDirection.DOWN));
            buttonGrid.add(button, 2, i);
            GridPane.setHalignment(button, HPos.CENTER);
        }
    }

    private void setupElevators(int elevators) {
        elevatorGrid.getRowConstraints().add(new RowConstraints(height));
        for (int i = 0; i < elevators; i++) {
            elevatorGrid.getColumnConstraints().add(new ColumnConstraints(width));
            Label label = new Label(Integer.toString(i+1));
            elevatorGrid.add(label, i+1, 0);
            GridPane.setHalignment(label, HPos.CENTER);
            Button button = new Button("X");
            int finalI = i;
            button.setOnAction(event -> engine.toggle(finalI));
            elevatorGrid.add(button, i+1, floors+1);
            GridPane.setHalignment(button, HPos.CENTER);
        }
        elevatorGrid.getRowConstraints().add(new RowConstraints(height));

        for (int i = 0; i < floors; i++) {
            elevatorGrid.getRowConstraints().add(new RowConstraints(height));
            Label label = new Label(Integer.toString(floors-i));
            elevatorGrid.add(label, 0, i+1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void setupInner(int elevators) {
        for (int i = 0; i < elevators; i++) {
            innerGrid.getRowConstraints().add(new RowConstraints(height));
            Spinner<Integer> spinner = new Spinner<>(1, floors, 1);
            Label label = new Label("Elevator " + (i+1));
            Button button = new Button("Go");
            int finalI = i;
            button.setOnAction(event -> engine.internalCall(finalI, spinner.getValue()));
            innerGrid.add(label, 0, i);
            innerGrid.add(spinner, 1, i);
            innerGrid.add(button, 2, i);
        }
    }

    private void clearGrid() {
        elevatorGrid.getChildren().retainAll(elevatorGrid.getChildren().get(0));
        elevatorGrid.getColumnConstraints().clear();
        elevatorGrid.getRowConstraints().clear();
    }

    private void callElevator(int floor, MoveDirection direction) {
        engine.addCall(new Call(floor, direction));
    }
}

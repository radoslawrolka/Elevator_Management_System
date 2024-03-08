package EMS.GUI;

import EMS.System.ChangeObserver;
import EMS.System.Elevator;
import EMS.System.Engine;
import EMS.System.MoveDirection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class SimulationPresenter implements ChangeObserver {
    private final int width = 30;
    private final int height = 30;
    private int floors;
    private Engine engine;
    private final GuiElementLoader elementLoader = new GuiElementLoader();

    @FXML
    private GridPane buttonGrid;
    @FXML
    private GridPane elevatorGrid;
    @FXML
    private void initialize() {}

    public void initData(Config config) {
        this.floors = config.floors();
        this.engine = new Engine(config.elevators());
        setupButtons(floors);
        setupElevators(config.elevators());
        engine.addObserver(this);
        new Thread(engine).start();
    }

    private void setupButtons(int floors) {
        for (int i = 0; i < 5; i++) {
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
            buttonGrid.add(button, 4, i);
            GridPane.setHalignment(button, HPos.CENTER);
        }
    }

    private void setupElevators(int elevators) {
        for (int i = 0; i < elevators+1; i++) {
            elevatorGrid.getColumnConstraints().add(new ColumnConstraints(width));
        }

        for (int i = 0; i < floors; i++) {
            elevatorGrid.getRowConstraints().add(new RowConstraints(height));
            Label label = new Label(Integer.toString(floors-i));
            elevatorGrid.add(label, 0, i);
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void clearGrid() {
        elevatorGrid.getChildren().retainAll(elevatorGrid.getChildren().get(0));
        elevatorGrid.getColumnConstraints().clear();
        elevatorGrid.getRowConstraints().clear();
    }

    @Override
    public void refreshView(List<Elevator> elevators) {
        Platform.runLater(() -> {
            clearGrid();
            setupElevators(elevators.size());
            for (int i = 0; i < elevators.size(); i++) {
                Elevator elevator = elevators.get(i);
                Rectangle rect = elementLoader.getElevatorRectangle(elevator.getDirection());
                elevatorGrid.add(rect, i+1, floors-elevator.getCurrentFloor());
            }
        });
    }

    private void callElevator(int floor, MoveDirection direction) {
        //engine.callElevator(new Call(floor, direction));
    }
}

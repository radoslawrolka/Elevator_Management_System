package EMS.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SimulationPresenter {
    private int floors;
    private int elevators;

    @FXML
    private void initialize() {}

    public void initData(Config config) {
        this.floors = config.floors();
        this.elevators = config.elevators();
        System.out.println("Received floors: " + floors);
        System.out.println("Received elevators: " + elevators);
    }

    @FXML
    private VBox vbox;
    @FXML
    private Label l;
    @FXML
    private Button btn;

    // add a new button to the vbox
    @FXML
    public void btnAdd() {
        Button newBtn = new Button("New Button");
        vbox.getChildren().add(newBtn);
    }
}

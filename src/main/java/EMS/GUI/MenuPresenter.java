package EMS.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

public class MenuPresenter {
    @FXML
    private Spinner<Integer> Floors;
    @FXML
    private Spinner<Integer> Elevators;

    private Config readInput() {
        return new Config(Elevators.getValue(), Floors.getValue());
    }

    @FXML
    private void Start() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/simulation.fxml"));
            Parent root = loader.load();
            SimulationPresenter simulationPresenter = loader.getController();
            simulationPresenter.initData(readInput());
            Stage simulationStage = configureStage(root);
            simulationStage.show();
            simulationStage.setOnCloseRequest(event -> onClose(simulationPresenter, simulationStage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onClose(SimulationPresenter simulationController, Stage simulationStage) {
        if (simulationController != null) {
            simulationController.stopSimulation();
        }
        simulationStage.close();
    }

    private Stage configureStage(Parent root) {
        Stage simulationStage = new Stage();
        simulationStage.setTitle("Elevator Management System - Simulation");
        simulationStage.setScene(new Scene(root));
        return simulationStage;
    }
}

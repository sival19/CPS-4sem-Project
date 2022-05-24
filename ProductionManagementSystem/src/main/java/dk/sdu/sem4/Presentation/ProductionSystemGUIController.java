package dk.sdu.sem4.Presentation;

import dk.sdu.sem4.Logic.orchestrator.IOrchestrator;
import dk.sdu.sem4.Logic.orchestrator.Orchestrator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;


public class ProductionSystemGUIController implements Initializable {
    private final IOrchestrator orchestrator = new Orchestrator();

    private Thread init;

    //labels
    // text fields

    @FXML
    private Rectangle whErrorMarker, whExecutingMarker, assemblyErrorMarker, assemblyExecutingMarker, agvExecutingMarker, agvChargingMarker;
    @FXML
    private TextField batteryStatus, agvProgramName, agvState, assemblyState, assemblyProgramName, whProgramName, whState;


    @FXML
    protected void onAbortProductionClick() {
//        init.start();
        Platform.exit();
        System.exit(1);
        System.out.println("Production abort button clicked");
    }

    @FXML
    protected void onStartProductionClick() {
        orchestrator.startProductionSequence();
        System.out.println("Production start button clicked");
    }

    @FXML
    protected void onStopProductionClick() {
        orchestrator.stopProductionSequence();
        System.out.println("Production stop button clicked");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init = new Thread(
                () -> {
                    while (true) {
                        showAGVData(orchestrator.getAgvState());
                        showAssemblyData(orchestrator.getAssemblyState());
                        showWarehoueData(orchestrator.getWarehouseState());

                        try {
                            orchestrator.startSequence();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
        init.start();
    }

    public void showAssemblyData(int stAS) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // only showing program name if the program is executing
        switch (stAS) {
            case -1 -> {
                assemblyState.setText("Default");
                assemblyProgramName.setText("Waiting");
                assemblyExecutingMarker.setVisible(false);
                assemblyErrorMarker.setVisible(false);
            }
            case 0 -> {
                assemblyState.setText("Idle");
                assemblyProgramName.setText("Waiting");
                assemblyExecutingMarker.setVisible(false);
                assemblyErrorMarker.setVisible(false);
            }
            case 1 -> {
                assemblyState.setText("Executing");
                assemblyProgramName.setText("Assembling");
                assemblyExecutingMarker.setVisible(true);
                assemblyErrorMarker.setVisible(false);
            }
            case 2 -> {
                assemblyState.setText("Error");
                assemblyProgramName.setText("Error");
                assemblyExecutingMarker.setVisible(false);
                assemblyErrorMarker.setVisible(true);
            }
            default -> {
                assemblyState.setText("Invalid");
                assemblyProgramName.setText("Invalid");
                assemblyExecutingMarker.setVisible(false);
                assemblyErrorMarker.setVisible(false);
            }
        }
    }


    public void showAGVData(int stAGV) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        batteryStatus.setText(String.valueOf(orchestrator.getAGVbattery()));
        // only showing program name if the program is executing or charging
        switch (stAGV) {
            case 0 -> {
                agvState.setText("Default");
                agvProgramName.setText("Waiting");
                agvExecutingMarker.setVisible(false);
                agvChargingMarker.setVisible(false);
            }
            case 1 -> {
                agvState.setText("Idle");
                agvProgramName.setText("Waiting");
                agvExecutingMarker.setVisible(false);
                agvChargingMarker.setVisible(false);
            }
            case 2 -> {
                agvState.setText("Executing");
                agvProgramName.setText(orchestrator.getAGVProgram());
                agvExecutingMarker.setVisible(true);
                agvChargingMarker.setVisible(false);
            }
            case 3 -> {
                agvState.setText("Charging");
                agvProgramName.setText(orchestrator.getAGVProgram());
                agvExecutingMarker.setVisible(false);
                agvChargingMarker.setVisible(true);
            }
            default -> {
                agvState.setText("Invalid");
                agvProgramName.setText("Invalid");
                agvExecutingMarker.setVisible(false);
                agvChargingMarker.setVisible(false);
            }
        }
    }

    public void showWarehoueData(int stWh) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch (stWh) {
            case -1 -> {
                whState.setText("Default");
                whProgramName.setText("Waiting");
                whExecutingMarker.setVisible(false);
                whErrorMarker.setVisible(false);
            }
            case 0 -> {
                whState.setText("Idle");
                whProgramName.setText("Waiting");
                whExecutingMarker.setVisible(false);
                whErrorMarker.setVisible(false);
            }
            //Change This
            case 1 -> {
                whState.setText("Executing");
                whProgramName.setText("MovingItem");
                whExecutingMarker.setVisible(true);
                whErrorMarker.setVisible(false);
            }
            case 2 -> {
                whState.setText("Error");
                whProgramName.setText("Error");
                whExecutingMarker.setVisible(false);
                whErrorMarker.setVisible(true);
            }
            default -> {
                whState.setText("Invalid");
                whProgramName.setText("Invalid");
                whExecutingMarker.setVisible(false);
                whErrorMarker.setVisible(false);
            }
        }
    }


}



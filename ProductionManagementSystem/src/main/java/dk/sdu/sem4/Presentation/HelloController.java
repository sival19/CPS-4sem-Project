package dk.sdu.sem4.Presentation;
import dk.sdu.sem4.Logic.ISubscriber;
import dk.sdu.sem4.Logic.orchestrator.IOrchestrator;
import dk.sdu.sem4.Logic.orchestrator.Orchestrator;
import javafx.scene.shape.Rectangle;

import dk.sdu.sem4.Logic.AGV.AGVsubscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    ISubscriber agv = new AGVsubscriber();
    IOrchestrator orchestrator = new Orchestrator();
    int battery;
    int state;
    String programName ="";
    //labels
    @FXML
    private Label welcomeText;
    // text fields
    @FXML
    private TextField agvStatus;
    @FXML
    private TextField batteryStatus;
    @FXML
    private TextField agvProgramName;
    @FXML
    private TextField agvState;
    // other details
    @FXML
    private Rectangle agvExecutingMarker;
    @FXML
    private Rectangle agvChargingMarker;
    // production control buttons
    @FXML
    protected void onStartProductionClick(){
        orchestrator.startSequence();
        System.out.println("Production start button clicked");
    }

    @FXML
    protected void onStopProductionClick(){
        System.out.println("Production stop button clicked");
    }

    @FXML
    protected void onPauseProductionClick(){
        System.out.println("Production pause button clicked");
    }

    @FXML
    protected void onAbortProductionClick(){
        System.out.println("Production abort button clicked");
    }

    @FXML
    protected void onChargeButtonClick(){
        agv.SendMessage("MoveToChargerOperation");
    }


    @FXML
    protected void onAssemblyButtonClick(){

//        agv.SendMessage("PutAssemblyOperation");
    }


    @FXML
    protected void onWarehouseButtonClick() {

        agv.SendMessage("MoveToStorageOperation");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

//                    System.out.println(orchestrator.getAGVbattery());

                    agvState.setText(String.valueOf(orchestrator.getAGVstate()));
                    batteryStatus.setText(String.valueOf(orchestrator.getAGVbattery()));
                    agvProgramName.setText(String.valueOf(orchestrator.getAGVProgram()));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

}

package dk.sdu.sem4.Presentation;
import dk.sdu.sem4.Logic.Assembly.AssemblySubscriber;
import dk.sdu.sem4.Logic.ISubscriber;
import dk.sdu.sem4.Logic.WH.WarehouseSubscriber;
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
    ISubscriber assembly= new AssemblySubscriber();

    ISubscriber wh = new WarehouseSubscriber();
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
    @FXML
    private TextField assemblyState;
    @FXML
    private TextField assemblyProgramName;
    // other details
    @FXML
    private TextField whProgramName;

    @FXML
    private TextField whState;
    @FXML
    private Rectangle agvExecutingMarker;
    @FXML
    private Rectangle agvChargingMarker;
    // production control buttons
    @FXML
    protected void onStartProductionClick(){
        orchestrator.startSequence();
//        System.out.println("Production start button clicked");
    }

    @FXML
    protected void onStopProductionClick(){ System.out.println("Production stop button clicked");}

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
        agv.SendMessage("MoveToAssemblyOperation");
    }
    @FXML
    void onAGVputAssemblyClick() {

        agv.SendMessage("PutAssemblyOperation");
    }


    @FXML
    protected void onWarehouseButtonClick() {

        agv.SendMessage("MoveToStorageOperation");


    }
    @FXML
    void onAssembleClick() {
        System.out.println("Assembly task sent");
        assembly.SendMessage("1234");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

//                  System.out.println(orchestrator.getAGVbattery());
                    assemblyProgramName.setText(String.valueOf(orchestrator.getAssemblyProgram()));
                    agvState.setText(String.valueOf(orchestrator.getAgvState()));
                    assemblyState.setText(String.valueOf(orchestrator.getAssemblyState()));
                    batteryStatus.setText(String.valueOf(orchestrator.getAGVbattery()));
                    agvProgramName.setText(String.valueOf(orchestrator.getAGVProgram()));
                    orchestrator.startSequence();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
    public void parseAssemblyState(int stateAS){
        switch(stateAS){
            case 0: assemblyState.setText("Idle");
            case 1: assemblyState.setText("Executing");
            case 2: assemblyState.setText("Error");
            default: assemblyState.setText("Default");
        }
    }
    public void parseAGVState(int stAGV){
        switch(stAGV){
            case 0: agvState.setText("Default");
            case 1: agvState.setText("Idle");
            case 2: assemblyState.setText("Executing");
            case 3: assemblyState.setText("Charging");
            default: assemblyState.setText("Default");
        }
    }
}



package dk.sdu.sem4.Presentation;
import com.hazelcast.topic.Message;
import javafx.scene.shape.Rectangle;
import org.json.JSONArray;
import org.json.JSONObject;

import dk.sdu.sem4.Logic.AGV.AGVsubscriber;
import dk.sdu.sem4.Logic.AGV.IAGVsubscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class HelloController implements Initializable {
    IAGVsubscriber agv = new AGVsubscriber();
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
        agv.SendMessage("PutAssemblyOperation");
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
                    agvStatus.setText(agv.getMessage());
                    // checking if the message is null before setting the values
                    if (agvStatus.getText() == null || agvStatus.getText().trim().isEmpty()) {
                        // setting initial AGV state before any messages come in
                        agvState.setText("Ready");
                    }else{
                        //String json = "{\"battery\":100,\"program name\":\"MoveToStorageOperation\",\"state\":2,\"timestamp\":\"2022-05-10T19:24:48.0047398+00:00\"}";
                        String json = agvStatus.getText();
                        //parsing JSON message from AGV to battery, state, program name variables
                        JSONObject ob = new JSONObject(json);
                        int state = ob.getInt("state");
                        String programName = ob.getString("program name");
                        int battery = ob.getInt("battery");

                        //agvState.setText("state: " + state);
                        // code for changing state marker colors depending on state
                        if(state==1){
                            agvState.setText("Ready");
                        }
                        if(state==2){
                            agvExecutingMarker.setVisible(true);
                            agvState.setText("Executing");
                        }
                        else if(state==3){
                            agvChargingMarker.setVisible(true);
                            agvState.setText("Charging");
                        }
                        else{agvExecutingMarker.setVisible(false);
                        agvChargingMarker.setVisible(false);}
                        agvProgramName.setText(programName);
                        batteryStatus.setText(battery+" %");

                    }
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }catch(InterruptedException e){
                    }
                }
            }
        }).start();
    }

}

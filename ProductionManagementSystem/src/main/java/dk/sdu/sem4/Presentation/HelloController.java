package dk.sdu.sem4.Presentation;

import dk.sdu.sem4.Logic.AGV.AGVsubscriber;
import dk.sdu.sem4.Logic.AGV.IAGVsubscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class HelloController implements Initializable {
    IAGVsubscriber agv = new AGVsubscriber();

    @FXML
    private Label welcomeText;
    @FXML
    private TextField agvStatus;



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
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }catch(InterruptedException e){
                    }
                }
            }
        }).start();

    }

}

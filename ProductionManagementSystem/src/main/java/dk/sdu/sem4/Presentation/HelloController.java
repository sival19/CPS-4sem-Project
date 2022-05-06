package dk.sdu.sem4.Presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("PLEASE WORK!!!!!");
    }
}

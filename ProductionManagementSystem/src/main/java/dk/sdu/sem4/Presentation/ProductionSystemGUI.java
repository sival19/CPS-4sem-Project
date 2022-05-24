package dk.sdu.sem4.Presentation;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductionSystemGUI extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProductionSystemGUI.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1066, 574);
        stage.setTitle("Drone Production System");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}

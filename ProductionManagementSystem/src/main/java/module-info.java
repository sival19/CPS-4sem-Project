module ProductionManagementSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires hazelcast.all;
    requires java.se;

    opens dk.sdu.sem4.Presentation to javafx.fxml;
    exports dk.sdu.sem4.Presentation;


}
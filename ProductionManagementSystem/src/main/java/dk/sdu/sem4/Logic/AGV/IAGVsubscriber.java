package dk.sdu.sem4.Logic.AGV;

public interface IAGVsubscriber {

    void SendMessage(String action);
    String getMessage();

}

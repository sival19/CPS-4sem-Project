package dk.sdu.sem4.Logic;

public interface ISubscriber {

    void SendMessage(String action);
    String getMessage();
}

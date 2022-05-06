package dk.sdu.sem4.Logic.Assembly;

public interface IAssemblySubscriber {
    void SendMessage(String action);
    String getMessage();
}

package dk.sdu.sem4.Logic.WH;
import dk.sdu.sem4.Domain.TopicHandler;

public class WarehouseSubscriber implements IWHsubscriber {

    private TopicHandler topicSubscriber;
    private String message;

    @Override
    public void SendMessage(String action) {
        topicSubscriber.PublishMessage("WarehouseFromJava", action);
    }

    public String getMessage() {
        message = topicSubscriber.getMessage();
        return message;
    }

    public WarehouseSubscriber() {
        topicSubscriber = new TopicHandler();
        topicSubscriber.TopicSubscriber("WarehouseToJava");
    }
}

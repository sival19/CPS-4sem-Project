package dk.sdu.sem4.Warehouse;
import dk.sdu.sem4.Hazelcast.TopicHandler;

public class WarehouseSubscriber {

    private TopicHandler topicSubscriber;
    private String message;

    public String getMessage() {
        message = topicSubscriber.getMessage();
        return message;
    }

    public WarehouseSubscriber() {
        topicSubscriber = new TopicHandler();
        topicSubscriber.TopicSubscriber("WarehousePubTopic");
    }
}

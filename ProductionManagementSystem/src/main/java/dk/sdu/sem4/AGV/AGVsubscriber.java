package dk.sdu.sem4.AGV;

import dk.sdu.sem4.Hazelcast.TopicHandler;

public class AGVsubscriber {

    private TopicHandler topicSubscriber;
    private String message;

    public String getMessage() {
        message = topicSubscriber.getMessage();
        return message;
    }

    public AGVsubscriber() {
        topicSubscriber = new TopicHandler();
        topicSubscriber.TopicSubscriber("Topic");
    }
}

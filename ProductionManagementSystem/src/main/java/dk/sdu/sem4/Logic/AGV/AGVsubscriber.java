package dk.sdu.sem4.Logic.AGV;

import dk.sdu.sem4.Domain.Hazelcast.TopicHandler;

public class AGVsubscriber implements IAGVsubscriber {

    private TopicHandler topicSubscriber;
    private String message;

    public String getMessage() {
        message = topicSubscriber.getMessage();
        return message;
    }

    /**
     *
     * @param action what will be sent
     */
    public void SendMessage(String action){
        topicSubscriber.PublishMessage("AGVPubTopic", action);
    }

    public AGVsubscriber() {
        topicSubscriber = new TopicHandler();
        topicSubscriber.TopicSubscriber("AGVSubTopic");
    }
}

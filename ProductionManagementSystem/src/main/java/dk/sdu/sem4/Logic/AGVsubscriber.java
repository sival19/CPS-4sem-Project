package dk.sdu.sem4.Logic;

import dk.sdu.sem4.Domain.TopicHandler;
import dk.sdu.sem4.Logic.ISubscriber;

public class AGVsubscriber implements ISubscriber {

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

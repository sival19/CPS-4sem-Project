package dk.sdu.sem4.Logic.Assembly;

import dk.sdu.sem4.Domain.TopicHandler;
import dk.sdu.sem4.Logic.ISubscriber;

public class AssemblySubscriber implements ISubscriber {

    private TopicHandler topicSubscriber;
    private String message;

    public String getMessage() {
        message = topicSubscriber.getMessage();
        return message;
    }

    public void SendMessage(String action){
        topicSubscriber.PublishMessage("AssemblyPubTopic", action);
    }

    public AssemblySubscriber() {
        topicSubscriber = new TopicHandler();
        topicSubscriber.TopicSubscriber("Assembly");
    }
}

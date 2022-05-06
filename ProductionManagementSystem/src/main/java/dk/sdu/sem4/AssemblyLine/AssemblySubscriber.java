package dk.sdu.sem4.AssemblyLine;

import dk.sdu.sem4.Hazelcast.TopicHandler;

public class AssemblySubscriber {

    private TopicHandler topicSubscriber;
    private String message;

    public String getMessage() {
        message = topicSubscriber.getMessage();
        return message;
    }

    public AssemblySubscriber() {
        topicSubscriber = new TopicHandler();
        topicSubscriber.TopicSubscriber("Assembly");
    }
}

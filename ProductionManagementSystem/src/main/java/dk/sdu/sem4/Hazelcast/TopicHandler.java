package dk.sdu.sem4.Hazelcast;

import com.hazelcast.topic.ITopic;


public class TopicHandler {

    String message;
    TopicListener tp = new TopicListener();

    public void TopicSubscriber(String TheTopic) {

        IHazelCastInstance instance = new HazelCastInstance();
        var hz = instance.HazelcastInstance();

        ITopic<String> topic = hz.getTopic(TheTopic);
        topic.addMessageListener(tp);
    }

    public String getMessage() {
            message = tp.getMsg();
            System.out.println(message);
            return message;


    }
}

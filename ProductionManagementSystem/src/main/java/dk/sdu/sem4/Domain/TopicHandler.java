package dk.sdu.sem4.Domain;

import com.hazelcast.topic.ITopic;
import dk.sdu.sem4.Domain.Hazelcast.HazelCastInstance;
import dk.sdu.sem4.Domain.Hazelcast.IHazelCastInstance;


public class TopicHandler {

    String message;
    TopicListener tp = new TopicListener();
    IHazelCastInstance instance = new HazelCastInstance();


    public void TopicSubscriber(String TheTopic) {

        var hz = instance.HazelcastInstance();
        ITopic<String> topic = hz.getTopic(TheTopic);
        topic.addMessageListener(tp);
    }



    public void PublishMessage(String TheTopic, String message){
        var hz = instance.HazelcastInstance();
        ITopic<String> topic = hz.getTopic(TheTopic);
        topic.publish(message);
    }

    public String getMessage() {
            message = tp.getMsg();

            return message;
    }
}

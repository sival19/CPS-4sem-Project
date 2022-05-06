package dk.sdu.sem4.Domain.Hazelcast;

import com.hazelcast.topic.ITopic;


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
            System.out.println(message);
            return message;


    }
}

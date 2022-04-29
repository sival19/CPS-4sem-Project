package dk.sdu.sem4;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.topic.ITopic;

import java.util.Scanner;


public class TopicSubscriber {

    String message;
    TopicListener tp = new TopicListener();

    public void TopicSubscriber() {


        IHazelCastInstance instance = new HazelCastInstance();
        var hz = instance.HazelcastInstance();

        ITopic<String> topic = hz.getTopic("Topic");
        topic.addMessageListener(tp);
//        System.out.println(hz.getDistributedObjects());
//        for (DistributedObject d : hz.getDistributedObjects()) {
//            ITopic<String> topic = hz.getTopic(d.getName());
//            System.out.println("Subscriber added to" + d.getName() + " Topic");
//            topic.addMessageListener(tp);
//        }
    }

    public void getMessage(String type) {
        if (type.equals("AGV")){
            message = tp.getMsg();
            System.out.println(message);
        }

    }
}

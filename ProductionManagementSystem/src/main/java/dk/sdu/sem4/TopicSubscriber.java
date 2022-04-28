package dk.sdu.sem4;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.topic.ITopic;


public class TopicSubscriber {


    public static void main(String[] args) {

        IHazelCastInstance instance = new HazelCastInstance();
        var hz = instance.HazelcastInstance();
        System.out.println(hz.getDistributedObjects());
        for (DistributedObject d : hz.getDistributedObjects()) {
            ITopic<String> topic = hz.getTopic(d.getName());
            System.out.println("Subscriber added to" + d.getName() + " Topic");
            topic.addMessageListener(new TopicListener());
        }
    }



}

package dk.sdu.sem4;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

public class TopicSubscriber {
    private static final String HAZELCAST_HOST = "127.0.0.1";
    private static final int HAZELCAST_PORT = 5701;

    public static void main(String[] args) {
        //HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        HazelcastInstance hz = getHazelcastInstance();
        ITopic<String> co2Topic = hz.getTopic("Test-Topic");
//        ITopic<String> temperatureTopic = hz.getTopic("TemperatureTopic");
//        System.out.println("Subscriber added to Temperature Topic");
        co2Topic.addMessageListener(new TopicListener());
//        temperatureTopic.addMessageListener(new TopicListener());
//        System.out.println("Subscriber added to CO2 Topic");
    }
    private static HazelcastInstance getHazelcastInstance(){
        //hazelcast address for the clients
        String hazelcastAddress = String.format("%s:%d", HAZELCAST_HOST, HAZELCAST_PORT);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(hazelcastAddress);
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}

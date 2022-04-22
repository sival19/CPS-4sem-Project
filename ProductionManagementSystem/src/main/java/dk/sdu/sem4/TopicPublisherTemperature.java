import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

public class TopicPublisherTemperature {
    private static final String HAZELCAST_HOST = "127.0.0.1";
    private static final int HAZELCAST_PORT = 5701;

    public static void main (String args[]){
        // get Hazelcast instance
        // HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        HazelcastInstance hz = getHazelcastInstance();
        // publish to topic, creates a new if it doesn't exist
        ITopic<String> topic = hz.getTopic("TemperatureTopic");
        topic.publish("Temperature data");
        System.out.println("Message published by topic publisher temperature");
    }
    private static HazelcastInstance getHazelcastInstance(){
        //hazelcast address for the clients
        String hazelcastAddress = String.format("%s:%d", HAZELCAST_HOST, HAZELCAST_PORT);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(hazelcastAddress);
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}

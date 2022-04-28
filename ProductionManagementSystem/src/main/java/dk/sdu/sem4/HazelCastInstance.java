package dk.sdu.sem4;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class HazelCastInstance implements IHazelCastInstance {
    private static final String HAZELCAST_HOST = "127.0.0.1";
    private static final int HAZELCAST_PORT = 5701;

    public HazelcastInstance HazelcastInstance() {
        //hazelcast address for the clients
        String hazelcastAddress = String.format("%s:%d", HAZELCAST_HOST, HAZELCAST_PORT);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(hazelcastAddress);
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}

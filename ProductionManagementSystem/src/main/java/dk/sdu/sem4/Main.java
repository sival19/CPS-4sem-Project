package dk.sdu.sem4;

import java.util.Collection;
import java.util.Date;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.multimap.MultiMap;

public class Main {

    private static final String HAZELCAST_HOST = "127.0.0.1";
    private static final int HAZELCAST_PORT = 5701;

    private static final String MAP_NAME = "my-service-registry";
    private static final String SERVICE = "CO2";
    private static final String IP = "127.0.0.1";

    public static void main(String[] args) {
        HazelcastInstance hz = getHazelcastInstance();

        //creates a new map here
        MultiMap<String,String> map = hz.getMultiMap(MAP_NAME);


        // read from topic

        //registering services
        //Service service1 = new Service(SERVICE, IP, 8001);
        //registerService(map, service1);
        
        //Service service2 = new Service(SERVICE, IP, 8002);
        //registerService(map, service2);

        //print out the service addresses from the map
        queryService(map, SERVICE).forEach(System.out::println);
        queryService(map, "NEW-CO2").forEach(System.out::println);

        hz.shutdown();
    }


    private static HazelcastInstance getHazelcastInstance(){
        //hazelcast address for the clients
        String hazelcastAddress = String.format("%s:%d", HAZELCAST_HOST, HAZELCAST_PORT);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(hazelcastAddress);
        return HazelcastClient.newHazelcastClient(clientConfig);
    } 

    //register a new service
    private static void registerService(MultiMap<String,String> map, Service service){
        String endpoint = String.format("%s:%d", service.ip, service.port);
        map.put(service.name, endpoint);
    }

    private static Collection<String> queryService(MultiMap<String,String> map, String service){
        Collection<String> servicesEndpoints = map.get(service);
        return servicesEndpoints;
    }

    /**
     * Service represents the type of service and its specific IP and port.
     */
    public record Service(String name, String ip, int port) {}
}
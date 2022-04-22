using System;
using System.Threading.Tasks;
using Hazelcast;

namespace AGV
{
    public class Program
    {
        // static async Task Main(string[] args)
        // {
        //     
        //     // Start the Hazelcast Client and connect to an already running Hazelcast Cluster on 127.0.0.1
        //     IHazelcastInstance hz = HazelcastClient.NewHazelcastClient();
        //     // Get the Distributed Map from Cluster.
        //     var topic = hz.GetTopic<string>("topic");
        //     topic.GetName();
        //     // var map = hz.GetMap<string,int>("theMap");
        //     // // Standard Put and Get.
        //     //  map.Put("key", 1);
        //     //  map.Get("key");
        //     //  //Concurrent Map methods, optimistic updating
        //     //  map.PutIfAbsent("somekey", 1);
        //     //  map.Replace("key", 1, 2);
        //      // Shutdown this Hazelcast Client
        //     hz.Shutdown();
        //     
        //     
        //     //REST
        //     // REST rest = new REST();
        //     // _ = rest.RunExample();
        //     //
        //     //
        //     // Console.ReadKey();
        // var options = HazelcastOptions.Build();
        //
        // options.Networking.Addresses.Add("127.0.0.1");
        //
        // options.ClusterName = "dev";
        //
        // options.ClientName = "MyClient";
        // }
    }
}

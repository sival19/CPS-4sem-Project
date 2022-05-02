using System;
using System.Threading;
using System.Threading.Tasks;
using Hazelcast;
using Hazelcast.DistributedObjects;
using RestSharp;

namespace AGV
{
    public class Program
    {
        private static string statusRequest = "v1/status/";
        
        
        
        
        private static void OnMessage(IHTopic<string> sender, TopicMessageEventArgs<string> args)
        {
            Console.WriteLine($"Got message " + args.Payload);
        }
        
        
        static async Task PublishTopic(string tp, string message)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
        }



        /// <summary>
        /// Loads an operation to the AGV and excecutes it
        /// </summary>
        /// <param name="name">Name of the operation to send</param>
        private static void putMethodRest(string name)
        {
            REST request = new REST();
            // string s = request.GetRequest(statusRequest).Result;
            
            request.PutOperation(name, 1, false);
            Thread.Sleep(1000);
            request.PutOperation(name, 2, true);
        }

        
        static async Task Main(string[] args)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>("AGVPubTopic");

            putMethodRest("MoveToChargerOperation");
            
            
            REST request = new REST();
            string s = request.GetRequest(statusRequest).Result;

            // request.PutOperation();
            // await topic.SubscribeAsync(on => on.Message(OnMessage));
            
            while (true)
            {
                await PublishTopic("AGVSubTopic", s);
                // await SubscribeTopic("AGVSubTopic");
            }
        }
    }
}
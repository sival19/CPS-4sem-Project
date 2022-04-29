using System;
using System.Threading;
using System.Threading.Tasks;
using Hazelcast;
using RestSharp;

namespace AGV
{
    public class Program
    {
        private static string statusRequest = "v1/status/";
    
        
        static async Task PublishTopic(string tp, string message)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
        }
        


        static async Task Main(string[] args)
        {
            REST request = new REST();
            string s = request.GetRequest(statusRequest).Result;
            
            // request.PutOperation();

            
            while (true)
            {
                await PublishTopic("Topic", s);
            }
        }
    }
}
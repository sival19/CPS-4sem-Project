using System;
using System.Threading;
using System.Threading.Tasks;
using Hazelcast;
using RestSharp;

namespace AGV
{
    public class Program
    {
    
        
        static async Task PublishTopic(string tp, string message)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
        }
        


        static async Task Main(string[] args)
        {
            REST request = new REST();
            string s = request.GetStatus("v1/status/").Result;

            
            while (true)
            {
                await PublishTopic("Topic", s);
            }
        }
    }
}
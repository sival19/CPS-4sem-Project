using System;
using System.Threading.Tasks;
using Hazelcast;
using Hazelcast.Core;
using Hazelcast.DistributedObjects;


namespace AGV
{
    public class HazelcastClientTemplate
    {
        // private HazelcastClient hz;
        // hz

        public static async Task SubscribePublishToTopic(String topicSubscribe, String message)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();

            await using var topic = await client.GetTopicAsync<String>(topicSubscribe);
            
            await topic.PublishAsync(message);

        }

        // public static async Task PublishMessage(String message)
        // {
        //     
        //     await topic.PublishAsync("Hello retards");
        //
        // }

        public static async Task Main(string[] args)
        {
            await SubscribePublishToTopic("Test-Topic", "Hello Retards");

        }
    }
}
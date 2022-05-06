using System;
using System.Threading;
using System.Threading.Tasks;
using Hazelcast;
using Hazelcast.DistributedObjects;
using Newtonsoft.Json;

namespace Assembly
{
    public class Program
    {
        static MQTT _request = new MQTT();


        public async Task PublishTopic(string tp, string message)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
        }

        // public static async Task ReceiveOnTopic(string tp)
        // {
        //     await using var client = await HazelcastClientFactory.StartNewClientAsync();
        //     await using var topic = await client.GetTopicAsync<String>(tp);
        //     await topic.SubscribeAsync(on => on.Message(OnMessage));
        //
        // }
        
        private static void OnMessage(IHTopic<string> sender, TopicMessageEventArgs<string> args)
        {
            // Thread.Sleep(500);
            Console.WriteLine($"Got message " + args.Payload);
            // var msg = new MQTTMessage();
            // msg.ProcessID = Convert.ToInt32(args.Payload);
            // _request.PublishOnTopic("emulator/operation", JsonConvert.SerializeObject(msg));
        }
        


        static async Task Main(string[] args)
        {
            MQTT request = new MQTT();
            await request.Connect();
            
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>("AssemblyPubTopic");
            await topic.SubscribeAsync(on => on.Message(OnMessage));

            while (true)
            {
                await topic.SubscribeAsync(on => on.Message(OnMessage));


                request.getMessage();
                // await ReceiveOnTopic("AssemblyPubTopic");

                // var msg = new MQTTMessage();
                // msg.ProcessID = 12345;
                // await request.PublishOnTopic("emulator/operation", JsonConvert.SerializeObject(msg));

                // Console.WriteLine(request.getMessage());
                // Console.WriteLine(request.Connect() + "Jeg er her");
                // Thread.Sleep(2000);

            }

            
            // Console.WriteLine(request.getMessage() + "Jeg er her");

            
            
            // var msg = new MQTTMessage();
            // msg.ProcessID = 12345;
            // await request.PublishOnTopic("emulator/operation", JsonConvert.SerializeObject(msg));
            //
            // String s = await request.PublishOnTopic("emulator/operation", JsonConvert.SerializeObject(msg));

            // request.PutOperation();

            //
            // while (true)
            // {
            //     await PublishTopic("Topic", s);
            // }
        }
    }
}
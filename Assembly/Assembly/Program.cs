using System;
using System.Diagnostics.CodeAnalysis;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using Hazelcast;
using Hazelcast.DistributedObjects;

namespace Assembly
{
    public class Program
    {
        static MQTT _request = new MQTT();
        private static Hazelcast _hazelcast = new Hazelcast();
        private static IHazelcastClient _client;


        public async Task PublishTopic(string tp, string message)
        {
            _client = _hazelcast.HazelcastInstance();
            await using var topic = await _client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
        }

        public static async Task ReceiveOnTopic(string tp)
        {
            _client = _hazelcast.HazelcastInstance();
            await using var topic = await _client.GetTopicAsync<String>(tp);
            await topic.SubscribeAsync(on => on.Message(OnMessage));
        }
        
        /// <summary>
        /// When receiving a message from java
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="args"></param>
        private static void OnMessage(IHTopic<string> sender, TopicMessageEventArgs<string> args)
        {
            // Thread.Sleep(500);
            Console.WriteLine($"Got message " + args.Payload);
            // Thread.Sleep(1000);
            // var msg = new MQTTMessage();
            // msg.ProcessID = Convert.ToInt32(args.Payload);
            // _request.PublishOnTopic("emulator/operation", JsonConvert.SerializeObject(msg));
        }
        


        public static async Task Main(string[] args)
        {
            await _request.Connect();
            
            await ReceiveOnTopic("AssemblyPubTopic");
            _request.getMessage();
            await Task.Delay(1_00);

            while (true)
            {
            }
        }
    }
}
using System;
using System.Threading;
using System.Threading.Tasks;
using Hazelcast;
using Newtonsoft.Json;

namespace Assembly
{
    internal class Program
    {
        private static string statusRequest = "v1/status/";
    
        
        public async Task PublishTopic(string tp, string message)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
        }
        


        static async Task Main(string[] args)
        {
            while (true)
            {
                MQTT request = new MQTT();
                await request.Connect();

                // await  request.RunExample();
                // request.getMessage();
                var msg = new MQTTMessage();
                msg.ProcessID = 9999;
                await request.PublishOnTopic("emulator/operation", JsonConvert.SerializeObject(msg));
                
                Console.WriteLine(request.getMessage());
                // Console.WriteLine(request.Connect() + "Jeg er her");
                Thread.Sleep(2000);
                
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
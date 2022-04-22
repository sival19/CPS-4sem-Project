using MQTTnet;
using MQTTnet.Client;
using MQTTnet.Client.Options;
using Newtonsoft.Json;
using System;
using System.Text;
using System.Threading.Tasks;

namespace Assembly
{
    public class MQTT
    {
        public MQTT()
        {
        }

        //MQTT vars
        MqttFactory factory;
        IMqttClient client;
        IMqttClientOptions options;

        private async Task Connect()
        {
            //init MQTT vars
            factory = new MqttFactory();
            client = factory.CreateMqttClient();
            options = new MqttClientOptionsBuilder()
                .WithClientId("")
                .WithTcpServer("localhost", 1883)
                .WithCleanSession(true)
                .Build();

            //the handlers of MQTTnet are very useful when working with an event-based communication
            //on established connection
            client.UseConnectedHandler(e =>
            {
                Console.WriteLine("Connected.");
                SubscribeToTopic("emulator/status");
                SubscribeToTopic("emulator/echo");
            });

            //on lost connection
            client.UseDisconnectedHandler(e =>
            {
                Console.WriteLine("Disconnected.");
            });

            //on receive message on subscribed topic
            client.UseApplicationMessageReceivedHandler(e =>
            {
                Console.WriteLine($"MQTT Subscribed message: {Encoding.UTF8.GetString(e.ApplicationMessage.Payload)} on topic: {e.ApplicationMessage.Topic}");
            });

            //connect
            await client.ConnectAsync(options);
        }


        public async void SubscribeToTopic(string input)
        {
            //printout
            Console.WriteLine("Subscribing to : " + input);

            //define topics
            var topic = new MqttTopicFilterBuilder()
                .WithTopic(input)
                .Build();

            //subscribe
            await client.SubscribeAsync(topic);
        }

        //publish method
        public async Task PublishOnTopic(string msg, string topic)
        {
            await client.PublishAsync(msg, topic);
        }

        //runner
        public async Task RunExample()
        {
            //connect and subscribe
            await Connect();

            //json serializable object
            var msg = new MQTTMessage();
            msg.ProcessID = 9999;
            
            //run publish
            await PublishOnTopic("emulator/operation", JsonConvert.SerializeObject(msg));
        }
    }

    //class to serialize json objects
    public class MQTTMessage
    {
        public int ProcessID { get; set; }
    }
}


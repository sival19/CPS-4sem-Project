using System;
using System.Diagnostics.CodeAnalysis;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using Hazelcast;
using Hazelcast.DistributedObjects;

namespace AGV
{
    public class Program
    {
        private static bool _isrunning;
        private static string _valueToMonitor = "";
        private static string _statusRequest = "v1/status/";
        private static REST _request = new REST();
        private static string _s;
        private static Regex rx = new Regex("\"state\":.");
        private static Hazelcast _hazelcast = new Hazelcast();
        private static IHazelcastClient _client;


        /// <summary>
        /// what to do with the message when received from hazecast
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="args"></param>
        private static void OnMessage(IHTopic<string> sender, TopicMessageEventArgs<string> args)
        {
            Console.WriteLine($"Got message " + args.Payload);
            if (args.Payload == "Start")
            {
                PublishTopic("AGVSubTopic", "{battery: 100, program name: Start, state: 1}");
            }
            else
            {
                PutMethodRest(args.Payload);

            }
        }


        private static async Task PublishTopic(string tp, string message)
        {
            _client = _hazelcast.HazelcastInstance();
            await using var topic = await _client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
        }

        private static async Task ReceiveOnTopic(string tp)
        {
            _client = _hazelcast.HazelcastInstance();
            await using var topic = await _client.GetTopicAsync<String>(tp);
            await topic.SubscribeAsync(on => on.Message(OnMessage));
        }


        /// <summary>
        /// Loads an operation to the AGV and excecutes it
        /// </summary>
        /// <param name="name">Name of the operation to send</param>
        private static void PutMethodRest(string name)
        {

            _request.PutOperation(name, 1, false);
            Thread.Sleep(1000);
            _request.PutOperation(name, 2, true);
        }


        [SuppressMessage("ReSharper.DPA", "DPA0001: Memory allocation issues")]
        public static async Task Main(string[] args)
        {
            _isrunning = true;
            await ReceiveOnTopic("AGVPubTopic");
            while (_isrunning)
            {
                _s = _request.GetRequest(_statusRequest).Result;
                Match match2 = rx.Match(_s);
                var changedValue = match2.Value;
                if (!_valueToMonitor.Equals(changedValue))
                {
                    _valueToMonitor = changedValue;
                    await PublishTopic("AGVSubTopic", _s);
                }

                
            }
            
            //capture CTRL+C and shut down the program
            Console.CancelKeyPress += delegate(object sender, ConsoleCancelEventArgs e)
            {
                e.Cancel = true;
                _isrunning = false;
            };

        }
    }
}
using System;
using System.ServiceModel.Channels;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Threading;
using Hazelcast;
using Hazelcast.DistributedObjects;
using WarehouseService;

namespace Warehouse
{
    internal class Program
    {
        private static bool _isrunning;
        private static string _s;
        private static Regex rx = new Regex("\"State\":.");
        private static string _valueToMonitor = "";
        private static SOAP soap = new SOAP();
        private static IHazelcastClient _client;
        private static Hazelcast _hazelcast = new Hazelcast();




        private static void OnMessage(IHTopic<string> sender, TopicMessageEventArgs<string> args)
        {
            Console.WriteLine($"Got message " + args.Payload);
            string operation = null;
            int trayId = 0;
            string name = null;

            switch (args.Payload.Split(",").Length)
            {
                case 1:
                    operation = args.Payload.Split(",")[0];
                    PutMethodSoap(operation);
                    break;
                case 2:
                    operation = args.Payload.Split(",")[0];
                    trayId = Convert.ToInt32(args.Payload.Split(",")[1]);
                    PutMethodSoap(operation, trayId);
                    break;
                case 3:
                    operation = args.Payload.Split(",")[0];
                    trayId = Convert.ToInt32(args.Payload.Split(",")[1]);
                    name = args.Payload.Split(",")[2];
                    PutMethodSoap(operation, trayId, name);
                    break;
                default:
                    Console.WriteLine("Error, input was: " + args.Payload);
                    break;
            }
        }

        private static async void PutMethodSoap(string operation)
        {
            if (operation.Equals("GetInventoryWarehouseOperation"))
                await soap.getInventory();
        }

        private static async void PutMethodSoap(string operation, int tray)
        {
            if (operation.Equals("PickItemWarehouseOperation"))
                await soap.pickItem(tray);
        }

        private static async void PutMethodSoap(string operation, int tray, string name)
        {
            if (operation.Equals("InsertItemWarehouseOperation"))
                await soap.insertItem(tray, name);
        }
        
        static async Task PublishTopic(string tp, string message)
        {
            _client = _hazelcast.HazelcastInstance();
            await using var publishTopic = await _client.GetTopicAsync<String>(tp);

            await publishTopic.PublishAsync(message);
        }

        private static async Task ReceiveOnTopic(string tp)
        {
            _client = _hazelcast.HazelcastInstance();
            await using var topic = await _client.GetTopicAsync<String>(tp);
            await topic.SubscribeAsync(on => on.Message(OnMessage));
        }
        
        static async Task Main(string[] args)
        {
            _isrunning = true;
            await ReceiveOnTopic("WarehouseFromJava");
            while (_isrunning)
            {
                _s = soap.getInventory().Result;
                Match match2 = rx.Match(_s);
                var changedValue = match2.Value;
                await Task.Delay(500);

                if (!_valueToMonitor.Equals(changedValue))
                {
                    Console.WriteLine(_s);
                    _valueToMonitor = changedValue;
                    await PublishTopic("WarehouseToJava", await soap.getInventory());
                    
                }
            }
        }
    }
}
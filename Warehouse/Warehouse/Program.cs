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

        // private static void OnMessage(IHTopic<string> sender, TopicMessageEventArgs<string> args)
        // {
        //     Console.WriteLine("Got message " + args.Payload);
        //     string payload = args.Payload;
        //     int firstArg = Convert.ToInt32(args.Payload.Split(" ")[1]);
        //     string secondArg = args.Payload.Split(" ")[2];
        //     Console.WriteLine(firstArg + secondArg);
        //     
        //     if (payload.Contains("PickItem"))
        //     {
        //         soap.pickItem(firstArg);
        //     }
        //     else if(payload.Contains("InsertItem"))
        //     {
        //         soap.insertItem(firstArg, secondArg);
        //     }
        //     else if (payload.Contains("GetInventory"))
        //     {
        //         soap.getInventory();
        //     }
        // }
        private static SOAP soap = new SOAP();
        private static string SOAPmessage;

        static async Task PublishTopic(string tp, string message)
        {
            await using var client1 = await HazelcastClientFactory.StartNewClientAsync();
            await using var publishTopic = await client1.GetTopicAsync<String>(tp);

            await publishTopic.PublishAsync(message);
        }

        static async Task startSOAP()
        {
            //instatiate web service from 'Connected Services' reference through Visual Studio tool
            var service = new EmulatorServiceClient();

            //print response of GetInventoryAsync()
            var response = await service.GetInventoryAsync();
            SOAPmessage = response;
            Console.WriteLine("SOAPmessage" + SOAPmessage);
        }

        static async Task Main(string[] args)
        {
            _isrunning = true;
            await using var client2 = await HazelcastClientFactory.StartNewClientAsync();
            await using var subscribeTopic = await client2.GetTopicAsync<String>("WarehouseFromJava");
            await subscribeTopic.SubscribeAsync(on => on.Message(OnMessage));
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
            
            //await soap.getInventory();

            // request.PutOperation();
        }
    }
}
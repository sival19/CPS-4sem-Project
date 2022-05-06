﻿using System;
using System.ServiceModel.Channels;
using System.Threading.Tasks;
using System.Threading;
using Hazelcast;
using Hazelcast.DistributedObjects;
using WarehouseService;

namespace Warehouse
{
    internal class Program
    {
        private static void OnMessage(IHTopic<string> sender, TopicMessageEventArgs<string> args)
        {
            Console.WriteLine("Got message " + args.Payload);
            
            int firstArg = Convert.ToInt32(args.Payload.Split(" ")[2]);
            if (args.Payload.Contains("PickItem"))
            {
                soap.pickItem(firstArg);
            }
            
        }
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
            await soap.getInventory();
            await using var client2 = await HazelcastClientFactory.StartNewClientAsync();
            await using var subscribeTopic = await client2.GetTopicAsync<String>("WarehouseFromJava");
            await subscribeTopic.SubscribeAsync(on => on.Message(OnMessage));
            // request.PutOperation();

            await PublishTopic("WarehouseToJava", await soap.getInventory());
        }
    }
}


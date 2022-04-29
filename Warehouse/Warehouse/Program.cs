using System;
using System.Threading.Tasks;
using System.Threading;
using Hazelcast;
using WarehouseService;

namespace Warehouse
{
    internal class Program
    {
        private static string SOAPmessage;
        static async Task PublishTopic(string tp, string message)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
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
            SOAP soap = new SOAP();
            await soap.getInventory();
          
            // request.PutOperation();

            
            while (true)
            {
                await PublishTopic("WarehousePubTopic", await soap.getInventory());
            }
        }
    }
}


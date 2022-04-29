using System;
using System.Threading.Tasks;
using System.Threading;
using Hazelcast;
using WarehouseService;

namespace Warehouse
{
    internal class Program
    {
        static async Task PublishTopic(string tp, string message)
        {
            await using var client = await HazelcastClientFactory.StartNewClientAsync();
            await using var topic = await client.GetTopicAsync<String>(tp);
            await topic.PublishAsync(message);
        }
         static async Task startSOAP()
                {
                    //SOAP
                    //SOAP soap = new SOAP();
                    //_ = soap.RunExample();

                    //Console.ReadKey();
                }
        


        static async Task Main(string[] args)
        {
            // make a SOAP request here
            string s = "HelloSOAP";
            
            // request.PutOperation();

            
            while (true)
            {
                await PublishTopic("WarehousePubTopic", s);
            }
        }
    }
}


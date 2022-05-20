using Hazelcast;

namespace AGV
{
    public class Hazelcast
    {
        private IHazelcastClient client;

        public IHazelcastClient HazelcastInstance()
        {
            if (client == null){}
            {
                // var options = HazelcastOptions.Build();
                //
                // options.Networking.Addresses.Add("192.168.80.3");
                client = HazelcastClientFactory.StartNewClientAsync().Result;
            }
            return client;
        }
    }
}
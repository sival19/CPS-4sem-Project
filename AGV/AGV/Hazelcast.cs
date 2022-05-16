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
                client = HazelcastClientFactory.StartNewClientAsync().Result;
            }
            return client;
        }
    }
}
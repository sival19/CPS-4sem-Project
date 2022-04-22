using System;

namespace Assembly
{
    internal class Program
    {
        static void Main(string[] args)
        {
            //MQTT
            MQTT mqtt = new MQTT();
            _ = mqtt.RunExample();

            Console.ReadKey();
        }
    }
}
using System;

namespace Warehouse
{
    internal class Program
    {
        static void Main(string[] args)
        {

            //SOAP
            SOAP soap = new SOAP();
            _ = soap.RunExample();

            Console.ReadKey();
        }
    }
}

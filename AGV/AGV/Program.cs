using System;

namespace AGV
{
    public class Program
    {
        static void Main(string[] args)
        {
            //REST
            REST rest = new REST();
            _ = rest.RunExample();
           

            Console.ReadKey();
        }
    }
}

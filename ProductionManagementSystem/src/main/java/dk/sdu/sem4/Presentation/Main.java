package dk.sdu.sem4.Presentation;


import dk.sdu.sem4.Logic.AGV.AGVsubscriber;


import dk.sdu.sem4.Logic.Assembly.AssemblySubscriber;


import dk.sdu.sem4.Logic.ISubscriber;

import dk.sdu.sem4.Logic.WH.WarehouseSubscriber;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        ISubscriber wh = new WarehouseSubscriber();

        ISubscriber agv = new AGVsubscriber();
        ISubscriber assembly = new AssemblySubscriber();

        boolean running = true;


        agv.getMessage();
        assembly.getMessage();

        //wh.getMessage();


        String helpString =
                "Welcome to The Awesome System\n" +
                "Press 1 to send AGV to Charger\n" +
                "Press 2 to send AGV to AssemblyStation\n" +
                "Press 3 to send AGV to Warehouse\n" +
                "Press 4 to send AGV to Activate the robot arm to pick payload fromAGV and place it at the assembly station\n" +
                "Press 5 to send AGV to Activate the robot arm to pick payload at the assembly station and place it on theAGV\n" +
                "Press 6 to send AGV to Activate the robot arm to pick payload from the warehouse outlet\n" +
                "Press 7 to send AGV to Activate the robot arm to place an item at the warehouse inlet\n" +

                "Press 8 to send operation to Assembly line\n" +
                "Press 9 to shut this down\n" +
                "Press 10 to shut this down\n" +

                "Write help to see the available commands";

        System.out.println(helpString);

        try (Scanner s = new Scanner(System.in)) {
            while (running) {
                switch (s.nextLine()) {
                    case "1":
                        wh.SendMessage("GetInventoryWarehouseOperation");
                        System.out.println(wh.getMessage());
                        break;
                    case "2":
                        wh.SendMessage("PickItemWarehouseOperation,5");
                        break;
                    case "3":

                        wh.SendMessage("InsertItemWarehouseOperation,5,This is the new item");

                        agv.SendMessage("MoveToStorageOperation");
                        break;
                    case "4":
                        agv.SendMessage("PutAssemblyOperation");
                        break;
                    case "5":
                        agv.SendMessage("PickAssemblyOperation");
                        break;
                    case "6":
                        agv.SendMessage("PickWarehouseOperation");
                        break;
                    case "7":
//                        agv.SendMessage("PutWarehouseOperation");
                        assembly.SendMessage("1234");
                        break;
                    case "8":

                        assembly.SendMessage("123456");
//                        System.out.println(assembly.getMessage());
//                        assembly.getMessage();

                        break;
                    case "9":

                      
                        System.out.printf("Sending message to Warehouse...\n");
                        wh.SendMessage("PickItem 2");

                        break;
                    case "10":
                        System.out.println("Dont leave me hanging :(");
                        running = false;
                        System.exit(0);
                        break;
                    case "help":
                        System.out.println(helpString);
                        break;
                }
            }
        }
    }
}

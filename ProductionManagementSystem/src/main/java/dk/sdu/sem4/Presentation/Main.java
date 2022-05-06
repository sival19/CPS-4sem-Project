package dk.sdu.sem4.Presentation;


import dk.sdu.sem4.Logic.AGV.AGVsubscriber;
import dk.sdu.sem4.Logic.AGV.IAGVsubscriber;
import dk.sdu.sem4.Logic.WH.IWHsubscriber;
import dk.sdu.sem4.Logic.WH.WarehouseSubscriber;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        IAGVsubscriber agv = new AGVsubscriber();
        IWHsubscriber wh = new WarehouseSubscriber();

        boolean running = true;

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
                "Press 8 to get Warehouse inventory\n" +
                "Press 9 to shut this down\n" +
                "Write help to see the available commands";

        System.out.println(helpString);

        try (Scanner s = new Scanner(System.in)) {
            while (running) {
                switch (s.nextLine()) {
                    case "1":
                        agv.SendMessage("MoveToChargerOperation");
                        break;
                    case "2":
                        agv.SendMessage("MoveToAssemblyOperation");
                        break;
                    case "3":
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
                        agv.SendMessage("PutWarehouseOperation");
                        break;
                    case "8":
                        System.out.println(wh.getMessage());
                        break;
                    case "9":
                        wh.SendMessage("InsertItem");
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

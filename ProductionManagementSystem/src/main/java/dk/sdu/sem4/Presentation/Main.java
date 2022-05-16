package dk.sdu.sem4.Presentation;


import dk.sdu.sem4.Logic.AGV.AGVsubscriber;
import dk.sdu.sem4.Logic.AGV.IAGVsubscriber;
import dk.sdu.sem4.Logic.WH.IWHsubscriber;
import dk.sdu.sem4.Logic.WH.WarehouseSubscriber;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        IWHsubscriber wh = new WarehouseSubscriber();

        IAGVsubscriber agv = new AGVsubscriber();

        boolean running = true;

        //wh.getMessage();

        String helpString =
                "Welcome to The Awesome System\n" +
                "Press 1 to get Warehouse inventory\n" +
                "Press 2 to pick item from Warehouse\n" +
                "Press 3 to insert item in Warehouse\n" +
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

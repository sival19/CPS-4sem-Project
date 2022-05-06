package dk.sdu.sem4;

import dk.sdu.sem4.Logic.WH.WarehouseSubscriber;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        WarehouseSubscriber wh = new WarehouseSubscriber();

        boolean running = true;
        System.out.println(
                "Welcome to The Aswesome System\n" +
                        "Press 1 for AGV status\n" +
                        "Press 2 for Warehouse status\n" +
                        "Press 3 to shut this down"
        );

        try (Scanner s = new Scanner(System.in)) {
            while (running) {
                switch (s.nextLine()) {
                    case "1":
                        System.out.println("Warehouse status is: ");

                        //get message from AGV
                        //agv

                        break;
                    case "2":
                        wh.getMessage();
                        break;
                    case "3":
                        System.out.println("Why would you quit ME?!");
                        running = false;
                        System.exit(0);
                        break;
                }
            }
        }
    }
}

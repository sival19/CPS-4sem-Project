package dk.sdu.sem4;

import dk.sdu.sem4.AGV.AGVsubscriber;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AGVsubscriber agv = new AGVsubscriber();

        boolean running = true;
        System.out.println(
                "Welcome to The Aswesome System\n" +
                        "Press 1 for AGV status\n" +
                        "Press 2 To send a command to the AGV\n" +
                        "Press 3 to shut this down"
        );

        try (Scanner s = new Scanner(System.in)) {
            while (running) {
                switch (s.nextLine()) {
                    case "1":
                        System.out.println("AGV status is: ");

                        //get message from AGV
                        agv.getMessage();

                        break;
                    case "2":
                        System.out.println("TODO");
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

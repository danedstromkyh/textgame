package com.company;

import java.util.Scanner;

public class Command {

    public void showCommands() {
        String validCommands[] = {"go", "inventory", "quit", "look", "help",};
        System.out.println("\nCommands you can use through the game.\nType help to show them again");
        System.out.println("--------------------------------------------");
        for (int i = 0; i < validCommands.length; i++) {
            System.out.print(validCommands[i] + "  ");
        }
        System.out.println("\n--------------------------------------------\n");

    }

    public void allCommands() {
        Game game = new Game();
        String command;
        Scanner in = new Scanner(System.in);
        boolean running = true;

        while(running)
        {
            System.out.print("> ");
            command = in.nextLine();
            command = command.toLowerCase();
            String[] commandParts = command.split(" ");
            if (commandParts.length < 2) {

                switch (command) {
                    case "go":
                        System.out.println("You need a direction. Ex north");
                        break;
                    case "help":
                        showCommands();
                        break;
                    case "look":
                        game.look();
                        break;
                    case "inventory":
                        game.showInventory();
                        break;
                    case "take":
                        System.out.println("hello");
                        break;
                    case "quit":
                        running = false;
                        break;
                    default:
                        System.out.println("Are you drunk? Type help for valid commands");
                }
            }
            //if commandparts is longer then one word
            if (commandParts[0].equals("go")) {
                if (commandParts.length >= 2) {

                    switch (commandParts[1]) {
                        case "north":
                            game.goNorth();
                            break;
                        case "south":
                            game.goSouth();
                            break;
                        case "east":
                            game.goEast();
                            break;
                        case "west":
                            game.goWest();
                            break;
                        default:
                            System.out.println("You can't go there. Use cardinal directions for moving. Ex go north");
                    }
                }
            }

        }
        System.out.println("Thank you for giving up. You didn't have what it took anyway");
    }



}


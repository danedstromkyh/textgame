package com.company;

public class CommandHelp {

    public void showCommands() {
        String validCommands[] = {"go", "take","use", "look", "inventory", "quit", "help",};
        System.out.println("\nCommands you can use through the game.\nType help to show them again");
        System.out.println("-------------------------------------------");
        for (int i = 0; i < validCommands.length; i++) {
            System.out.print(validCommands[i] + "  ");
        }
        System.out.println("\n-------------------------------------------\n");

    }


}


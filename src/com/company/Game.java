package com.company;


import javax.swing.*;
import java.util.ArrayList;
import java.util.*;

public class Game {
    Music music = new Music();
    CommandHelp help = new CommandHelp();
    MapPicture mapPicture = new MapPicture();
    public ArrayList<Room> map = new ArrayList<Room>();
    public ArrayList<Treasure> items = new ArrayList<Treasure>();
    public ArrayList<Treasure> playerList = new ArrayList<Treasure>();
    public ArrayList<Treasure> bedroomList = new ArrayList<Treasure>();
    public ArrayList<Treasure> kitchenList = new ArrayList<Treasure>();
    public ArrayList<Treasure> hallwayList = new ArrayList<Treasure>();
    public ArrayList<Treasure> livingRoomList = new ArrayList<Treasure>();
    public ArrayList<Treasure> terraceList = new ArrayList<Treasure>();
    boolean running = true;
    private static int noExit = -1;
    private Character hero;


    public Game() {
        initGame();
    }

    private void initGame() {
        //Arraylists for things
        bedroomList.add(new Treasure("Clothes", "Your trashy clothes"));
        bedroomList.add(new Treasure("Junk", "Test"));
        kitchenList.add(new Treasure("Key","Is this keys to the bedroom door?"));
        kitchenList.add(new Treasure("Map","Look like a blueprint over the place"));
        hallwayList.add(new Treasure("Wallet","Its a note in your wallet"));
        hallwayList.add(new Treasure("Crowbar","Can be used to open locked doors"));
        livingRoomList.add(new Treasure("Phone", "Its your phone!"));
        terraceList.add(new Treasure("Pepper spray", "Use it for protection"));


        //Create rooms, possible exits created. (N, S, E, W) Ex Master bedroom. Integer 2 is represented as south. Leading to Arraylist at index 2 which is Hallway
        map.add(new Room("Master bedroom", "A dark messy room with a huge bed", noExit, noExit, noExit, 1, bedroomList));
        map.add(new Room("Kitchen", "looks like its been a party here. Bottles all over", noExit, noExit, 0, noExit, kitchenList));
        map.add(new Room("Hallway", "Long hallway, smells like liquor", 0, noExit, noExit, 3, hallwayList));
        map.add(new Room("Living room", "A bunch of passed out people and loud music", noExit, noExit, 2, noExit, livingRoomList));
        map.add(new Room("Terrace", "The police is here, they are looking for you!", noExit, noExit, 3, noExit, terraceList));

        //Create player and a start room location
        hero = new Character("Chase Rabbit", "Drunk and disoriented",playerList, map.get(0));
    }

    public Character getHero() {
        return hero;
    }

    public String[] splitCommand(String command){
        command = command.toLowerCase();
        String commandParts[] = command.split(" ");
        return commandParts;

    }

    public void allCommands() {
        String command;
        Scanner in = new Scanner(System.in);


        while (running) {
            System.out.print("> ");
            command = in.nextLine();

            splitCommand(command);

                switch (splitCommand(command)[0]) {
                    case "go":
                        moveTo(hero, splitCommand(command));
                        break;
                    case "help":
                        help.showCommands();
                        break;
                    case "look":
                        look(getHero());
                        break;
                    case "take":
                        takeObject(splitCommand(command));
                        break;
                    case "use":
                        useObject(splitCommand(command));
                        break;
                    case "inventory":
                        showInventory();
                        break;
                    case "map":
                        mapPicture.picture();
                        break;
                    case "quit":
                        System.out.println("Thank you for giving up. You didn't have what it took anyway");
                        running = false;
                        break;
                    default:
                        System.out.println("Are you drunk? Type help for valid commands");
            }
        }
    }

    public int moveTo(Character hero, String[] command) {
        int exit;

        Room l = hero.getLocation();


        if (command.length < 2) {
            System.out.println("You need to use a direction. Ex go north");
            allCommands();
        }

        switch (command[1]) {

            case "north":
                exit = l.getNorth();
                goNorth(exit);
                break;
            case "south":
                exit = l.getSouth();
                goSouth(exit);
                break;
            case "east":
                exit = l.getEast();
                goEast(exit);
                break;
            case "west":
                exit = l.getWest();
                goWest(exit);
                break;
            default:
                exit = -1; //Stay in same room
                break;
        }
        return exit;
    }

    //Moves player by calling the moveTo method, and checks if any doors are locked
    public void goNorth(int exit) {
        moveCharacter(exit);

    }

    public void goSouth(int exit) {
        int bedroomDoor = map.get(0).getSouth();
        if(locked("Master bedroom",bedroomDoor)) {
            System.out.println("Locked door, use keys, maybe look in the kitchen");
        }
        else {
            moveCharacter(exit);
        }

    }

    public void goWest(int exit) {
        moveCharacter(exit);

    }

    public void goEast(int exit) {
        int livingRDoor = map.get(3).getEast();
        if(locked("Living room", livingRDoor)) {
            System.out.println("Broken door, you need to bend it up with something");
        }
        else {
            moveCharacter(exit);
        }

    }

    private void moveCharacter(int exit){
        if (exit != -1) {
            hero.setLocation(map.get(exit));
        }
            updateOutput(exit);
    }

    private void updateOutput(int exit) {
    // if roomNumber = noGo, display Cant go here, otherwise display name and description of room
    if  (exit == -1) {
        System.out.println("Can't go here, it's no door!");
    }
    else {
        hero.setLocation(map.get(exit));
        System.out.println("You are now in the " + hero.getLocation().getName() + ". " + hero.getLocation().getDescription());
        }
    }

    //Checks if doors are locked
    public boolean locked(String inRoom, int lockedDoor){
        Boolean locked = false;
        String s = hero.getLocation().getName();
        if(s.equals(inRoom) && (lockedDoor == noExit)) {
            locked = true;
        }
        return locked;
    }

    //Print out playerlist/inventory content
    public void showInventory() {
        if(playerList.size() == 0) {
            System.out.println("You have nothing you poor bastard");
        }

        for(Item play : playerList) {
            System.out.println(play.name + ", " + play.description);
        }
    }

    //Print out items in room
    public void look(Character hero) {
        System.out.println("Things in this room:\n"
                +"-------------------");
        for(Item l : hero.getLocation().getRoomList()) {
            System.out.println(l.name + ", " + l.description);
        }
    }

    public void takeObject(String[] command) {
        if (command.length < 2) {
            System.out.println("You need to specify an item to take. Try look command");
            allCommands();
        }

        boolean objectFound = false;
        ArrayList<Treasure> roomItems = hero.getLocation().getRoomList();
        for(Treasure list : roomItems) {
            Item l = list;

            if (l.name.equalsIgnoreCase(command[1])) {
                playerList.add(new Treasure(l.name, l.description));
                System.out.println(l.name + " taken!");
                roomItems.remove(l);
                objectFound = true;
                break;
            }
        }
        if (!objectFound) {
            System.out.println("Are you sure the item exists in this room?");
        }
    }

    public boolean canUse(String command, String object, String inRoom){
        String s = hero.getLocation().getName();
        boolean test = false;

        for(Treasure list : playerList) {
            if(command.equals(object) && list.name.equalsIgnoreCase(object)) {
                if(s == inRoom){
                    test = true;
                    break;
                }
            }
        }
        return test;
    }

    public void useObject(String[] command) {
        if (command.length < 2) {
            System.out.println("You need to use an item. Check your inventory");
            allCommands();
        }
        if(splitCommand(command[1]).equals("map")) {
            System.out.println("You found a blueprint over your location. Type map to see it");
        }

        boolean objectFound = false;
        if(canUse(command[1], "key", "Master bedroom")) {
            System.out.println("Congratulations, you can use a key, door is now open!");
            map.get(0).setSouth(2);
            updateOutput(2);
            objectFound = true;
        }
        if(canUse(command[1],"crowbar","Living room")) {
            System.out.println("You successfully opened the door with pure strength and a crowbar.\n");
            map.get(3).setEast(4);
            updateOutput(4);
            police();
            objectFound = true;
        }

        if(!objectFound) {
            System.out.println("Can't use this item here or maybe you don't even have it?");
        }
    }

    public void police(){
        music.playMusic("police.wav");
        Scanner police = new Scanner(System.in);
        String choose;
        System.out.println("You have two choices. Fight the coppers or run like the wind\n"+
                "Commands: Fight or run\n");
        System.out.print("> ");
        choose = police.nextLine();
        choose = choose.toLowerCase();

        if(choose.equals("fight")) {
            System.out.println("Game over! You never fight the police, rookie mistake. You go straight to jail. Thanks for playing though");
            music.delaySong("fought.wav");
            running = false;
        }
        if(choose.equals("run")) {
            music.playMusic("hooray.wav");
            JOptionPane.showMessageDialog(null,"Good choice! You are now free to roam the seven seas again. Thanks for playing!");
            running = false;
       }
        if (!choose.equals("run") && !choose.equalsIgnoreCase("fight")){
            police();
        }
    }

    public void intro(){
        System.out.println("\n _____                           _   _                                      _             \n" +
                "|  ___|                         | | | |                                    (_)            \n" +
                "| |__ ___  ___ __ _ _ __   ___  | |_| |__   ___   _ __ ___   __ _ _ __  ___ _  ___  _ __  \n" +
                "|  __/ __|/ __/ _` | '_ \\ / _ \\ | __| '_ \\ / _ \\ | '_ ` _ \\ / _` | '_ \\/ __| |/ _ \\| '_ \\ \n" +
                "| |__\\__ \\ (_| (_| | |_) |  __/ | |_| | | |  __/ | | | | | | (_| | | | \\__ \\ | (_) | | | |\n" +
                "\\____/___/\\___\\__,_| .__/ \\___|  \\__|_| |_|\\___| |_| |_| |_|\\__,_|_| |_|___/_|\\___/|_| |_|\n" +
                "                   | |                                                                    \n" +
                "                   |_|   \n\nYou wake up alone and confused in a bed.\n" +
                "You have a small recollection of a party in a mansion. Looks like you still are there.\n" +
                "But where are your stuff and what the hell happened yesterday?\n" +
                "You have a bad feeling about this. Something is wrong, find your stuff and get out!\n" +
                "\nType help to see valid commands\n");

    }
}
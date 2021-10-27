package com.company;

import java.util.ArrayList;
import java.util.*;

public class Game {
    Music music = new Music();
    MapPicture mapPicture = new MapPicture();
    ShowInput show = new ShowInput();
    public ArrayList<Room> map = new ArrayList<Room>();
    public ArrayList<Treasure> playerList = new ArrayList<Treasure>();
    public ArrayList<Treasure> bedroomList = new ArrayList<Treasure>();
    public ArrayList<Treasure> kitchenList = new ArrayList<Treasure>();
    public ArrayList<Treasure> hallwayList = new ArrayList<Treasure>();
    public ArrayList<Treasure> livingRoomList = new ArrayList<Treasure>();
    boolean alreadyExecutedK = false; //So command open fridge under method openThings only executes ones
    boolean alreadyExecutedL = false;
    private final int noExit = -1;
    private Character hero;
    boolean running;

    public Game() {
        initGame();
        running = true;
    }

    public void run() {
        while (running) {
            userCommands();
        }
    }

    private void initGame() {
        //Arraylists for things
        bedroomList.add(new Treasure("Clothes", "It's a good idea to get dressed before leaving"));
        kitchenList.add(new Treasure("Key","Is this keys to the bedroom door?"));
        kitchenList.add(new Treasure("Map","Looks like a blueprint over the place"));
        hallwayList.add(new Treasure("Crowbar","Can be used to open locked doors"));


        //Create rooms, possible exits created. (N, S, W, E) Ex Master bedroom. Integer 2 is represented as south. Leading to Arraylist at index 2 which is Hallway
        map.add(new Room("Master bedroom", "A dark messy room with a huge bed", noExit, noExit, noExit, 1, bedroomList));
        map.add(new Room("Kitchen", "Bottles all over. It's a blood trail leading from the fridge. Dare to open it?", noExit, noExit, 0, noExit, kitchenList));
        map.add(new Room("Hallway", "Long corridor", 0, noExit, noExit, 3, hallwayList));
        map.add(new Room("Living room", "The music is still pumping but where are the people?\nCan you hear the ringing in the closet?", noExit, noExit, 2, noExit, livingRoomList));
        map.add(new Room("Terrace", "The police is here, they are looking for you!", noExit, noExit, 3, noExit, null));

        //Create player and a start room location
        hero = new Character(ShowInput.text, playerList, map.get(0));
    }

    public Character getHero() {
        return hero;
    }

    //Splits upp command input into String[]
    public String[] splitCommand(String command){
        command = command.toLowerCase();
        String commandParts[] = command.split(" ");
        return commandParts;

    }

    //Handles user input
    public void userCommands() {
        String command;
        Scanner in = new Scanner(System.in);

        System.out.print("> ");
        command = in.nextLine();

        splitCommand(command);

        switch (splitCommand(command)[0]) {
            case "go":
                moveTo(hero, splitCommand(command));
                break;
            case "help":
                showCommands();
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
            case "open":
                openThings(splitCommand(command));
                break;
            case "inventory":
                showInventory();
                break;
            case "where":
                roomInfo();
                break;
            case "map":
                mapPicture.picture();
                break;
            case "quit":
                System.out.println("Thank you for giving up " + hero.getName() + ". You didn't have what it took anyway");
                running = false;
                break;
            default:
                System.out.println("Are you drunk? Type help for valid commands");
            }
        }

    public int moveTo(Character hero, String[] command) {
        int exit;
        Room l = hero.getLocation();

        if (command.length < 2) {
            System.out.println("You need to use a direction. Ex go north");
            run();
        }

        String direction = command[1];
        switch (command[1]) {
            case "north":
                exit = l.getNorth();
                goConditions(direction,exit);
                break;
            case "south":
                exit = l.getSouth();
                goConditions(direction,exit);
                break;
            case "east":
                exit = l.getEast();
                goConditions(direction,exit);
                break;
            case "west":
                exit = l.getWest();
                goConditions(direction,exit);
                break;
            default:
                exit = noExit; //Stay in same room
                break;
        }
        return exit;
    }

    //Checks if doors a blocked before moving player
    private void goConditions(String direction, int exit) {
        int livingRDoor = map.get(3).getEast();
        int bedroomDoor = map.get(0).getSouth();

        if(direction.equals("south")){
            if(locked("Master bedroom",bedroomDoor)) {
                System.out.println("Locked door, find some keys to use");
                    return;
            }
        }
        if(direction.equals("east")) {

            if(locked("Living room", livingRDoor)) {
                if(gotAllStuff()){
                    System.out.println("Broken door, you need to bend it up with something");

                }
                else{
                    System.out.println("You dont have all your stuff yet! Get back and look for them");
                }
                return;
            }
        }

        moveCharacter(exit);
    }

    //Updates output for character or shows if the door is locked
    private void moveCharacter(int exit){
        if  (exit == noExit) {
            System.out.println("Can't go here, it's no door!");
        }
        else{
            hero.setLocation(map.get(exit));
            System.out.println("You are now in the " + hero.getLocation().getName() + ". " + hero.getLocation().getDescription());
        }

    }

    //Locked door method
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

        for(Thing play : playerList) {
           System.out.println(play.name + ", " + play.description);
        }
    }

    //When user input is where this displays room name and description
    private void roomInfo(){
        System.out.println("You are now in the " + hero.getLocation().getName() + ". " + hero.getLocation().getDescription());
    }

    //Print out items in room
    private void look(Character hero) {
        System.out.println("Things in this room:\n"
                +"-------------------");
        for(Thing l : hero.getLocation().getRoomList()) {
            System.out.println(l.name + ", " + l.description);
        }
    }

    private void openThings(String[] command){

        if (command.length < 2) {
            System.out.println("You need to specify what to open. If you typ 'where' maybe you can get some clues.");
            run();
        }

        if(!alreadyExecutedK && command[1].equals("fridge")) {
            System.out.println("The blood was ketchup but your wallet is in here!");
            kitchenList.add(new Treasure("Wallet","Your wallet and still everything in it. Lucky!"));
            map.get(1).setDescription("Bottles all over. It's confirmed ketchup on the floor.");
            alreadyExecutedK = true;
            return;
        }
        if(!alreadyExecutedL && command[1].equals("closet")) {
            System.out.println("The ringing stopped but is that....Its your phone! Wohoo!");
            livingRoomList.add(new Treasure("Phone", "Its your phone! Needs charging though. But lets do that later"));
            map.get(3).setDescription("The music is still pumping but where are the people?");
            alreadyExecutedL = true;
            return;
        }
        else{
            System.out.println("No things to open here, or maybe you already opened it?");
        }

    }

    private void takeObject(String[] command) {
        boolean objectFound = false;
        ArrayList<Treasure> roomItems = hero.getLocation().getRoomList();

        if (command.length < 2) {
            System.out.println("You need to specify an item to take. Try 'look' command");
            run();
        }

        for(Treasure list : roomItems) {
            Thing l = list;

            if (l.name.equalsIgnoreCase(command[1])) {
                playerList.add(new Treasure(l.name, l.description));
                System.out.println(l.name + " taken!");
                roomItems.remove(l);
                objectFound = true;
                break;
            }
        }

        if(command[1].equals("map")) {
            System.out.println("You found a blueprint over your location. Type map to see it");

        }
        if (!objectFound) {
            System.out.println("Are you sure the item exists in this room?");
        }
    }

    private void useObject(String[] command) {
        if (command.length < 2) {
            System.out.println("You need to use an item. Check your inventory");
            run();
        }

        boolean objectFound = false;
        if(canUse(command[1], "key", "Master bedroom")) {
            System.out.println("Congratulations, you can use a key, door is now open!\n");
            playerList.removeIf(treasure -> treasure.getName().equals("Key"));
            map.get(0).setSouth(2);
            moveCharacter(2);
            objectFound = true;

        }
        if(canUse(command[1],"crowbar","Living room")) {
            System.out.println("You successfully opened the door with pure strength and a crowbar.\n");
            map.get(3).setEast(4);
            moveCharacter(4);
            police();
            objectFound = true;
        }

        if(!objectFound) {
            System.out.println("Can't use this item here or maybe you don't even have it?");
        }
    }

    //Checks if specific command, object and room are true for useObject
    public boolean canUse(String command, String object, String inRoom){
        String s = hero.getLocation().getName();
        boolean testUse = false;

        for(Treasure list : playerList) {
            if(command.equals(object) && list.name.equalsIgnoreCase(object)) {
                if(s == inRoom){
                    testUse = true;
                    break;
                }
            }
        }
        return testUse;
    }

    //Checks if you picked up all your things excluding the map
    private boolean gotAllStuff() {
        boolean allStuff = false;
        int allThings = 0;
        int allThingsMap =0;
        for(Treasure list : playerList){
            if(list.name.equals("Map")){}

            else{
                allThings++;
            }
            if(allThings == 4){
                allStuff = true;
            }
        }
        return allStuff;
    }

    //Lists command when user input is 'help'
    public void showCommands() {
        String validCommands[] = {"go", "take","use", "look","where","open", "inventory", "quit", "help",};
        System.out.println("\nCommands you can use through the game.\nType help to show them again");
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < validCommands.length; i++) {
            System.out.print(validCommands[i] + "  ");
        }
        System.out.println("\n--------------------------------------------------------\n");

    }

    //End game scenario at terrace
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
            show.gameEndingText();
            running = false;
       }
        if (!choose.equals("run") && !choose.equals("fight")){
            police();
        }
    }

    //Game intro
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
                "You have a bad feeling about this. Something is wrong, find all your stuff and get out!\n" +
                "\nType help to see valid commands\n");

    }
}
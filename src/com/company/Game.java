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
    private Character hero;
    private String use;

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
        map.add(new Room("Master bedroom", "A dark messy room with a huge bed", Direction.noGo, Direction.noGo, Direction.noGo, 1, bedroomList));
        map.add(new Room("Kitchen", "looks like its been a party here. Bottles all over", Direction.noGo, Direction.noGo, 0, Direction.noGo, kitchenList));
        map.add(new Room("Hallway", "Long hallway, smells like liquor", 0, Direction.noGo, Direction.noGo, 3, hallwayList));
        map.add(new Room("Living room", "A bunch of passed out people and loud music", Direction.noGo, Direction.noGo, 2, Direction.noGo, livingRoomList));
        map.add(new Room("Terrace", "The police is here, they are looking for you!", Direction.noGo, Direction.noGo, 3, Direction.noGo, terraceList));

        //Create player and a start room location
        hero = new Character("Chase Rabbit", "Drunk and disoriented",playerList, map.get(0));
    }

    public Character getHero() {
        return hero;
    }

    public void allCommands() {
        String command;
        Scanner in = new Scanner(System.in);


        while (running) {
            System.out.print("> ");
            command = in.nextLine();
            command = command.toLowerCase();
            String[] commandParts = command.split(" ");

            if (commandParts.length < 2)
            {
                switch (command) {
                    case "go":
                        System.out.println("You need a direction. Ex north");
                        break;
                    case "help":
                        help.showCommands();
                        break;
                    case "look":
                        look(getHero());
                        break;
                    case "take":
                        System.out.println("You need to specify an item to take. Try look command");
                        break;
                    case "use":
                        System.out.println("You need to use an item. Check your inventory");
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
            //if commandparts[0] is "go"
            if (commandParts[0].equals("go")) {
                if (commandParts.length >= 2) {

                    switch (commandParts[1]) {
                        case "north":
                            goNorth();
                            break;
                        case "south":
                            goSouth();
                            break;
                        case "east":
                            goEast();
                            break;
                        case "west":
                            goWest();
                            break;
                        default:
                            System.out.println("You can't go there. Use cardinal directions for moving. Ex go north");
                    }
                }
            }
            if (commandParts[0].equals("take") && commandParts.length >= 2 ) {
                    takeObject(commandParts[1]);
                    if(commandParts[1].equals("map")) {
                        System.out.println("You found a blueprint over your location. Type map to see it");
                    }
                }


            if (commandParts[0].equals("use") && commandParts.length >= 2 ) {
                    use = commandParts[1];
                    useObject();
            }
        }
    }


    public int moveTo(Character hero, Direction latitude) {

        int exit;
        Room l = hero.getLocation();


        switch (latitude) {

            case north:
                exit = l.getNorth();
                break;
            case south:
                exit = l.getSouth();
                break;
            case east:
                exit = l.getEast();
                break;
            case west:
                exit = l.getWest();
                break;
            default:
                exit = Direction.noGo; //Stay in same room
                break;
        }
        if (exit != Direction.noGo) {
            hero.setLocation(map.get(exit));
        }

        return exit;
    }

    //Moves player by calling the moveTo method, and checks if any doors are locked
    public void goNorth() {
        updateOutput(moveTo(hero,Direction.north));
    }

    public void goSouth() {
        int bedroomDoor = map.get(0).getSouth();
        if(locked("Master bedroom",bedroomDoor)) {
            System.out.println("Locked door, use keys, maybe look in the kitchen");
        }
        else {
            updateOutput(moveTo(hero, Direction.south));
        }
    }

    public void goWest() {
        updateOutput(moveTo(hero, Direction.west));
    }

    public void goEast() {
        int livingRDoor = map.get(3).getEast();
        if(locked("Living room", livingRDoor)) {
            System.out.println("Broken door, you need to bend it up with something");
        }
        else {
            updateOutput(moveTo(hero, Direction.east));
        }
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


    public void takeObject(String command) {
        boolean objectFound = false;
        ArrayList<Treasure> roomItems = hero.getLocation().getRoomList();
        for(Treasure list : roomItems) {
            Item l = list;

            if (l.name.equalsIgnoreCase(command)) {
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

    public boolean canUse(String object, String inRoom){
        String s = hero.getLocation().getName();
        boolean test = false;

        for(Treasure list : playerList) {
            if(use.equals(object) && list.name.equalsIgnoreCase(object)) {
                if(s == inRoom){
                    test = true;
                    break;
                }
            }
        }
        return test;
    }

    public void useObject() {
        boolean objectFound = false;
        if(canUse("key", "Master bedroom")) {
            System.out.println("Congratulations, you can use a key, door is now open!");
            map.get(0).setSouth(2);
            moveTo(hero, Direction.south);
            updateOutput(2);
            objectFound = true;
        }
        if(canUse("crowbar","Living room")) {
            System.out.println("You successfully opened the door with pure strength and a crowbar.\n");
            map.get(3).setEast(4);
            moveTo(hero, Direction.east);
            updateOutput(4);
            police();
            objectFound = true;
        }

        if(!objectFound) {
            System.out.println("Can't use this item here or maybe you don't even have it?");
        }
    }


    private void updateOutput(int roomNumber) {
        // if roomNumber = noGo, display Cant go here, otherwise display name and description of room
        if  (roomNumber == Direction.noGo) {
            System.out.println("Can't go here, it's no door!");
        }
        else {
            System.out.println("You are now in the " + hero.getLocation().getName() + ". " + hero.getLocation().getDescription());
        }
    }
    //Checks if doors are locked
    public boolean locked(String inRoom, int lockedDoor){
        Boolean locked = false;
        String s = hero.getLocation().getName();
        if(s.equals(inRoom) && (lockedDoor == Direction.noGo)) {
            locked = true;
        }
        return locked;
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
package com.company;


import java.util.ArrayList;
import java.util.*;

public class Game {
    CommandHelp help = new CommandHelp();
    public ArrayList<Room> map = new ArrayList<Room>();
    public ArrayList<Treasure> items = new ArrayList<Treasure>();
    public ArrayList<Treasure> playerList = new ArrayList<Treasure>();
    public ArrayList<Treasure> bedroomList = new ArrayList<Treasure>();
    public ArrayList<Treasure> kitchenList = new ArrayList<Treasure>();
    public ArrayList<Treasure> hallwayList = new ArrayList<Treasure>();
    public ArrayList<Treasure> livingRoomList = new ArrayList<Treasure>();
    public ArrayList<Treasure> terraceList = new ArrayList<Treasure>();
    private Character hero;
    private Character police;
    private String take;


    public Game() {
        //Arraylists for things
        bedroomList.add(new Treasure("Clothes", "Your trashy clothes"));
        bedroomList.add(new Treasure("Junk", "Test"));
        kitchenList.add(new Treasure("Key","Is this keys to the locked door?"));
        hallwayList.add(new Treasure("Wallet","Its a note in your wallet"));
        livingRoomList.add(new Treasure("Mobile phone", "Its your phone!"));
        terraceList.add(new Treasure("Pepper spray", "Use it for protection"));

        //Create rooms, possible exits created. (N, S, E, W) Ex Master bedroom. Integer 2 is represented as south. Leading to Arraylist at index 2 which is Hallway
        map.add(new Room("Master bedroom", "A dark messy room with a huge bed", Direction.noGo, -1, Direction.noGo, 1, bedroomList));
        map.add(new Room("Kitchen", "looks like its been a party here. Bottles all over", Direction.noGo, Direction.noGo, 0, Direction.noGo, kitchenList));
        map.add(new Room("Hallway", "Long hallway, smells like liquor", 0, Direction.noGo, Direction.noGo, 3, hallwayList));
        map.add(new Room("Living room", "A bunch of passed out people and loud music", Direction.noGo, Direction.noGo, 2, 4, livingRoomList));
        map.add(new Room("Terrace", "Big terrace with a pool", Direction.noGo, Direction.noGo, 3, Direction.noGo, terraceList));

        //Create player and a start room location
        hero = new Character("Chase Rabbit", "Drunk and disoriented",playerList, map.get(0));
        police = new Character("Justin Law", "Righteous Police",null, map.get(4));
    }

    public void allCommands() {
        String command;
        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("> ");
            command = in.nextLine();
            command = command.toLowerCase();
            String[] commandParts = command.split(" ");

            if (commandParts.length < 2 && (!"take".equals(commandParts[0])) && (!"use".equals(commandParts[0])))
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
                    case "inventory":
                        showInventory();
                        break;
                    case "quit":
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
            if (commandParts[0].equals("take")) {
                if(commandParts.length<2) {
                    System.out.println("You need to take something. Use look to see available items");}
                if (commandParts.length >= 2) {
                    take = commandParts[1];
                    takeObject();

                }
            }
            if(commandParts[0].equals("use")) {
                if(commandParts.length<2) {
                    System.out.println("You need to use and item. Check your inventory");}
                if (commandParts.length >= 2) {
                   //use = commandParts[1];
                    useObject();
                }
            }


        }
        System.out.println("Thank you for giving up. You didn't have what it took anyway");
    }

    public Character getHero() {
        return hero;
    }

    //Assigns current to variable Room l
    private int moveTo(Character hero, Direction latitude) {

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

        //Print out playerlist/inventory content
        public void showInventory() {
            if(playerList.size() == 0) {
                System.out.println("You have nothing you poor bastard");
            }

            for(Item play : playerList) {
                     System.out.println("You have " + play.name + ", " + play.description);
                }
        }

    //Print out items in room
    public void look(Character drunkenHero) {
        System.out.println("Things in this room:\n"
                          +"-------------------");
        for(Item loop : hero.getLocation().getRoomList()) {
            System.out.println(loop.name + ", " + loop.description);
        }
    }

    public void takeObject() {
        boolean object_is_found = false;

        for(Item loop : hero.getLocation().getRoomList()) {
            Item t = loop;

            if (loop.name.equalsIgnoreCase(take)) {
                playerList.add(new Treasure(t.name, t.description));
                System.out.println(t.name + " taken!");
                hero.getLocation().getRoomList().remove(loop);
                object_is_found = true;
                break;
            }
        }
            if (!object_is_found) {
                System.out.println("Are you sure the item exists in this room?");
            }
    }

    public void useObject() {

        for(Item list : playerList) {
            Item l = list;

            //Open bedroom door to hallway with key
            if (list.name.equalsIgnoreCase("key")) {
                if(hero.getLocation().getName().equals("Master bedroom")) {
                    System.out.println("Congratulations, you can use a key, door is now open!");
                    map.get(0).setSouth(2);
                }
                else {
                    System.out.println("Cant use the key in this room");
                }
            }
        }
    }

    private void updateOutput(int roomNumber) {
        // if roomNumber = noGo, display Cant go here, otherwise display name and description of room
        if  (roomNumber == Direction.noGo) {
            System.out.println("Can't go here, it's no door!");
        }
        else {
            System.out.println("You are in " + hero.getLocation().getName() + ". " + hero.getLocation().getDescription());
        }
    }

    //Moves player by calling the moveTo method
    public void goNorth() {
        updateOutput(moveTo(hero,Direction.north));
    }
    public void goSouth() {
            if (hero.getLocation().getName() == "Master bedroom") {
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
        updateOutput(moveTo(hero, Direction.east));
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
                "You have a bad feeling about this. Something is wrong, find your stuff and get out!\n");


        }



}

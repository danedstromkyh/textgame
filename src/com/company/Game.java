package com.company;

import com.sun.source.tree.Scope;

import java.util.*;


public class Game {


    public ArrayList<Room> map;
    public ArrayList<Treasure> items;
    private Character drunkenHero;






    public Game() {
       // this.items = new ArrayList<Item>();
        //items.add(new Item("Knife","for stabbing"));
        //Make arraylist for rooms
        this.map = new ArrayList<Room>();
        //Arraylists for things
        ThingList playerList = new ThingList();
        playerList.add(new Treasure("Knife","for stabbing"));
        //items.add(new Treasure("Knife","for stabbing"));
        ThingList bedroomList = new ThingList();
        bedroomList.add(new Treasure("Clothes", "Get dressed!"));
        bedroomList.add(new Treasure("Test", "Why is this here!"));
        //items.add(new Treasure("Clothes", "Get dressed!"));
        //items.add(new Treasure("Test", "Why is this here!"));
        ThingList kitchenList = new ThingList();
        kitchenList.add(new Treasure("Keys", "Its your keys! and another key beside it"));
        //items.add(new Treasure("Keys", "Its your keys! and another key beside it"));
        ThingList hallwayList = new ThingList();
        hallwayList.add(new Treasure("Wallet", "Its your wallet with a note in it!"));
        //items.add(new Treasure("Wallet", "Its your wallet with a note in it!"));
        ThingList livingRoomList = new ThingList();
        livingRoomList.add(new Treasure("Mobile phone", "Its your phone!"));
        //items.add(new Treasure("Mobile phone", "Its your phone!"));
        ThingList terraceList = new ThingList();
        terraceList.add(new Treasure("Pepper spray", "Use it for protection"));
        //items.add(new Treasure("Pepper spray", "Use it for protection"));




        //Create rooms, possible exits created. (N, S, E, W) Ex Master bedroom. Integer 2 is represented as south. Leading to Arraylist at index 2 which is Hallway
        map.add(new Room("Master bedroom", "A dark messy room with a huge bed", Direction.noGo, 2, Direction.noGo, 1, bedroomList));
        map.add(new Room("Kitchen", "looks like its been a party here. Bottles all over", Direction.noGo, Direction.noGo, 0, Direction.noGo, kitchenList));
        map.add(new Room("Hallway", "Long hallway, smells like liquor", 0, Direction.noGo, Direction.noGo, 3, hallwayList));
        map.add(new Room("Living room", "A bunch of passed out people and loud music", Direction.noGo, Direction.noGo, 2, 4, livingRoomList));
        map.add(new Room("Terrace", "Big terrace with a pool", Direction.noGo, Direction.noGo, 3, Direction.noGo, terraceList));


        //Create player and a start room location
        drunkenHero = new Character("Chase Rabbit", "Drunk and disoriented",playerList, map.get(0));
        //System.out.println(bedroomList.listItems());



    }
    public enum Direction {

        north,south,west,east;

        public static int noGo = -1;
    }

    public Character getDrunkenHero() {

        return drunkenHero;
    }

    //Assigns current to variable Room r
    private int moveTo(Character drunkenHero, Direction latitude) {

        int exit;
        Room l = drunkenHero.getLocation();

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
                moveCharacterTo(drunkenHero, map.get(exit));
            }

        return exit;
    }
    //Print out playerlist content
    public void showInventory() {
        System.out.println("You have " + getDrunkenHero().getItems().listItems());
        }

    //Print out items in room
    public void look() {

        String name = getDrunkenHero().getLocation().getItems().listItems();
        System.out.println(map.get(0).getName());
        System.out.println(items.get(0).getName());
        System.out.println();

    }



    //Change player location to new room
    private void moveCharacterTo(Character drunkenHero, Room inRoom) {
        drunkenHero.setLocation(inRoom);
    }



    private void transferOb(Item t, ThingList fromlist, ThingList tolist) {
            fromlist.remove(t);
            tolist.add(t);
        }

    /*private String takeOb(String obname) {
        String retStr ="";
        Item t = drunkenHero.getLocation().getItems().listItems()
        if (obname.equals("")) {  obname = "nameless object";// if no object specified
            }  if (t == null) {  retStr = "There is no " + obname + " here!";  }
        else {  transferOb(t, drunkenHero.getLocation().getItems(), drunkenHero.getItems());
            retStr = obname + " taken!";  }  return retStr;  }
*/




        private void updateOutput(int roomNumber) {
        // if roomNumber = noGo, display Cant go here, otherwise display name and description of room
        if (roomNumber == Direction.noGo) {
            System.out.println("Cant go here!");
        } else {
            System.out.println("You are in " + drunkenHero.getLocation().getName() + ". " + drunkenHero.getLocation().getDescription());
        }
    }

    //Moves player by calling the movePlayerTo Method
    public void goNorth() {
        updateOutput(moveTo(drunkenHero,Direction.north));
    }
    public void goSouth() {
        updateOutput(moveTo(drunkenHero, Direction.south));
    }
    public void goWest() {
        updateOutput(moveTo(drunkenHero, Direction.west));
    }
    public void goEast() {
        updateOutput(moveTo(drunkenHero, Direction.east));
    }

    public void intro(){
        System.out.println("\nYou wake up alone, confused in a bed, half naked.\n" +
                "You have a small recollection of a party in a mansion. Looks like you still are there.\n" +
                "But where are your stuff and what the hell happened yesterday?\n" +
                "You here sirens far away. You need to find your things and get out quick\n");


        }



}

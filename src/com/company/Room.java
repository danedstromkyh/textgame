package com.company;

import java.util.ArrayList;

public class Room extends Item {

    public int north, south, west, east;
    private ArrayList<Treasure>roomList;

    //Create room with name, description and possible exits that represents different cardinal directions
    public Room(String inName, String inDescription, int inNorth, int inSouth, int inWest, int inEast, ArrayList<Treasure>inRoomList) {
        super(inName, inDescription);
        roomList = inRoomList;
        this.north = inNorth;
        this.south = inSouth;
        this.west = inWest;
        this.east = inEast;
    }

    public ArrayList<Treasure> getRoomList() {

        return roomList;
    }


    public int getNorth() {
        return north;
    }

    public int getSouth() {
        return south;
    }

    public int getWest() {
        return west;
    }

    public int getEast() {
        return east;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public void setEast(int east) {
        this.east = east;
    }


}



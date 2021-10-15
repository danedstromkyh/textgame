package com.company;

public class Room extends Item {
    protected ThingList roomItems;

    private int north, south, west, east;

    //Create room with name, description and possible exits that represents different latitudes
    public Room(String inName, String inDescription, int inNorth, int inSouth, int inWest, int inEast, ThingList inRoomItems) {
        super(inName, inDescription);
        roomItems = inRoomItems;
        this.north = inNorth;
        this.south = inSouth;
        this.west = inWest;
        this.east = inEast;
    }

    public ThingList getItems() {
        return roomItems;
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

    public String describeItems() {
        return "\nThings in this room:\n" + "--------------------\n" + getItems().listItems();
    }
}



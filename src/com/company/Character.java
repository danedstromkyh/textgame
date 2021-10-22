package com.company;

import java.util.ArrayList;

public class Character {
    String name;
    String description;
    private Room location;
    private ArrayList<Treasure>playerList;


public Character(String inName, String inDescription, ArrayList<Treasure>inPlayerList, Room inRoom) {
    name = inName;
    description = inDescription;
    location = inRoom;
    playerList = inPlayerList;
}

    public String getName() {
        return name;
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room inLocation) {
        this.location = inLocation;
    }
}

package com.company;

public class Character {
    String name;
    String description;
    private Room location;
    ThingList items = new ThingList();


public Character(String inName,String inDescription, ThingList inItem, Room inRoom) {
    name = inName;
    description = inDescription;
    location = inRoom;
    items = inItem;

}

    public ThingList getItems() {
        return items;
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room inlocation) {
        this.location = inlocation;
    }
}

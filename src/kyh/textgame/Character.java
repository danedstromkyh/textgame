package kyh.textgame;

import java.util.ArrayList;

public class Character {
    String name;
    private Room location;
    private ArrayList<Treasure>playerList;


public Character(String inName, ArrayList<Treasure>inPlayerList, Room inRoom) {
    name = inName;
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

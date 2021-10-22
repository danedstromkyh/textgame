package com.company;
import java.util.ArrayList;

public class Item {

        protected String name;
        protected String description;


    public Item(String inName, String inDescription) {
        name = inName;
        description = inDescription;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

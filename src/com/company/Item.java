package com.company;
import java.util.ArrayList;

public class Item {

        protected String name;          // what object is it (e.g. name/title/something else.
        protected String description;   // description of object.




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

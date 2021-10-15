package com.company;

public class Item {

        protected String name;          // what object is it (e.g. name/title/something else.
        protected String description;   // description of object.
       // protected ThingList items; //Create a new list of things, implemented in Room and Character

    public Item(String inName, String inDescription) {
        name = inName;
        description = inDescription;
        //items = inItem;
    }

   // public ThingList getItems() {
   //     return items;
   // }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

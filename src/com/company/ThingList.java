package com.company;

import java.util.ArrayList;

public class ThingList extends ArrayList<Item> {
        public String listItems() {
                String itemDescription = "";
                if (this.size() == 0) {
                        itemDescription = "nothing here you unlucky bastard\n";
                } else {

                        for (int i=0;i<this.size();i++) {
                                itemDescription = itemDescription + this.get(i).getName() + ", " + this.get(i).getDescription() +"\n";

                        }
                }
                return itemDescription;
        }



        }

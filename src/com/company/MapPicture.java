package com.company;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class MapPicture {

    public static void picture() {
        File f = new File("./blueprint.jpg");
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
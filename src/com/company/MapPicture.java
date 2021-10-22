package com.company;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapPicture {

    public void picture(){
        try {
            Image picture = ImageIO.read(new File("blueprint.jpg"));

            Process photo = new ProcessBuilder("mspaint","blueprint.jpg").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

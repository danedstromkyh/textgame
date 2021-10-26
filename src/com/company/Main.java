package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {


    public static void main(String[] args) throws Exception {

        Music music = new Music();
        Game game = new Game();
        music.playMusic("welcome.wav");
        JOptionPane.showMessageDialog(null,"Welcome to Escape the mansion! Press OK to start");
        game.intro();
        game.run();

    }

}



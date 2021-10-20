package com.company;

import javax.swing.*;

public class Main {



    public static void main(String[] args) {
        Music music = new Music();
        Game game = new Game();
        music.playMusic("welcome.wav");
        JOptionPane.showMessageDialog(null,"Welcome to Escape the mansion! Press OK to start");
        game.intro();
        game.allCommands();

    }

}



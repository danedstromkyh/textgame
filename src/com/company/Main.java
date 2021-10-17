package com.company;

import javax.swing.*;

public class Main {



    public static void main(String[] args) {
        CommandHelp commands = new CommandHelp();
        Game game = new Game();

        Music music = new Music();
        //music.playMusic("");
        JOptionPane.showMessageDialog(null,"Welcome to Escape the mansion! Press OK to start");
        game.intro();
        game.allCommands();


    }

}



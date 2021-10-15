package com.company;

import javax.swing.*;

public class Main {




    public static void main(String[] args) {

        Music music = new Music();
        music.playMusic("");
        JOptionPane.showMessageDialog(null,"Welcome player, press OK to start adventure");

        Command commands = new Command();
        Game game = new Game();

        game.intro();
        commands.allCommands();


    }

}



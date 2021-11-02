package com.company;

import java.util.Random;
import java.util.Scanner;

public class RockPaper {

    public  RockPaper() {
        init();
    }

    public static void init() {

        int heroCount = 0;
        int drunkCount = 0;
        gameStart(heroCount, drunkCount);
    }

    public static void gameStart(int heroCount, int drunkCount) {
        Game game = new Game();
        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        do {
            String throwStr = "";
            System.out.print("\nMake your choice:\n(0=Rock, 1=Paper, 2=Scissors): ");

            if (!input.hasNextInt()) {
                System.out.println("You can read right? 0, 1 and 2 are valid inputs. Nothing else.");
                gameStart(heroCount, drunkCount);
            }

            int heroThrow = input.nextInt();
            if (heroThrow == 0) {
                throwStr = "ROCK";
            } else if (heroThrow == 1) {
                throwStr = "PAPER";
            } else if (heroThrow == 2) {
                throwStr = "SCISSORS";
            } else {
                System.out.println("\nYou can read right? 0, 1 and 2 are valid inputs. Nothing else.");
                gameStart(heroCount, drunkCount);
            }

            System.out.println("Throws " + throwStr);
            int drunkThrow = rand.nextInt(3);
            if (drunkThrow == 0) {
                throwStr = "ROCK";
            } else if (drunkThrow == 1) {
                throwStr = "PAPER";
            } else if (drunkThrow == 2) {
                throwStr = "SCISSORS";
            }
            System.out.println("The drunk guy throws " + throwStr);
            if ((heroThrow == 0 && drunkThrow == 2) || (heroThrow == 1 && drunkThrow == 0) || (heroThrow == 2 && drunkThrow == 1)) {
                System.out.println("You win!\n");
                heroCount++;
            } else if (heroThrow == drunkThrow) {
                System.out.println("Tie!");
            } else if ((heroThrow == 2 && drunkThrow == 0) || (heroThrow == 0 && drunkThrow == 1) || (heroThrow == 1 && drunkThrow == 2)) {
                System.out.println("The drunk guy wins!\n");
                drunkCount++;
            }
            System.out.print(game.hero.getName() + ": " + heroCount + "\n");
            System.out.print("Drunk guy: " + drunkCount + "\n");

        } while (heroCount < 2 && drunkCount < 2);

        if (heroCount > drunkCount) {
            System.out.print("\nYou are the winner!\nDrunk guy runs away and you are free to continue your escape.\n");
        }
        else if(drunkCount > heroCount){
            System.out.print("The drunk guy is the winner!\nHe insists to play you again. Seems like you have to.\n");
            init();
        }
    }
}


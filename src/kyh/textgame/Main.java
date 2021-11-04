package kyh.textgame;

public class Main {



    public static void main(String[] args) {
        ShowInput show = new ShowInput();
        Music music = new Music();
        music.playMusic("welcome.wav");
        show.welcomeText();
        Game game = new Game();
        game.intro();
        game.run();
    }
}



package kyh.textgame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music {
    void playMusic(String musicLocation){
        try
        {
            File musicPath = new File(musicLocation);
            if(musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else
            {
                System.out.println("Cant find");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void delaySong(String inSong) {
        int timeToWait = 19; //seconds
        playMusic(inSong);
        try {
            for (int i=0; i<timeToWait ; i++) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException ie)
        {
            Thread.currentThread().interrupt();
        }
    }
}

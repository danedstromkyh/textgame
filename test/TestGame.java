import com.company.*;
import com.company.Character;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGame {
    Game game = new Game();
    Character player = new Character("Player1", game.playerList, game.map.get(2));
    Room room = new Room("Room1", "Its a nice room", -1, 2, -1, -1, game.hallwayList);

    @Test
    //get the player name of test character
    public void getPlayerName() {
        String result = player.getName();
        assertEquals("Player1", result);
    }

    @Test
    //get the description from test room
    public void getRoomDescription() {
        String result = room.getDescription();
        assertEquals("Its a nice room", result);
    }

    @Test
    //Get the arraylist "south" from test room
    public void getSouthArrayList() {
        int result = room.getSouth();
        assertEquals(2, result);
    }

    @Test
    //Tests the split words method
    public void splitWords() {
        String command = "testing split method";
        String[] result = game.splitCommand(command);
        assertEquals("split",result[1]);

    }

    @Test
    //Getting current position from test player. game.map.get(2) = Hallway
    public void getLocation() {
        String result = player.getLocation().getName();
        assertEquals("Hallway", result);

    }

}




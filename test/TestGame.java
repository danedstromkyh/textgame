import com.company.*;
import com.company.Character;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGame {
    Game game = new Game();
    Character player = new Character("Test", "test2", game.bedroomList, game.map.get(2));
    Room room = new Room("Room1", "Its a nice room", -1, -1, -1, -1, null);

    @Test
    public void movePlayer() {
        int result = game.moveTo(player, Direction.east);
        assertEquals(3, result);
    }

    @Test
    public void getPlayerName() {
        String result = player.getName();
        assertEquals("Test", result);
    }
}



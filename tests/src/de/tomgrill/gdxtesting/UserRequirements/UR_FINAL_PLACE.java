package de.tomgrill.gdxtesting.UserRequirements;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.dragonboat.game.*;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(GdxTestRunner.class)
/**
 * Tests that leaderboard returns the boats in the correct order.
 */
public class UR_FINAL_PLACE {
    /*
     * Mocking classes needed for the test
     */
    Player player = mock(Player.class);
    Opponent boat2 = mock(Opponent.class);
    Opponent boat3 = mock(Opponent.class);
    Opponent[] opponents = new Opponent[2];


    @Test
    public void FinalPlaceAwardsTest() {
        opponents[0] = boat2;
        opponents[1] = boat3;
        Leaderboard leaderboard = new Leaderboard(player,opponents);

        when(player.getFastestTime()).thenReturn(5F);
        when(boat2.getFastestTime()).thenReturn(10F);
        when(boat3.getFastestTime()).thenReturn(15F);

        when(player.getName()).thenReturn("Player");
        when(boat2.getName()).thenReturn("Bot1");
        when(boat3.getName()).thenReturn("Bot2");

        /*Expected pass test cases
        Where index 0, 1, 2 = 1st place, 2nd place, 3rd place respectively*/
        leaderboard.UpdateOrder();
        Boat[] results1 = leaderboard.getPodium();

        assertTrue(results1[0].getName().startsWith("Player"));
        assertTrue(results1[1].getName().startsWith("Bot1"));
        assertTrue(results1[2].getName().startsWith("Bot2"));
    }

}

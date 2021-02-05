package de.tomgrill.gdxtesting.UserRequirements;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.dragonboat.game.Boat;
import com.dragonboat.game.Lane;
import com.dragonboat.game.Leaderboard;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(GdxTestRunner.class)
public class UR_FINAL_PLACE {

    /*
     * Mocking leaderboard with a set of testcase boat finishes, to test
     * Podium function indexes to see what medal would be given
     */
    Leaderboard leaderboard = mock(Leaderboard.class);

    /*
     * Creating test cases
     */
    Lane lane = new Lane(10, 20);
    Boat boat1 = new Boat(20, 10, 5, lane, "Player" );
    Boat boat2 = new Boat(15, 5, 10, lane, "Bot1" );

    Boat boat3 = new Boat(25, 4, 10, lane, "Bot2" );

/*
    Expected pass test cases
*/
    Boat[] testcase1 = {boat1, boat2, boat3};
    Boat[] testcase2 = {boat2, boat1, boat3};
    Boat[] testcase3 = {boat2, boat3, boat1};

    @Test
    public void FinalPlaceAwardsTest() {


        /*Expected pass test cases
        Where index 0, 1, 2 = 1st place, 2nd place, 3rd place respectively*/
        when(leaderboard.getPodium()).thenReturn(testcase1);
        Boat[] results1 = leaderboard.getPodium();

        assertTrue(results1[0].getName().startsWith("Player"));

        when(leaderboard.getPodium()).thenReturn(testcase2);
        Boat[] results2 = leaderboard.getPodium();
        assertTrue(results2[1].getName().startsWith("Player"));

        when(leaderboard.getPodium()).thenReturn(testcase3);
        Boat[] results3 = leaderboard.getPodium();
        assertTrue(results3[2].getName().startsWith("Player"));

        /*Expected fail cases*/
        assertFalse(results3[1].getName().startsWith("Player"));




    }

}

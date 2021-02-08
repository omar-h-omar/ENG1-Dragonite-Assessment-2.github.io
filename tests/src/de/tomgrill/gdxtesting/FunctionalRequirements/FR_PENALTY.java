package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.dragonboat.game.*;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(GdxTestRunner.class)
public class FR_PENALTY {


    /**
     * creating boat instance and test case array
     * */
    Lane lane = new Lane(10, 20);
    float[] testCases = {3, 10, 4, 6};
    Boat boat = new Boat(20, 10, 5, lane, "Player");

    @Test
    public void testPenalty() {


        /**
         * Pass test cases
         * */

        boat.applyPenalty(testCases[0]);
        Assert.assertEquals(testCases[0], boat.getPenalty(), 0);

        boat.applyPenalty(7);
        Assert.assertEquals(testCases[1], boat.getPenalty(), 0);

        /**
         * Fail test cases
         * */
        boat.setPenalties(0);

        boat.applyPenalty(5);
        Assert.assertNotEquals(testCases[2], boat.getPenalty());

        boat.applyPenalty(2);
        Assert.assertNotEquals(testCases[3], boat.getPenalty());


    }
}

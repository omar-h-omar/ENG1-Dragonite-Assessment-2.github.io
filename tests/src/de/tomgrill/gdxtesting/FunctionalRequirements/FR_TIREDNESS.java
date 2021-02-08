package de.tomgrill.gdxtesting.FunctionalRequirements;


import com.dragonboat.game.Boat;
import com.dragonboat.game.DragonBoatGame;
import com.dragonboat.game.Lane;
import com.dragonboat.game.Player;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;



@RunWith(GdxTestRunner.class)
public class FR_TIREDNESS {



    @Test
    public void TestTiredness() {

        /**
         * Creating boat object to test tiredness
         */

        Lane lane1 = new Lane(5, 10);
        Boat boat1 = new Boat(5, 3, 7, lane1, "Player");

        /**
         * Test to see tiredness starting value
         */

        assertTrue(boat1.getTiredness() == 0);

        /**
         * Test to see if tiredness is increased
         */
        boat1.setTiredness(5);
        boat1.IncreaseTiredness();
        assertTrue(boat1.getTiredness() == 5.75);

        /**
         * Test to see if tiredness can be decreased
         */

        boat1.setTiredness(1);
        boat1.DecreaseTiredness();
        assertTrue(boat1.getTiredness() == 0);


    }

}

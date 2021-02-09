package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.dragonboat.game.Boat;
import com.dragonboat.game.Lane;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;
import java.util.Arrays;

@RunWith(GdxTestRunner.class)
public class FR_UNIQUE_BOATS {


    @Test
    public void TestUniqueBoats() {


        /**
         * Creating Lane instance and all boats to test
         * */

        Lane lane = new Lane(10, 15 );

        Boat boat1 = new Boat(3, 3, 7, lane, "boat1");
        Boat boat2 = new Boat(9, 3, 7, lane, "boat2");
        Boat boat3 = new Boat(12, 3, 7, lane, "boat3");
        Boat boat4 = new Boat(15, 3, 7, lane, "boat4");
        Boat boat5 = new Boat(20, 3, 7, lane, "boat5");
        Boat boat6 = new Boat(23, 3, 7, lane, "boat6");
        Boat boat7 = new Boat(27, 3, 7, lane, "boat7");


        /**
         * Setting stats to boats
         * */
        boat1.setStats('A');
        boat2.setStats('B');
        boat3.setStats('C');
        boat4.setStats('D');
        boat5.setStats('E');
        boat6.setStats('F');
        boat7.setStats('G');



        HashMap<Float[], Integer> nonequalStats = new HashMap<>();
        /**
         * Compare boats stats with each other
         * HashMap duplicate keys are not allowed
         * */


        nonequalStats.put(boat1.getStats(), 1);
        nonequalStats.put(boat2.getStats(), 1);
        nonequalStats.put(boat3.getStats(), 1);
        nonequalStats.put(boat4.getStats(), 1);
        nonequalStats.put(boat5.getStats(), 1);
        nonequalStats.put(boat6.getStats(), 1);
        nonequalStats.put(boat7.getStats(), 1);


        Assert.assertTrue( nonequalStats.size() == 7);

    }


}

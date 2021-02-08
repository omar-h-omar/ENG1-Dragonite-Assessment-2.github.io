package de.tomgrill.gdxtesting.UserRequirements;


import com.dragonboat.game.DragonBoatGame;
import com.dragonboat.game.Lane;
import com.dragonboat.game.Opponent;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class UR_MIN_BOATS {

    @Test
    public void MinimumBoatOpponents() {



        /*Creating test cases*/
        Lane lane1 = new Lane(1, 5);
        Lane lane2 = new Lane(5, 10);
        Lane lane3 = new Lane(10, 15);
        Lane lane4 = new Lane(15, 20);


        Opponent boat1 = new Opponent(2, 3, 7, lane1, "Boat1" );
        Opponent boat2 = new Opponent(6, 3, 7, lane2, "Boat2" );
        Opponent boat3 = new Opponent(10, 3, 7, lane3, "Boat3" );
        Opponent boat4 = new Opponent(14, 3, 7, lane4, "Boat4" );

        /*Storing testcases*/
        Opponent[] testcase = {boat1, boat2, boat3, boat4};
        Lane[] lanes = {lane1, lane2, lane3, lane4};

        /*Creating a new game and initializing the opponents and lanes*/
        DragonBoatGame dragonBoatGame = new DragonBoatGame();
        dragonBoatGame.opponents = testcase;
        dragonBoatGame.lanes = lanes;

        /*Checking to see that Opponents and lanes are stored, not null and are of expected length*/
        Assert.assertNotNull(dragonBoatGame.opponents);
        Assert.assertEquals(4, dragonBoatGame.opponents.length);
        Assert.assertArrayEquals(dragonBoatGame.lanes, lanes);
    }

}

package de.tomgrill.gdxtesting.UnitTests;
import com.dragonboat.game.Boat;
import com.dragonboat.game.Lane;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class BoatTests {

    Lane x = new Lane(5, 10);
    Boat testBoat = new Boat(50, 20, 30, x , "okay");

    @Test
    public void TestSteerLeft() {

        //Need to set values for ACCELERATION and MANEUVERABILITY for tests to work
        testBoat.setACCELERATION(5);
        testBoat.setMANEUVERABILITY(3);
        testBoat.setCurrentSpeed(1);
        testBoat.setXPosition(5);

        //Values to be tested and boatsteering test
        float xBefore = testBoat.getX();
        testBoat.SteerLeft();
        float xAfter = testBoat.getX();
        boolean steerLeftResults = xBefore > xAfter;
        assertTrue(steerLeftResults);
    }

    @Test
    public void TestSteerRight() {

        //Need to add setter for ACCELERATION, MANEUVERABILITY for tests to work
        testBoat.setACCELERATION(5);
        testBoat.setMANEUVERABILITY(3);
        testBoat.setCurrentSpeed(1);

        //Values to be tested and boatsteering test
        float xBefore = testBoat.getX();
        testBoat.SteerRightTest();
        float xAfter = testBoat.getX();
        boolean steerLeftResults = xBefore < xAfter;
        assertTrue(steerLeftResults);
    }

    @Test
    public void TestIncreaseTiredness() {}

    @Test
    public void TestDecreaseTiredness() {}

    @Test
    public void TestIncreaseSpeed() {}

    @Test
    public void TestDecreaseSpeed() {}

    public static void main(String[] args) {}
}

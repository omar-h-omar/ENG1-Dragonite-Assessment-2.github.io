package de.tomgrill.gdxtesting.FunctionalRequirements;
import com.dragonboat.game.Boat;
import com.dragonboat.game.Course;
import com.dragonboat.game.Lane;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class BoatTests {

    Lane x = new Lane(5, 10);
    Boat testBoat = new Boat(50, 20, 30, x , "okay");
    Course course = mock(Course.class);

    @Test
    public void TestSteerLeft() {
        when(course.getLeftBoundary()).thenReturn(0);
        when(course.getRightBoundary()).thenReturn(1080);

        //Need to set values for ACCELERATION and MANEUVERABILITY for tests to work
        testBoat.setACCELERATION(5);
        testBoat.setMANEUVERABILITY(3);
        testBoat.setCurrentSpeed(1);
        testBoat.setXPosition(5);

        //Values to be tested and boatsteering test
        float xBefore = testBoat.getX();
        testBoat.SteerLeft(course);
        float xAfter = testBoat.getX();
        boolean steerLeftResults = xBefore > xAfter;
        assertTrue(steerLeftResults);
    }

    @Test
    public void TestSteerRight() {
        when(course.getLeftBoundary()).thenReturn(0);
        when(course.getRightBoundary()).thenReturn(1080);

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

package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.dragonboat.game.Boat;
import com.dragonboat.game.Course;
import com.dragonboat.game.Lane;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FR_COURSE_BOUNDARIES {
    Course course = mock(Course.class);
    Boat boat = new Boat(0,0,0,mock(Lane.class),"Boat");

    /**
     * Tests that boats can't leave the course
     */
    @Test
    public void courseBoundariesTest(){
        /**
        *Tests that the boat can't leave the left boundary of the course
         */
        when(course.getLeftBoundary()).thenReturn(0);
        when(course.getRightBoundary()).thenReturn(1080);

        boat.setXPosition(0);
        boat.setCurrentSpeed(10);
        boat.SteerLeft(course);

        assertTrue(boat.getX() >= 0);

        /**
        *Tests that the boat can't leave the right boundary of the course
         */
        boat.setXPosition(1080);
        boat.setCurrentSpeed(10);
        boat.SteerLeft(course);

        assertTrue(boat.getX() <= 1080);
    }
}

package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.badlogic.gdx.graphics.Texture;
import com.dragonboat.game.*;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class FR_AI {
    Lane lane = new Lane(0,200);
    Course course = mock(Course.class);

    @Test
    public void aiTest(){
        // Tests AI speed control

        Opponent boat = new Opponent(0,0,0,lane,"boat");
        boat.setCurrentSpeed(10);
        boat.setStats(40,5,10,5);
        boat.ai(0,"Easy",course);
        assertTrue(boat.getCurrentSpeed() > 10);

        // Tests AI control over the x position of the boat
        boat.setXPosition(0);
        boat.ai(0,"Easy",course);
        assertTrue(boat.getX() > 0);

        // Tests if AI can avoid obstacles
        boat = new Opponent(0,0,0,lane,"boat");
        lane.SpawnObstacle(99,0,"Rock1");
        boat.setCurrentSpeed(10);
        boat.setStats(40,5,10,5);
        boat.setXPosition(100);
        boat.ai(10,"Easy",course);
        assertTrue(boat.getX() != 100);
    }
}

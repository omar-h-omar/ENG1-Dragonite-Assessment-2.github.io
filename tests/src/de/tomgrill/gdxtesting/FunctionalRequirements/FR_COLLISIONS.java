package de.tomgrill.gdxtesting.FunctionalRequirements;



import com.badlogic.gdx.graphics.Texture;
import com.dragonboat.game.*;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert.*;
import org.junit.*;


import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.dragonboat.game.Lane;



import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class FR_COLLISIONS {


    /*
     * Mocking classes needed for the test
     */
    Lane lane = mock(Lane.class);

    /**
     * Tests if a boat's robustness and speed will decrease
     * after a collision with an obstacle.
     */
    @Test
    public void CollisionTest() {

       Boat boat = new Boat(20, 10, 5, lane, "Player");
       boat.setDurability(15);
       boat.setCurrentSpeed(3);
       boat.setROBUSTNESS(5);
       boat.ApplyDamage(10);
       assertEquals(13, boat.getDurability());
       assertTrue(boat.getCurrentSpeed() < 3);

    }
}

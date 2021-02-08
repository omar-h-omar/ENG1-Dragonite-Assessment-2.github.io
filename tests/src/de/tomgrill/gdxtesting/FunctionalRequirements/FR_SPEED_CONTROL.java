package de.tomgrill.gdxtesting.FunctionalRequirements;


import com.badlogic.gdx.*;
import com.dragonboat.game.Course;
import com.dragonboat.game.Lane;
import com.dragonboat.game.Player;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(GdxTestRunner.class)
public class FR_SPEED_CONTROL {

    Player player = new Player(10, 3, 8, mock(Lane.class), "Player");


    /**
     * Tests if the user can control the speed of the boat.
     */
    @Test
    public void SpeedControlTest() {

        /**
         * Setting up mock
         * */
        Gdx.input = mock(Input.class);
        Course course = mock(Course.class);
        when(course.getLeftBoundary()).thenReturn(0);
        when(course.getRightBoundary()).thenReturn(1080);


        /**
         * tests cases for when increasing speed
         * creating player to test on and setting test case stats
         * */

        player.setStats(5, 11, 3, 3);

        float start = player.getCurrentSpeed();
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);
        player.GetInput(course);
        float finish = player.getCurrentSpeed();

        assertTrue(finish > start);

        /**
         * resetting stat
         * */
        player.setCurrentSpeed(2);

        /**
         * test cases for when decreasing speed
         * */
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);
        player.GetInput(course);

        assertTrue(2 > player.getCurrentSpeed());

    }

}

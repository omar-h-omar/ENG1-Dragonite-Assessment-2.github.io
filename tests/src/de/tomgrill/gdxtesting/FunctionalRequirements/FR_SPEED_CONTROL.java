package de.tomgrill.gdxtesting.FunctionalRequirements;


import com.badlogic.gdx.*;
import com.dragonboat.game.Lane;
import com.dragonboat.game.Player;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class FR_SPEED_CONTROL {

    Lane lane = new Lane(10, 15);
    Player player = new Player(10, 3, 8, lane, "player"  );



    @Test
    public void SpeedControlTest() {

        //TODO ADD more test cases

        /*Setting up mock*/
        Gdx.input = mock(Input.class);


        /*tests cases for when increasing speed*/
        /*creating player to test on and setting test case stats*/
        player.setStats(5, 11, 3, 3);
        float start = player.getCurrentSpeed();
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);
        player.GetInput();
        float finish = player.getCurrentSpeed();

        assertTrue(finish > start);

        /*resetting stat*/
        player.setCurrentSpeed(2);

        /*test cases for when decreasing speed*/
        player.setStats(3, 4, 2, 3);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);
        player.GetInput();

        assertTrue(2 > player.getCurrentSpeed());

    }

}

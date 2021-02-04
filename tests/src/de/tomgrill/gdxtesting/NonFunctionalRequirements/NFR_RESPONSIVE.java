package de.tomgrill.gdxtesting.NonFunctionalRequirements;

import com.badlogic.gdx.*;
import com.dragonboat.game.Lane;
import com.dragonboat.game.Player;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Date;


@RunWith(GdxTestRunner.class)
public class NFR_RESPONSIVE {
    Date date = new Date();
    Lane lane = new Lane(10, 15);
    Player player = new Player(10, 3, 8, lane, "player"  );

    @Test
    public void TestResponse() {
        float startingSpeed = player.getCurrentSpeed();
        player.setStats(10, 10, 5, 10);
        long start = System.currentTimeMillis();
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(anyInt())).thenReturn(true);
        player.GetInput();
        if (player.getCurrentSpeed() > startingSpeed) {
            long end = System.currentTimeMillis();
            assertTrue(end - start < 50);
        } else {
            fail();
        }
    }
}
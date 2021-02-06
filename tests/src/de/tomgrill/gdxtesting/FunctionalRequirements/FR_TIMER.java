package de.tomgrill.gdxtesting.FunctionalRequirements;


import com.badlogic.gdx.utils.Array;
import com.dragonboat.game.Opponent;
import com.dragonboat.game.Player;
import com.dragonboat.game.ProgressBar;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class FR_TIMER {

    @Test
    public void testTimer() {
        ProgressBar progressBar = new ProgressBar(mock(Player.class),new Opponent[6]);
        progressBar.StartTimer();
        progressBar.IncrementTimer(2);
        float time = progressBar.getTime();
        assertTrue(time == 2);
    }
}

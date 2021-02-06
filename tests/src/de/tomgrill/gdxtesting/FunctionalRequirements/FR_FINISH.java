package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dragonboat.game.*;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class FR_FINISH {
    /*
     * Mocking classes needed for the test
     */
    Player player = new Player(0,0,0,mock(Lane.class),"Player");
    ProgressBar progressBar = mock(ProgressBar.class);

    /**
     * Tests if the game recognises when the
     * user's boat crosses the finish line and calculates the time taken.
     */
    @Test
    public void calculateLapTimeTest(){
        when(progressBar.getPlayerTime()).thenReturn(10F);
        float[] progress = {1}; // represents the progress of the boats
        GameScreen.UpdateFinishedBoats(progress,progressBar,player, new Opponent[0]);
        float time = player.getFastestTime();
        assertEquals(time,10.0,time);
    }
}

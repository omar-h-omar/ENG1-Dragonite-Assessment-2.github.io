package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.headless.mock.audio.MockAudio;
import com.badlogic.gdx.backends.headless.mock.audio.MockMusic;
import com.badlogic.gdx.files.FileHandle;
import com.dragonboat.game.DragonBoatGame;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.MockHandler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FR_MUSIC {
    DragonBoatGame game = new DragonBoatGame();

    @Test
    public void musicTest(){
        MockAudio mock = new MockAudio();
        Gdx.audio = mock;
        Gdx.files = mock(Files.class);
        game.setMusic();
        assertNotNull(game.music);
    }
}

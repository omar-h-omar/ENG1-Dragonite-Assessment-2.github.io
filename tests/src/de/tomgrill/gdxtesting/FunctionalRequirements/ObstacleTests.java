package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.dragonboat.game.Obstacle;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class ObstacleTests {
    Obstacle obstacle1 = new Obstacle(5, 10, 2, 4, 6, new Texture(Gdx.files.internal("OakLog.png")),"Goose");

    @Test
    public void TestObstacleCreation() {
        assertNotNull(obstacle1);
    }

    @Test
    public void TestObstacleMove() {
        obstacle1.Move(2, 5);
        assertTrue(obstacle1.getY() == 0);
        obstacle1.Move(- 5, 5);
        assertTrue(obstacle1.getY() == 5);
    }

}

package de.tomgrill.gdxtesting.UnitTests;

import com.dragonboat.game.Lane;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class LaneTests {

    @Test
    public void testLaneCreation() {
        Lane lane1 = new Lane(20, 40);
        assertNotNull(lane1);
    }

    @Test
    public void TestLaneSpawnObstacle() {}

    @Test
    public void TestLaneRemoveObstacle() {}
}

package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.dragonboat.game.Lane;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class FR_OBSTACLE_INCREASE {

    @Test
    public void IncreaseObstacleCount() {

        /**
        * Creating obstacleCount start to compare and creating Lane to test on
         */

        Lane lane = new Lane(1, 11);
        int obstacleCountStart = lane.getTotalObstacles();

        //Obstacle added to lane
        lane.SpawnObstacle(2, 3, "Goose");
        lane.SpawnObstacle(1, 3, "OakLog");


        int obstacleCountFinish = lane.getTotalObstacles();

        /**
        *Tests
         */
        Assert.assertNotNull(lane.getObstacles());
        Assert.assertTrue(obstacleCountFinish > obstacleCountStart);


    }
}

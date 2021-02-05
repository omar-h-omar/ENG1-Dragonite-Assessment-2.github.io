/*package de.tomgrill.gdxtesting.FunctionalRequirements;



//@RunWith(GdxTestRunner.class)
public class ObstacleTests {
*//*<<<<<<< HEAD
   *//**//* Texture rock1 = new Texture("core.assets.Rock1.png");
    //Obstacle obstacle1 = new Obstacle(5, 10, 2, 4, 6, rock1);
=======
    Obstacle obstacle1 = new Obstacle(5, 10, 2, 4, 6, new Texture(Gdx.files.internal("OakLog.png")),"Goose");
>>>>>>> ff0e62f824368fb4d372af193d13d2f60695819f

    @Test
    public void TestObstacleCreation() {
        //assertNotNull(obstacle1);
    }

    @Test
    public void TestObstacleMove() {
        obstacle1.Move(2, 5);
        assertTrue(obstacle1.getY() == 0);
        obstacle1.Move(- 5, 5);
<<<<<<< HEAD
        assertTrue(obstacle1.getY() == 7);
    }*//**//*
=======
        assertTrue(obstacle1.getY() == 5);*//*
    }
//>>>>>>> ff0e62f824368fb4d372af193d13d2f60695819f

}*/

package de.tomgrill.gdxtesting.FunctionalRequirements;



import com.badlogic.gdx.graphics.Texture;
import com.dragonboat.game.*;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert.*;
import org.junit.*;


import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.dragonboat.game.Lane;



import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class FR_COLLISIONS {


    /*Creating mock boat object to test*/
    Lane lane = new Lane(10, 15);
    Texture texture = mock(Texture.class);
    Boat boat = mock(Boat.class);


    @Test
    public void CollisionTest() {

       when(boat.CheckCollisions(anyInt())).thenReturn(true);

       if (boat.CheckCollisions(anyInt())) {

           /*pass test cases */
           /*Creating boat, applying damage collision and test*/
           /*test case 1*/
           Boat boat = new Boat(20, 10, 5, lane, "Player");
           boat.setDurability(15);
           boat.setCurrentSpeed(3);
           boat.setROBUSTNESS(5);
           boat.ApplyDamage(10);
           assertEquals(13, boat.getDurability());
           assertTrue(boat.getCurrentSpeed() < 3);

           /*test case 2*/
           Boat boat2 = new Boat(15, 5, 10, lane, "Bot2" );
           boat2.setDurability(10);
           boat2.setCurrentSpeed(5);
           boat2.setROBUSTNESS(2);
           boat2.ApplyDamage(6);
           assertEquals(7, boat2.getDurability());
           assertTrue(boat.getCurrentSpeed() < 5);

           /*fail test case*/
           Boat boat3 = new Boat(7, 3, 10, lane, "Bot3" );
           boat3.setDurability(12);
           boat3.setCurrentSpeed(4);
           boat3.setROBUSTNESS(8);
           boat3.ApplyDamage(16);
           assertFalse(boat3.getCurrentSpeed() == 4);


       } else {
           fail();
       }




    }
}

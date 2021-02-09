package de.tomgrill.gdxtesting.FunctionalRequirements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.dragonboat.game.Boat;
import com.dragonboat.game.Lane;
import com.dragonboat.game.PowerUp;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

@RunWith(GdxTestRunner.class)
public class FR_POWERUPS {

    @Test
    public void TestPowerups() {

        /**
         * Creating boat to test powerups on
         * */

        Lane lane = new Lane(10, 15);
        Boat boat1 = new Boat(3, 3, 7, lane, "Player");

        /**
         * Creating PowerUps to apply on boat
         * */

        PowerUp manuPowerUp = new PowerUp("Maneuverability", 2, 2,  new Texture(Gdx.files.internal("itemBox.png")), lane );
        PowerUp repairPowerUp = new PowerUp("Repair", 2, 2,  new Texture(Gdx.files.internal("itemBox.png")), lane );
        PowerUp speedPowerUp = new PowerUp("SpeedBoost", 2, 2,  new Texture(Gdx.files.internal("itemBox.png")), lane );
        PowerUp timePowerUp = new PowerUp("TimeReduction", 2, 2,  new Texture(Gdx.files.internal("itemBox.png")), lane );
        PowerUp invinPowerUp = new PowerUp("Invincibility", 2, 2,  new Texture(Gdx.files.internal("itemBox.png")), lane );


        /**
         * initial boat values before test
         * */
        boat1.setMANEUVERABILITY(1);
        boat1.setDurability(2);
        boat1.setCurrentSpeed(2);
        float initialTimeReduction = boat1.getTimeReduction();
        boolean boat1Invincible = boat1.getInvincible();

        /**
         * Applying powerups
         * */

        boat1.ApplyPowerUp(manuPowerUp);
        boat1.ApplyPowerUp(repairPowerUp);
        boat1.ApplyPowerUp(speedPowerUp);
        boat1.ApplyPowerUp(timePowerUp);
        boat1.ApplyPowerUp(invinPowerUp);

        /**
         * adding Powerups to slot
         * */

        boat1.AddPowerUp(manuPowerUp);
        boat1.AddPowerUp(repairPowerUp);



        /**
         * Test if powerUps are applied
         * */

        Assert.assertTrue(boat1.getManeuverability() == 2.0);
        Assert.assertTrue(boat1.getDurability() == 50);
        Assert.assertTrue(boat1.getCurrentSpeed() == 8.0 );
        Assert.assertTrue(boat1.getTimeReduction() == 5.0);
        Assert.assertTrue(boat1.getInvincible() == true);


        /**
         * tests if powerUp added to slots are actually added
         * */


        Assert.assertTrue(boat1.getPowerUpSlot()[0] == manuPowerUp );
        Assert.assertTrue(boat1.getPowerUpSlot()[1] == repairPowerUp );

    }
}

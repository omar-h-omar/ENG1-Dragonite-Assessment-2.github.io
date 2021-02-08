//"ASSESSMENT2:START"
package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents an invincibility power up in the game
 */
public class Invincibility extends PowerUp{
    public Lane givenLane;

    /**
     * <p>
     * Creates an invincibility power up instance.
     * </p>
     *
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the invincibility power up.
     * @param lane      Lane the power up will spawn in.
     */
    public Invincibility(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("Invincibility", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }
}
//"ASSESSMENT2:END"
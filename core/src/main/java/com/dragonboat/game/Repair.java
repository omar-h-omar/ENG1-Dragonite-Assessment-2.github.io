//"ASSESSMENT2:START"
package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a repair power up in the game
 */
public class Repair extends PowerUp{
    public Lane givenLane;

    /**
     * <p>
     * Creates a repair power up instance.
     * </p>
     *
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the repair power up.
     * @param lane      Lane the power up will spawn in.
     */
    public Repair(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("Repair", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }

}
//"ASSESSMENT2:END"
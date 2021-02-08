//"ASSESSMENT2:START"
package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a maneuverability power up in the game
 */
public class Maneuverability extends PowerUp{
    public Lane givenLane;

    /**
     * <p>
     * Creates a maneuverability power up instance.
     * </p>
     *
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the maneuverability power up.
     * @param lane      Lane the power up will spawn in.
     */
    public Maneuverability(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("Maneuverability", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }

}
//"ASSESSMENT2:END"
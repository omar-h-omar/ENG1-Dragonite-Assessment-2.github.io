//"ASSESSMENT2:START"
package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a speed boost power up in the game
 */
public class SpeedBoost extends PowerUp{
    public Lane givenLane;

    /**
     * <p>
     * Creates a speed boost power up instance.
     * </p>
     *
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the speed bosst power up.
     * @param lane      Lane the power up will spawn in.
     */
    public SpeedBoost(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("SpeedBoost", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }
}
//"ASSESSMENT2:END"
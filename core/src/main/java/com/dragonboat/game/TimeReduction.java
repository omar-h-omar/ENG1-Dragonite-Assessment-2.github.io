//"ASSESSMENT2:START"
package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a time reduction power up in the game
 */
public class TimeReduction extends PowerUp{
    public Lane givenLane;

    /**
     * <p>
     * Creates a time reduction power up instance.
     * </p>
     *
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the time reduction power up.
     * @param lane      Lane the power up will spawn in.
     */
    public TimeReduction(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("TimeReduction", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }
}
//"ASSESSMENT2:END"
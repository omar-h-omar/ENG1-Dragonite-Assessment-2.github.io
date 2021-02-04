//"ASSESSMENT2:START"
package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TimeReduction extends PowerUp{
    public Lane givenLane;

    public TimeReduction(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("TimeReduction", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }
}
//"ASSESSMENT2:END"
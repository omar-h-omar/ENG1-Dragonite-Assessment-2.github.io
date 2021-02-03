//"ASSESSMENT2:START"
package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpeedBoost extends PowerUp{
    public Lane givenLane;

    public SpeedBoost(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("SpeedBoost", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }
}
//"ASSESSMENT2:END"
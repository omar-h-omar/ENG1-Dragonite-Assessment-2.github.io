//"ASSESSMENT2:START"
package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;

public class Invincibility extends PowerUp{
    public Lane givenLane;

    public Invincibility(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("Invincibility", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }
}
//"ASSESSMENT2:END"
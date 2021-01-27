package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Maneuverability extends PowerUp{
    public Lane givenLane;
    public Maneuverability(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("Maneuverability", xPosition, yPosition, texture , lane);
        this.givenLane = lane;
    }

    public Texture getTexture() {
        return new Texture(Gdx.files.internal("Maneuverability\\sprite_0.png"));
    }

}
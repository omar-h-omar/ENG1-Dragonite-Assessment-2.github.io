package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Repair extends PowerUp{
    public Lane givenLane;
    public Repair(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("Repair", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }

    public Texture getTexture() {
        return new Texture(Gdx.files.internal("Repair\\sprite_0.png"));
    }

}

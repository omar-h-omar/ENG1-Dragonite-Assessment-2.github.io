package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TimeReduction extends PowerUp{
    public Lane givenLane;
    public TimeReduction(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("TimeReduction", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }

    public Texture getTexture() {
        return new Texture(Gdx.files.internal("TimeReduction\\sprite_0.png"));
    }
}
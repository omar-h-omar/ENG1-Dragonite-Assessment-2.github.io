package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpeedBoost extends PowerUp{
    public Lane givenLane;
    public SpeedBoost(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("SpeedBoost", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }

    public Texture getTexture() {
        return new Texture(Gdx.files.internal("SpeedBoost\\sprite_0.png"));
    }
}
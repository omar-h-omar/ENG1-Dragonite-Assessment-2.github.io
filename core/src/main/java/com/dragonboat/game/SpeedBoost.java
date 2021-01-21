package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

public class SpeedBoost extends PowerUp{
    public Lane givenLane;
    public SpeedBoost(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("SpeedBoost", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }
}
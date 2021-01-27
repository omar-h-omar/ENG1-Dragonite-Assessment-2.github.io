package com.dragonboat.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.dragonboat.game.PowerUp;

public class Invincibility extends PowerUp{
    public Lane givenLane;
    public Invincibility(int xPosition, int yPosition, Texture texture, Lane lane) {
        super("Invincibility", xPosition, yPosition, texture, lane);
        this.givenLane = lane;
    }

    public Texture getTexture() {
        return new Texture(Gdx.files.internal("Invincibility\\sprite_0.png"));
    }
}

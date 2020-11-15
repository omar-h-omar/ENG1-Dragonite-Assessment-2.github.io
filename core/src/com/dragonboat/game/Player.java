package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Boat{

    public Player(int yPosition, int width, int height, Lane lane, String name) {
        super(yPosition, width, height, lane, name);
    }

    public void GetInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.IncreaseSpeed();
            this.IncreaseTiredness();
        }
        else this.DecreaseTiredness();

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            //Call method associated
            this.DecreaseSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //Call method associated
            this.SteerLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            //Call method associated
            this.SteerRight();
        }
    }
    public void ChooseBoat(int boatNo) {
        /*
         Called in the MenuScreen class, used to set the player's boat to the boat they click on.
         boatNo is a number 0 <= x <= 6 corresponding to boats A - G
         */
        char boatLabel = (char) (65 + boatNo);
        this.setTexture(new Texture(Gdx.files.internal("core/assets/boat"+ boatLabel +" sprite1.png")));
        this.generateTextureFrames(boatLabel);
        this.SetStats(boatLabel);
    }
}

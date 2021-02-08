package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents the player's boat.
 */
public class Player extends Boat {

    /**
     * Creates an instance of the player boat.
     *
     * @param yPosition Y-position of the boat.
     * @param width     Width of the boat.
     * @param height    Height of the boat.
     * @param lane      Lane for the boat.
     * @param name      Name of the boat.
     */
    public Player(int yPosition, int width, int height, Lane lane, String name) {
        super(yPosition, width, height, lane, name);
    }

    /**
     * Moves the player based on key pressed (W, A, S, D).
     * @param course The race course
     */
    public void GetInput(Course course) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)|| Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.IncreaseSpeed();
            this.IncreaseTiredness();
        } else
            this.DecreaseTiredness();

        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            // Call method associated
            this.DecreaseSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            // Call method associated
            this.SteerLeft(course);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            // Call method associated
            this.SteerRight(course);
        }
  //"ASSESSMENT2:START"
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            // Call method associated
            if (!(this.boatPowerUps[0] == null)) {
                this.ApplyPowerUp(this.boatPowerUps[0]);
                boatPowerUps[0] = boatPowerUps[1];
                boatPowerUps[1] = null;
            }
        }
    }
  //"ASSESSMENT2:END"
    /**
     * <p>
     * Assigns the selected boat template to the boat.
     * </p>
     * <p>
     * This includes stats and texture.
     * </p>
     *
     * @param boatNo Number of the boat template selected.
     */
    public void ChooseBoat(int boatNo) {
        char boatLabel = (char) (65 + boatNo);
        this.setTexture(new Texture(Gdx.files.internal("boat" + boatLabel + " sprite1.png")));
        this.GenerateTextureFrames(boatLabel);
        this.setStats(boatLabel);
    }
}

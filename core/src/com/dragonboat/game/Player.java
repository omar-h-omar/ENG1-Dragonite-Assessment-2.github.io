package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Boat {
    private int x_coordinate;
    private int y_coordinate;

    public Player(int xPosition, int yPosition, int width, int height, Lane lane) {
        super(xPosition, yPosition, width, height, lane);
    }

    public  void GetInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            IncreaseSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            //Call method associated
            DecreaseSpeed();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //Call method associated
            SteerLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            //Call method associated
            SteerRight();
        }
    }
    public int[] ChooseBoat() {
        /*  In the game section where boats are picked the method
            is called and returns an array of current x,y coordiantes.

         */
        x_coordinate = Gdx.input.getX();
        y_coordinate = Gdx.input.getY();

        return new int[]{x_coordinate, y_coordinate};
    }
}

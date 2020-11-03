package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player {
    private int x_coordinate;
    private int y_coordinate;

    public  void GetInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            //Call method assoicated
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            //Call method assoicated
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            //Call method assoicated

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            //Call method assoicated
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

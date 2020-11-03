package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Boat{
    private int x_coordinate;
    private int y_coordinate;

    public Player(int yPosition, int width, int height, Lane lane) {
        super(yPosition, width, height, lane);
    }

    public  void GetInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.IncreaseSpeed();
        }
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
    public int[] ChooseBoat() {
        /*  In the game section where boats are picked the method
            is called and returns an array of current x,y coordiantes.

         */
        x_coordinate = Gdx.input.getX();
        y_coordinate = Gdx.input.getY();

        return new int[]{x_coordinate, y_coordinate};
    }
}

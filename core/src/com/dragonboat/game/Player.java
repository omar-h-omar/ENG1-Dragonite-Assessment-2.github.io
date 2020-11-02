package com.dragonboat.game;

import com.badlogic.gdx.Gdx;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player {
    private int x_coordinate;
    private int y_coordinate;
    public  void GetInput() {
        addKeyListener(new KeyListener()
            public void keyPressed(KeyEvent key_press) {
                int keyCode = key_press.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        //Call method associated
                        break;
                    case KeyEvent.VK_S:
                        //Call method associated
                        break;
                    case KeyEvent.VK_A:
                        //Call method associated
                        break;
                    case KeyEvent.VK_D:
                        //Call method associated
                        break;
                }
            }
        }

    public int[] ChooseBoat() {
        /*  In the game section where boats are picked the method
            is called and returns an array of current x,y coordiantes.

         */
        x_coordinate = Gdx.input.getX();
        y_coordinate = Gdx.input.getY();

        return new int[]{x_coordinate y_coordinate};
    }
}

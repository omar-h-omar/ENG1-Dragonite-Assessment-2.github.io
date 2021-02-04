package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

public class Rock extends Obstacle {

    /**
     * Creates a rock instance.
     *
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the rock.
     */
    public Rock(int xPosition, int yPosition, Texture texture,String obstacleType) {
        super(20, xPosition, yPosition, texture.getWidth(), texture.getHeight(), texture, obstacleType);
    }
}
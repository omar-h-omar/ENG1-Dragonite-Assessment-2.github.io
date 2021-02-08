package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a rock obstacle in the game
 */
public class Rock extends Obstacle {

    /**
     * Creates a rock instance.
     *
     * @param xPosition    X-position.
     * @param yPosition    Y-position.
     * @param texture      Texture asset for the rock.
     * @param obstacleType The type of the obstacle
     */
    public Rock(int xPosition, int yPosition, Texture texture,String obstacleType) {
        super(20, xPosition, yPosition, texture.getWidth(), texture.getHeight(), texture, obstacleType);
    }
}
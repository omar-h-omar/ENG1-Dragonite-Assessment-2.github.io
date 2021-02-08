package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a log obstacle on the course.
 */
public class Log extends Obstacle {

    /**
     * Creates a log instance.
     *
     * @param xPosition    X-position.
     * @param yPosition    Y-position.
     * @param texture      Texture asset for the log.
     * @param obstacleType The type of obstacle
     */
    public Log(int xPosition, int yPosition, Texture texture , String obstacleType) {
        super(15, xPosition, yPosition, texture.getWidth(), texture.getHeight(), texture, obstacleType);
    }
}
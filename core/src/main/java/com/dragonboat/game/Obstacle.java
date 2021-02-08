package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents an obstacle on the course.
 *
 * @see Log
 * @see Rock
 * @see Goose
 */
public class Obstacle {
    protected float yPosition, xPosition;
    private int damage;
    public int width, height;
    public Texture texture;
    public String obstacleType;

    /**
     * Creates an obstacle instance.
     *
     * @param damage       Damage the obstacle can inflict on a boat.
     * @param xPosition    X-position.
     * @param yPosition    Y-position.
     * @param width        Width of the obstacle.
     * @param height       Height of the obstacle.
     * @param texture      Texture asset for the obstacle.
     * @param obstacleType The type of obstacle
     */
    public Obstacle(int damage, int xPosition, int yPosition, int width, int height, Texture texture, String obstacleType) {
        this.damage = damage;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.obstacleType = obstacleType;
    }

    /**
     * Moves the obstacle.
     *
     * @param moveVal          Distance to move the object by.
     * @param backgroundOffset Offset from screen to course coordinates.
     */
    public void Move(float moveVal, int backgroundOffset) {
        this.setY(this.getY() - moveVal);
    }

    // getters and setters

    /**
     * Gets the obstacle's damage
     * @return Int representing damage the obstacle inflicts upon collision.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Gets the obstacle's x position
     * @return Float representing the x-position.
     */
    public float getX() {
        return this.xPosition;
    }

    /**
     * Gets the obstacle's y position
     * @return Float representing the y-position.
     */
    public float getY() {
        return this.yPosition;
    }

    /**
     * Sets the obstacle's y position
     * @param yPosition Y-position.
     */
    public void setY(float yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Sets the obstacle's y position
     * @param xPosition X-position.
     */
    public void setX(float xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Gets the obstacle's texture
     * @return Texture asset for obstacle.
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Gets the obstacle's x height
     * @return Int representing the height of the obstacle.
     */
    public int getHeight() {
        return this.height;
    }
}
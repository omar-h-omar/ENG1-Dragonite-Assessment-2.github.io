package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents an obstacle on the course
 * @see Log
 * @see Goose
 */
public class Obstacle {
	protected float yPosition, xPosition;
	private int damage;
	public int width, height;
	public Texture texture;

	/**
	 * Creates an obstacle instance
	 * @param damage Damage the obstacle can inflict on a boat
	 * @param xPosition X coordinate position
	 * @param yPosition Y coordinte position
	 * @param width Width of the obstacle
	 * @param height Height of the obstacle
	 * @param texture Texture asset for the obstacle
	 */
	public Obstacle(int damage, int xPosition, int yPosition, int width, int height, Texture texture) {
		this.damage = damage;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}

	//getters and setters

	/**
	 * 
	 * @return int representing damage the obstacle inflicts upon collision
	 */
	public int getDamage() {
		return this.damage;
	}

	/**
	 * 
	 * @return Float representing the x coordinate position
	 */
	public float getX() {
		return this.xPosition;
	}

	/**
	 * 
	 * @return Float representing the y coordinate position
	 */
	public float getY() {
		return this.yPosition;
	}

	/**
	 * 
	 * @param yPosition Y coordinate position
	 */
	public void setY(float yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * 
	 * @param xPosition X coordinate position
	 */
	public void setX(float xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Moves
	 * @param moveVal Distance to move the object by
	 * @param backgroundOffset Offset from screen to course coordinates
	 */
	public void Move(float moveVal, int backgroundOffset) {
		this.setY(this.getY() - moveVal);
	}

	/**
	 * 
	 * @return Texture asset for obstacle
	 */
	public Texture getTexture() {
		return this.texture;
	}

	/**
	 * 
	 * @return int representing the height of the obstacle
	 */
	public int getHeight() {
		return this.height;
	}
}
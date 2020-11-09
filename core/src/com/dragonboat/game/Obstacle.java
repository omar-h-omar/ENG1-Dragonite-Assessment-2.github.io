package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;


public class Obstacle {
	protected float yPosition, xPosition;
	private int damage;
	public int width, height;
	public Texture texture;

	public Obstacle(int damage, int xPosition, int yPosition, int width, int height, Texture texture) {
		this.damage = damage;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}

	//getters and setters

	public int getDamage() {
		return this.damage;
	}

	public float getX() {
		return this.xPosition;
	}

	public float getY() {
		return this.yPosition;
	}

	public void setY(float yPosition) {
		this.yPosition = yPosition;
	}

	public void setX(float xPosition) {
		this.xPosition = xPosition;
	}

	public void Move(float moveVal) {
		this.setY(this.getY() - moveVal);
	}

	public Texture getTexture() {
		return this.texture;
	}

	public int getHeight() {
		return this.height;
	}
}
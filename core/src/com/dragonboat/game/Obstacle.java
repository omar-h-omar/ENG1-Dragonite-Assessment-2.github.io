package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;


public class Obstacle {
	private int xPosition, yPosition, damage;
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

	public int getX() {
		return this.xPosition;
	}

	public int getY() {
		return this.yPosition;
	}

	public void setY(int yPosition) {
		this.yPosition = yPosition;
	}

	public void setX(int xPosition) {
		this.xPosition = xPosition;
	}

	public void Move(int moveVal) {
		setyPosition(getyPosition() - moveVal);
	}
}
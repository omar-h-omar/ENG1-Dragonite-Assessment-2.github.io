package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;


public class Obstacle {
	private int xPosition, yPosition, damage;
	public int  width, height;
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
	
	public void Move() {
		this.yPosition -= 10; // this number is currently a placeholder
	}
}

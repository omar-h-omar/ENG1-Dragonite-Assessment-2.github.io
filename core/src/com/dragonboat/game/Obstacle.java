package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;


public class Obstacle {
	private int damage;
	public int xPosition, yPosition, width, height;
	public Texture texture;
	public Lane lane;
	
	public Obstacle(int damage, int xPosition, int yPosition, int width, int height, Lane lane) {
		this.damage = damage;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.lane = lane;
	}
	
	
	public void Move() {
		yPosition -= 10; // this number is currently a placeholder
	}
	
	public void removeObstacle() {
		// this removes the Obstacle
		// obstacle is removed upon either leaving the boundaries of the course or
		// colliding with an instance of Boat
	}
}

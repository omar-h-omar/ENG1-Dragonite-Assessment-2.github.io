package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;


public class Obstacle {
	private int damage;
	public int xPosition, yPosition, width, height;
	public Texture texture; 
	
	public Obstacle(int damage, int xPosition, int yPosition, int width, int height) {
		this.damage = damage;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
	}
	
	/** public Obstacle spawnObstacle(int x, int y, String obstacleType) {
		
		I'm not sure that this belongs here?
	**/
	
	public void Move() {
		yPosition -= 10; // this number is currently a placeholder
	}
	
	public void removeObstacle() {
		// this removes the Obstacle
	}
}

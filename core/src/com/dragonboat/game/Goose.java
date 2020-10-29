package com.dragonboat.game;

public class Goose extends Obstacle {
	
	public Goose(int damage, int xPosition, int yPosition, int width, int height, Lane lane) {
		super(damage, xPosition, yPosition, width, height, lane);
	}
	
	public void changeDirection() {
		// changes a random direction
	}
}

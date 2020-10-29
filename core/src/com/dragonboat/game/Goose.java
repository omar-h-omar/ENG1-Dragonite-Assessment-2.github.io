package com.dragonboat.game;

public class Goose extends Obstacle {
	
	public Goose(int xPosition, int yPosition) {
		// Joe: geese will have a set damage value, could increase with difficulty.
		//      they'll also have a set width and height. just put some placeholders in for now.
		super(5, xPosition, yPosition, 10, 10);
	}
	
	public void changeDirection() {
		// changes a random direction
	}
}

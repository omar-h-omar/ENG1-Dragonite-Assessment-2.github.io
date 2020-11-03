package com.dragonboat.game;

public class Goose extends Obstacle {
	
	public Goose(int xPosition, int yPosition) {
		// Joe: geese will have a set damage value, could increase with difficulty.
		//      they'll also have a set width and height. just put some placeholders in for now.
		// Ben: geese can also face N, S, E, W.
		//      width and height will swap places when  switching between N/S and E/W.
		super(5, xPosition, yPosition, 10, 10, Texture texture);
	}
	
	public void changeDirection() {
		// changes a random direction
	}
}

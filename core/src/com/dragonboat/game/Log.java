package com.dragonboat.game;

public class Log extends Obstacle {
	
	public Log(int xPosition, int yPosition, Texture texture) {
		// Joe: logs will have a set damage value, could increase with difficulty.
		//      they'll also have a set width and height. just put some placeholders in for now.
		super(10, xPosition, yPosition, 10, 30, texture);
	}
}

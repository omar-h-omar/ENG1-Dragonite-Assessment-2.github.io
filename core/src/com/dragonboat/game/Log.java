package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a log obstacle on the course
 */
public class Log extends Obstacle {
	
	/**
	 * Creates a log instance
	 * @param xPosition X coordinate position
	 * @param yPosition Y coordinate position
	 * @param texture Texture asset for the log
	 */
	public Log(int xPosition, int yPosition, Texture texture) {
		// Joe: logs will have a set damage value, could increase with difficulty.
		//      they'll also have a set width and height. just put some placeholders in for now.
		super(15, xPosition, yPosition, texture.getWidth(), texture.getHeight(), texture);
	}
}

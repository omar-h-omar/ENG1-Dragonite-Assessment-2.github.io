package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;


public class Obstacle {
	private int damage;
	public int xPosition, yPosition, width, height;
	public Texture texture; 
	
	public Obscatle(int damage, int xPosition_, int yPosition_, int width_, int height_) {
		xPosition = xPosition_;
		yPosition = yPosition_;
		width = width_;
		height = height_;	
	}
}

package com.dragonboat.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Course {
	private Lane[] lanes;
	public int startY, finishY, leftBoundary, rightBoundary;
	public Texture texture;
	
	public Course(Texture texture, Lane[] lanes) {
		this.texture = texture;
		this.leftBoundary = 0;
		this.rightBoundary = Gdx.graphics.getWidth();
		this.lanes = lanes;
	}


	// getters and setters

	public Texture getTexture() {
		return texture;
	}

	public int getNoLanes() {
		return lanes.length;
	}

	public Lane[] getLanes() {
		return lanes;
	}
}

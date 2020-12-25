package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents the race course.
 */
public class Course {
	public Lane[] lanes;
	public int startY, finishY, leftBoundary, rightBoundary;
	public Texture texture;

	/**
	 * Creates a course instance for all the boats.
	 * 
	 * @param texture Texture asset for the course.
	 * @param lanes   Array of lane objects for the course.
	 */
	public Course(Texture texture, Lane[] lanes) {
		this.texture = texture;
		this.leftBoundary = 0;
		this.rightBoundary = Gdx.graphics.getWidth();
		this.lanes = lanes;
	}

	/**
	 * 
	 * @return Texture for the course (background image).
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * 
	 * @return Int representing the number of lanes in the course.
	 */
	public int getNoLanes() {
		return lanes.length;
	}
}

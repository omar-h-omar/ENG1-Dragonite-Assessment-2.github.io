package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;

public class Course {
	public Lane[] lanes;
	public int startY, finishY, leftBoundary, rightBoundary;
	public Texture texture;
	
	public Course(Texture texture) {
		this.texture = texture;
	}
}

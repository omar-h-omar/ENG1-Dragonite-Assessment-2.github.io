package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Goose extends Obstacle {

	public String direction = "South"; //Facing south by default.
	public Lane givenLane;
	
	public Goose(int xPosition, int yPosition, Texture texture, Lane lane) {
		// Joe: geese will have a set damage value, could increase with difficulty.
		//      they'll also have a set width and height. just put some placeholders in for now.
		// Ben: geese can also face N, S, E, W.
		//      width and height will swap places when  switching between N/S and E/W.
		super(5, xPosition, yPosition, texture.getWidth(), texture.getHeight(), texture);
		this.givenLane = lane;
	}
	
	public void changeDirection() {
		/*
		Changes the direction of the Goose to an appropriate albeit random cardinal direction.
		*/
		HashMap<String, ArrayList<String>> cardinals = new HashMap<String, ArrayList<String>>();
		cardinals.put("North", new ArrayList<String>(Arrays.asList("East", "West")));
		cardinals.put("East", new ArrayList<String>(Arrays.asList("North", "South")));
		cardinals.put("South", new ArrayList<String>(Arrays.asList("East", "West")));
		cardinals.put("West", new ArrayList<String>(Arrays.asList("North", "South")));
		
		direction = cardinals.get(direction).get(new Random().nextInt(2));
	}

	public void Move(float moveVal) {
		if(this.getX() > givenLane.GetLeftBoundary() || this.getX() < givenLane.GetRightBoundary()) {
			if(direction == "South" || direction == "West") {
				moveVal = moveVal * -1;
			}
			if(this.direction == "East" || this.direction == "West") {
				this.setX(this.getX() + moveVal);
				this.changeDirection();
			}
			if(direction == "North" || direction == "South") {
				this.setY(this.getY() + moveVal);
				int randomMove = 100;
				if(new Random().nextInt(randomMove) == randomMove - 1) {
					changeDirection();
				}
			}
		}
		else {
			this.direction = "South";
			this.setY(this.getY() - moveVal);
		}
		/*
		boolean edge = false;
		if(this.getX() == givenLane.GetLeftBoundary() || this.getX() == givenLane.GetRightBoundary()) {
			edge = true;
		}
		
		int randomMove = 100;
		if(new Random().nextInt(randomMove) == randomMove - 1) {
			changeDirection();
		}
		if(direction == "South" || direction == "West") {
			moveVal = moveVal * -1;
		}
		if(direction == "North" || direction == "South") {
			this.setY(this.getY() + moveVal/2);
		}
		else {
			if(!edge) {
				this.setX(this.getX() + moveVal/2);
			}
		}
		*/
	}
	
}

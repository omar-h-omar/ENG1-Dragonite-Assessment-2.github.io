package com.dragonboat.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Goose extends Obstacle {

	public String direction = "South"; //Facing south by default.
	
	public Goose(int xPosition, int yPosition, Texture texture) {
		// Joe: geese will have a set damage value, could increase with difficulty.
		//      they'll also have a set width and height. just put some placeholders in for now.
		// Ben: geese can also face N, S, E, W.
		//      width and height will swap places when  switching between N/S and E/W.
		super(5, xPosition, yPosition, 10, 10, Texture texture);
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

	public void Move() {
		int moveVal = 10;
		if(direction == "South" || direction == "West") {
			moveVal = moveVal * -1;
		}
		if(direction == "North" || direction == "South") {
			this.yPosition += moveVal; //------------------------------------------------------------------------------------------------ Need setter method.
		}
		else {
			this.xPosition += moveVal; //------------------------------------------------------------------------------------------------ Need setter method.
		}
	}
	
}

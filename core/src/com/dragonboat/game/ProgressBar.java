package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

public class ProgressBar {
	
	public Texture texture;

	private Player playerBoat;
	private Opponent[] opponnentBoats;
	private int courseLength;
	private float playerProgress;
	private float[] opponentProgress;
	private float timeSeconds;

	
	public ProgressBar(Player player, Opponent[] opponents, Course course) {
		this.playerBoat = player;
		this.opponnentBoats = opponents;

		this.courseLength = course.finishY - course.startY;
		
		//fill progress with empty
		this.playerProgress = 0f;
		for(int i = 0; i < this.opponnentBoats.length; i++){
			this.opponentProgress[i] = 0f;
		}
	}
	
	public void UpdatePositions() {
		//update player
		this.playerProgress = playerBoat.GetY() / this.courseLength;
		//update opponents
		for(int i = 0; i < this.opponnentBoats.length; i++){
			this.opponentProgress[i] = this.opponnentBoats[i].GetY() / this.courseLength;
		}

		//update time
		
	}

	//sets timer to zero
	public void StartTimer(){
		this.timeSeconds = 0;
	}

	//increments time
	public void IncrementTimer(float timePassed){
		this.timeSeconds += timePassed;
	}

	//need to register when boat passes finish line
}

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
	private float playerTime;
	private int finishLine;

	
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
	
	//update progress for all boats
	public void UpdateProgress() {
		//update player
		if(this.playerBoat.GetY() < this.finishLine){
			this.playerProgress = this.playerBoat.GetY() / this.courseLength;
		}else{
			this.playerProgress = 1f;
		}
		
		//update opponents
		for(int i = 0; i < this.opponnentBoats.length; i++){
			if(this.opponnentBoats[i].GetY() < this.finishLine){
				this.opponentProgress[i] = this.opponnentBoats[i].GetY() / this.courseLength;
			}else{
				this.opponentProgress[i] = 1f;
			}
		}
	}

	//return boat positions
	//player position, followed by all others
	public float[] GetProgress(){
		//make sure positions are up to data
		UpdateProgress();

		float[] out = new float[opponnentBoats.length + 1];
		out[0] = playerProgress;
		for(int i = 0; i < opponnentBoats.length; i++){
			out[i + 1] = opponentProgress[i];
		}

		return out;
	}

	//sets timer to zero
	public void StartTimer(){
		this.timeSeconds = 0f;
		this.playerTime = 0f;
	}

	//increments time
	public void IncrementTimer(float timePassed){
		this.timeSeconds += timePassed;
		//check player is still racing
		if(!this.playerBoat.Finished()){
			this.playerTime = this.timeSeconds;
		}
	}

	//get the current game time for player - to be displayed
	public float GetPlayerTime(){
		return this.playerTime;
	}

	//get current game time - goes till race finishes
	public float GetTime(){
		return this.timeSeconds;
	}
}

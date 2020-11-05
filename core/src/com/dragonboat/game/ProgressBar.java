package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

public class ProgressBar {
	
	public Texture texture;

	private Player playerBoat;
	private Opponent[] opponnentBoats;
	private float timeSeconds;
	private float playerTime;

	
	public ProgressBar(Player player, Opponent[] opponents, Course course) {
		this.playerBoat = player;
		this.opponnentBoats = opponents;
	}

	//return boat positions
	//player position, followed by all others
	public float[] GetProgress(){
		float[] out = new float[opponnentBoats.length + 1];
		out[0] = playerBoat.progress;
		for(int i = 0; i < opponnentBoats.length; i++){
			out[i + 1] = opponnentBoats[i].progress;
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

package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ProgressBar {
	
	public Texture texture, playerIcon, opponentIcon;

	private Player playerBoat;
	private Opponent[] opponentBoats;
	private float timeSeconds;
	private float playerTime;

	
	public ProgressBar(Player player, Opponent[] opponents, Course course) {
		this.playerBoat = player;
		this.opponentBoats = opponents;
		this.texture = new Texture(Gdx.files.internal("top bar sprite.png"));
		this.playerIcon = new Texture(Gdx.files.internal("progress icon player.png"));
		this.opponentIcon = new Texture(Gdx.files.internal("progress icon enemy.png"));

	}

	//return boat positions
	//player position, followed by all others
	public float[] getProgress(int finishY){
		float[] out = new float[opponentBoats.length + 1];
		out[0] = playerBoat.getProgress(finishY);
		for(int i = 0; i < opponentBoats.length; i++){
			out[i + 1] = opponentBoats[i].getProgress(finishY);
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
	public float getPlayerTime(){
		return this.playerTime;
	}

	//get current game time - goes till race finishes
	public float getTime(){
		return this.timeSeconds;
	}

	public Texture getTexture() {
		return texture;
	}

	public Texture getPlayerIcon() {
		return playerIcon;
	}

	public Texture getOpponentIcon() {
		return opponentIcon;
	}
}

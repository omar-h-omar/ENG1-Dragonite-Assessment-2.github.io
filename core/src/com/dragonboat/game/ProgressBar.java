package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a progress bar
 */
public class ProgressBar {
	
	public Texture texture, playerIcon, opponentIcon;

	private Player playerBoat;
	private Opponent[] opponentBoats;
	private float timeSeconds;
	private float playerTime;

	/**
	 * Creates a progress bar that tracks the player and opponent boats progress along the course
	 * @param player The player's boat
	 * @param opponents Array of opponent boats
	 */
	public ProgressBar(Player player, Opponent[] opponents) {
		this.playerBoat = player;
		this.opponentBoats = opponents;
		this.texture = new Texture(Gdx.files.internal("top bar sprite.png"));
		this.playerIcon = new Texture(Gdx.files.internal("progress icon player.png"));
		this.opponentIcon = new Texture(Gdx.files.internal("progress icon enemy.png"));

	}

	//return boat positions
	//player position, followed by all others

	/**
	 * Gets the progress of all boats
	 * @param finishY Y coordinate of the finish line
	 * @return Array of floats representing the percentage of the course covered by each boat. First index stores player's progress
	 */
	public float[] getProgress(int finishY){
		float[] out = new float[opponentBoats.length + 1];
		out[0] = playerBoat.getProgress(finishY);
		for(int i = 0; i < opponentBoats.length; i++){
			out[i + 1] = opponentBoats[i].getProgress(finishY);
		}

		return out;
	}

	/**
	 * Returns true if all boats have finished.
	 * @param finishY Y coordinate of the finish line
	 * @return Boolean representing if all boats have finished the course
	 */
	public boolean allFinished(int finishY){
		float[] progress = this.getProgress(finishY);
		for(int i = 0; i < progress.length; i++){
			if(progress[i] != 1){
				return false;
			}
		}
		return true;
	}

	/**
	 * Resets the timer to zero
	 */
	public void StartTimer(){
		this.timeSeconds = 0f;
		this.playerTime = 0f;
	}

	/**
	 * Increments the timer by the time passed
	 * @param timePassed The time delta from the last frame
	 */
	public void IncrementTimer(float timePassed){
		this.timeSeconds += timePassed;
		//check player is still racing
		if(!this.playerBoat.Finished()){
			this.playerTime = this.timeSeconds;
		}
	}

	/**
	 * Gets the time passed for the player in the current race
	 * @return Returns a float representing the player's current race time
	 */
	public float getPlayerTime(){
		return this.playerTime;
	}

	/**
	 * Gets the time passed for the current race
	 * @return Returns a float representing the current race time
	 */
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

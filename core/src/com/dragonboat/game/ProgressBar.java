package com.dragonboat.game;

import com.badlogic.gdx.graphics.Texture;

public class ProgressBar {
	
	public float playerProgress;
	public float[] opponentProgress;
	public Texture texture;

	
	public ProgressBar(float playerProgress, float[] opponentProgress) {
		this.playerProgress = playerProgress;
		this.opponentProgress = opponentProgress;
	}
	
	public void update() {
		// this updates the leaderboard
	}
}

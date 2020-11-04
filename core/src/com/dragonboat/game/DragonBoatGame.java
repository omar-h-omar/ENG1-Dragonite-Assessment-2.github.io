package com.dragonboat.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class DragonBoatGame extends Game {
	private GameScreen gameScreen;
	public Lane[] lanes;
	public Player player;
	public Course course;
	public int noOfObstacles;
	public float[][] obstacleTimes;
	private int noOfLanes;

	@Override
	public void create () {
		Random rnd = new Random();
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();

		noOfLanes = 7;
		noOfObstacles = 10;

		lanes = new Lane[noOfLanes];
		obstacleTimes = new float[noOfLanes][noOfObstacles];

		// Generating random times for each obstacle to spawn.

		for(int x = 0; x < lanes.length; x++) {
			lanes[x] = new Lane((x * w / lanes.length), (((x + 1) * w) / lanes.length));
			for (int y = 0; y < obstacleTimes[x].length; y++) {
				// This will need fine-tuning, basically maps a float from (0,1] to a float from (0,X] to space them out somewhat.
				obstacleTimes[x][y] = Math.round(80 * rnd.nextFloat());
			}
		}

		// Initialising game objects.

		course = new Course(new Texture(Gdx.files.internal("background sprite.png")), lanes);
		player = new Player(0,56, 182, lanes[3]);
		player.setTexture(new Texture(Gdx.files.internal("boatA sprite1.png")));
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	// All drawing is done in the GameScreen class.

	@Override
	public void render () {
		player.MoveForward();
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width,height);
	}

	@Override
	public void dispose () {
		gameScreen.dispose();
	}
}

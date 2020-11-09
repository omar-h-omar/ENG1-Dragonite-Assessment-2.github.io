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
	public Opponent[] opponents;
	public ProgressBar progressBar;
	public float[][] obstacleTimes;
	public int noOfObstacles;

	@Override
	public void create () {
		int w = Gdx.graphics.getWidth() - 80;
		int h = Gdx.graphics.getHeight();
		Random rnd = new Random();


		lanes = new Lane[7];
		noOfObstacles = 15;
		obstacleTimes = new float[lanes.length][noOfObstacles];

		for(int x = 0; x < lanes.length; x++) {
			lanes[x] = new Lane((x*w/lanes.length) + 40, (((x+1)*w)/lanes.length) + 40);
			for(int y = 0; y < noOfObstacles; y++) {
				obstacleTimes[x][y] = 5 * (rnd.nextFloat() + y);
			}
		}

		course = new Course(new Texture(Gdx.files.internal("core/assets/background sprite.png")), lanes);
		player = new Player(0,56, 182, lanes[3], "Player");

		opponents = new Opponent[0];

		progressBar = new ProgressBar(player, opponents, course);
		player.setTexture(new Texture(Gdx.files.internal("core/assets/boatA sprite1.png")));
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	// All drawing is done in the GameScreen class.

	@Override
	public void render () {
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

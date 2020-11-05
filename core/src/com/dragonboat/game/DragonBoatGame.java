package com.dragonboat.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class DragonBoatGame extends Game {
	public Texture courseTexture;
	GameScreen gameScreen;
	public Lane[] lanes;
	public Player player;
	public Course course;
	public float[][] obstacleTimes;
	public int noOfObstacles;

	@Override
	public void create () {
		courseTexture = new Texture(Gdx.files.internal("core/assets/background sprite.png"));
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		Random rnd = new Random();


		lanes = new Lane[7];
		noOfObstacles = 10;
		obstacleTimes = new float[lanes.length][noOfObstacles];

		for(int x = 0; x < lanes.length; x++) {
			lanes[x] = new Lane((x*w/lanes.length), (((x+1)*w)/lanes.length));
			for(int y = 0; y < noOfObstacles; y++) {
				obstacleTimes[x][y] = 80 * rnd.nextFloat();
			}
		}

		course = new Course(courseTexture, lanes);
		player = new Player(0,56, 182, lanes[3], "Player");
		player.setTexture(new Texture(Gdx.files.internal("core/assets/boatA sprite1.png")));
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

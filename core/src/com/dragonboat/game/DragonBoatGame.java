package com.dragonboat.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DragonBoatGame extends Game {
	public Texture courseTexture;
	GameScreen gameScreen;
	public Lane[] lanes;
	public Player player;

	@Override
	public void create () {
		courseTexture = new Texture(Gdx.files.internal("background sprite.png"));
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();


		lanes = new Lane[7];
		for(int x = 0; x < lanes.length; x++) {
			lanes[x] = new Lane((x/lanes.length) * w, ((x+1)/lanes.length) * w);
		}

		Course course = new Course(courseTexture, lanes);
		player = new Player(0,56, 182, lanes[3]);
		player.setTexture(new Texture(Gdx.files.internal("boatA sprite1.png")));
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

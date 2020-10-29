package com.dragonboat.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DragonBoatGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture courseTexture;
	private OrthographicCamera camera;
	@Override
	public void create () {
		batch = new SpriteBatch();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		courseTexture = new Texture(Gdx.files.internal("background sprite.png"));
		Course course = new Course(courseTexture);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / 2, h / 2);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		batch.draw(courseTexture,0,0);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width / 2, height / 2);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}

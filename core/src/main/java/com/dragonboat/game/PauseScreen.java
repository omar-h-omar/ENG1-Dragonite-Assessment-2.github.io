package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScreen implements Screen {

    private Texture resume;
    private SpriteBatch batch;
    final DragonBoatGame game;

    public PauseScreen(DragonBoatGame Game) {
        game = Game;
        batch = new SpriteBatch();
        Texture resume = new Texture("resume.png");

        if (Gdx.input.justTouched()) {
            //need to dispose PauseScreen
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        batch.begin();
        //draws resume in the middle of the screen
        batch.draw(resume, (Gdx.graphics.getWidth() - resume.getWidth()) / 2, (Gdx.graphics.getHeight() - resume.getHeight()) / 2);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        resume.dispose();
    }
}

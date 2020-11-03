package com.dragonboat.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    // ENVIRONMENT VARIABLES:
    // game
    private DragonBoatGame game;
    private Player player;

    // screen
    private OrthographicCamera camera;
    private Viewport viewport;

    // graphics
    private SpriteBatch batch;
    private Texture background;

    // timing
    private int backgroundOffset;

    // global parameters
    private final int WIDTH = 1080;
    private final int HEIGHT = 720;


    public GameScreen(DragonBoatGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH,HEIGHT,camera);

        // texture setting

        background = this.game.courseTexture;
        //this.game.player.setTexture(new Texture(Gdx.files.internal("boatA sprite1.png")));
        backgroundOffset = 0;
        batch = new SpriteBatch();
    }
    @Override
    public void render(float deltaTime) {
        /*
        Main rendering function. backgroundOffset determines which portion of the background is shown.
        this should be set to the player's y position each frame.
         */
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.player.GetInput();
        backgroundOffset = this.game.player.GetY();
        batch.begin();

        batch.draw(background,0,0, 0,background.getHeight()-HEIGHT-backgroundOffset, WIDTH, HEIGHT);
        batch.draw(this.game.player.texture, this.game.player.GetX(), this.game.player.GetY());

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
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
        background.dispose();
        player.texture.dispose();
    }

    @Override
    public void show() {

    }
}

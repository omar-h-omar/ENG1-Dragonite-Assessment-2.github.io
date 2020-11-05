package com.dragonboat.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Null;
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
    private float totalDeltaTime = 0;

    // global parameters
    private final int WIDTH = 1080;
    private final int HEIGHT = 720;


    public GameScreen(DragonBoatGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH,HEIGHT,camera);

        // texture setting

        background = this.game.course.getTexture();
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

        totalDeltaTime += deltaTime;
        for(int i = 0; i < this.game.course.getNoLanes(); i++) {
            for(int j = 0; j < this.game.noOfObstacles; j++) {
                if(this.game.obstacleTimes[i][j] - totalDeltaTime < 0.00001f) {
                    this.game.obstacleTimes[i][j] = 9999999999f;

                    // spawn an obstacle in lane i
                    System.out.println("obstacle spawn in lane " + i);
                }
            }
        }

        this.game.player.GetInput();

        // Until the player is at half of the window height, don't move the background
        // Then move the background so the player is centered.

        backgroundOffset = this.game.player.getY() + this.game.player.getHeight() / 2 > HEIGHT / 2 ? this.game.player.getY() + this.game.player.getHeight() / 2 - HEIGHT/2 : 0;

        batch.begin();

        batch.draw(background,0,0, 0,background.getHeight()-HEIGHT-backgroundOffset, WIDTH, HEIGHT);
        batch.draw(this.game.player.texture, this.game.player.getX(), this.game.player.getY()-backgroundOffset);

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

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
import java.util.Random;

public class GameScreen implements Screen {
    // ENVIRONMENT VARIABLES:
    private Random rnd;

    // game
    private DragonBoatGame game;
    private Player player;
    private Course course;
    private Lane[] lanes;

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
        rnd = new Random();

        this.game = game;
        player = this.game.player;
        course = this.game.course;
        lanes = this.game.lanes;


        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH,HEIGHT,camera);

        // texture setting

        background = course.getTexture();
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
        for(int i = 0; i < course.getNoLanes(); i++) {
            for(int j = 0; j < this.game.noOfObstacles; j++) {
                if(this.game.obstacleTimes[i][j] - totalDeltaTime < 0.00001f) {
                    this.game.obstacleTimes[i][j] = 9999999999f;
                    String[] obstacleTypes = {"Goose", "Log"};

                    // spawn an obstacle in lane i.
                    int xCoord = lanes[i].GetLeftBoundary() + rnd.nextInt(lanes[i].GetRightBoundary() - lanes[i].GetLeftBoundary());
                    lanes[i].SpawnObstacle(xCoord, backgroundOffset + HEIGHT, obstacleTypes[rnd.nextInt(obstacleTypes.length)]);
                    System.out.println("obstacle spawn in lane " + i + " at " + xCoord + ", " + (backgroundOffset + HEIGHT));
                }
            }
        }

        player.GetInput();
        player.MoveForward();

        // Until the player is at half of the window height, don't move the background
        // Then move the background so the player is centered.

        backgroundOffset = player.getY() + player.getHeight() / 2 > HEIGHT / 2 ? player.getY() + player.getHeight() / 2 - HEIGHT/2 : 0;

        batch.begin();

        // display background
        batch.draw(background,0,0, 0,background.getHeight()-HEIGHT-backgroundOffset, WIDTH, HEIGHT);

        // display player
        batch.draw(player.texture, player.getX(), player.getY()-backgroundOffset);

        // display and move obstacles
        for(int i = 0; i < lanes.length; i++) {
            for(int j = 0; j < lanes[i].obstacles.size(); j++) {
                Obstacle o = lanes[i].obstacles.get(j);
                o.Move(0.4f + (backgroundOffset > 0 ?player.getCurrentSpeed() : 0));
                batch.draw(o.getTexture(),o.getX(),o.getY());
            }
        }
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
        for(int i = 0; i < lanes.length; i++) {
            for(int j = 0; j < lanes[i].obstacles.size(); j++) {
                lanes[i].obstacles.get(j).getTexture().dispose();
            }
        }
    }

    @Override
    public void show() {

    }
}

package com.dragonboat.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private ProgressBar progressBar;
    private Leaderboard leaderboard;
    private Opponent[] opponents;
    private boolean started = false;
    private String[] times;

    // screen
    private OrthographicCamera camera;
    private Viewport viewport;

    // graphics
    private SpriteBatch batch;
    private Texture background;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;

    // timing
    private int backgroundOffset;
    private float totalDeltaTime = 0;

    // global parameters
    private final int WIDTH = 1080;
    private final int HEIGHT = 720;


    public GameScreen(DragonBoatGame game) {
        // grab game objects from DragonBoatGame
        rnd = new Random();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 28;
        font = generator.generateFont(parameter);
        this.game = game;
        player = this.game.player;
        course = this.game.course;
        lanes = this.game.lanes;
        progressBar = this.game.progressBar;
        opponents = this.game.opponents;
        leaderboard = this.game.leaderboard;

        // setup view
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
        this should be set to the player's y position each frame
        totalDeltaTime is the total time passed.
         */
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // if the game has started, start incrementing time.
        totalDeltaTime += started ? deltaTime : 0;

        // check whether obstacles need to be spawned.
        for (int i = 0; i < course.getNoLanes(); i++) {
            if(!started || player.Finished() || this.game.obstacleTimes[i].size() == 0) break;
            if (this.game.obstacleTimes[i].get(0) - totalDeltaTime < 0.0001f) {
                String[] obstacleTypes = {"Goose", "Log"};
                // spawn an obstacle in lane i.
                int xCoord = lanes[i].GetLeftBoundary() + rnd.nextInt(lanes[i].GetRightBoundary() - lanes[i].GetLeftBoundary());
                lanes[i].SpawnObstacle(xCoord, HEIGHT + 40, obstacleTypes[rnd.nextInt(obstacleTypes.length)]);
                // make sure obstacle is only spawned once.
                // might implement this as an ordered list if it impacts the performance.
                this.game.obstacleTimes[i].remove(0);
            }
        }
        // move player
        player.GetInput();
        player.MoveForward();
        if(player.getCurrentSpeed() > 0 && !started)
        {
            // detect start of game (might change this to a countdown)
            started = true;
            progressBar.StartTimer();
        }

        // move opponents
        for(Opponent o : opponents) {
            if(!started) break;
            o.IncreaseSpeed();
            o.MoveForward();

            if(Math.round(totalDeltaTime)%1 == 0) {
                o.ai(backgroundOffset);
            }
        }

        // Until the player is at half of the window height, don't move the background
        // Then move the background so the player is centered.
        if(player.getY() + HEIGHT / 2 + player.getHeight()/2 > course.getTexture().getHeight()) {}
        else if(player.getY() + player.getHeight() / 2 > HEIGHT / 2) backgroundOffset = player.getY() + player.getHeight() / 2 - HEIGHT / 2;

        batch.begin();

        // display background
        batch.draw(background, 0, 0, 0, background.getHeight() - HEIGHT - backgroundOffset, WIDTH, HEIGHT);

        // display and move obstacles
        for (Lane lane : lanes) {
            if (!started) break;
            for (int j = 0; j < lane.obstacles.size(); j++) {
                Obstacle o = lane.obstacles.get(j);
                // if the background hasn't started moving yet, or if the player has reached the top of the course, move obstacle at set speed.
                // else add the player speed to the obstacle speed.
                o.Move(0.4f + (backgroundOffset > 0 && player.getY() + HEIGHT / 2 + player.getHeight() / 2 < course.getTexture().getHeight() ? player.getCurrentSpeed() : 0));
                if (o.getY() < -o.getHeight()) {
                    lane.RemoveObstacle(o);
                }
                batch.draw(o.getTexture(), o.getX(), o.getY());
            }
        }

        // display player
        batch.draw(player.texture, player.getX(), player.getY() - backgroundOffset);

        // display opponents
        for(Opponent o : opponents) {
            batch.draw(o.texture, o.getX(), o.getY() - backgroundOffset);
            //font.draw(batch, Float.toString(o.getY()), o.getX() + 20, o.getY() - backgroundOffset);
        }


        // display progress bar
        batch.draw(progressBar.getTexture(), WIDTH - progressBar.getTexture().getWidth() - 60, HEIGHT - progressBar.getTexture().getHeight() - 20);

        // get progress for each boat, draw player and opponent icons on progress bar with x coordinates respective to their progress.
        float[] progress = progressBar.getProgress(course.getTexture().getHeight());
        for(int i = 1; i < progress.length; i++) {
            batch.draw(progressBar.getOpponentIcon(), WIDTH - progressBar.getTexture().getWidth() - 50 + progress[i] * (progressBar.getTexture().getWidth()-214), HEIGHT - progressBar.getTexture().getHeight() / 2 - 10);
        }
        batch.draw(progressBar.getPlayerIcon(),WIDTH - progressBar.getTexture().getWidth() - 50 + progress[0] * (progressBar.getTexture().getWidth() - 214), HEIGHT - progressBar.getTexture().getHeight() / 2 - 10);

        // check if player has finished, if so update the finished boolean
        if(progress[0] == 1 && !player.Finished()) {
            player.setFinished(true);
            player.UpdateFastestTime(progressBar.getPlayerTime());
        }

        //check if opponents have finished, if so update the finished boolean
        for(int i = 0; i < opponents.length; i++){
            if(progress[i + 1] == 1 && !opponents[i].Finished()){
                opponents[i].setFinished(true);
                opponents[i].UpdateFastestTime(progressBar.getTime());
            }
        }

        // display player time
        progressBar.IncrementTimer(deltaTime);
        font.draw(batch, Float.toString(started ? Math.round(progressBar.getPlayerTime() * 100) / 100.00f : 0.00f), WIDTH-230, HEIGHT-40);

        //check if all boats have passed the finish line
        //if so, generate the leaderboard
        if(progressBar.allFinished(course.getTexture().getHeight())){
            batch.draw(leaderboard.getTexture(), WIDTH/2 - leaderboard.getTexture().getWidth()/2, HEIGHT/2 - leaderboard.getTexture().getHeight()/2);
            this.times = leaderboard.GetTimes(opponents.length + 1);
            for(int i = 0; i < opponents.length + 1; i++){
                font.draw(batch, this.times[i], WIDTH/2 - leaderboard.getTexture().getWidth()/2,
                600 - 50 * i);
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
        for (Lane lane : lanes) {
            for (int j = 0; j < lane.obstacles.size(); j++) {
                lane.obstacles.get(j).getTexture().dispose();
            }
        }
        progressBar.getTexture().dispose();
        progressBar.getOpponentIcon().dispose();
        progressBar.getPlayerIcon().dispose();
    }

    @Override
    public void show() {

    }
}

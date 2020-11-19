package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

/**
 * Main Game Screen class for Dragon Boat Game.
 * This is the main game loop, handling all the game logic and rendering.
 */
public class GameScreen implements Screen {
    // ENVIRONMENT VARIABLES:
    private Random rnd;
    private final int MAX_DURABILITY = 40, MAX_TIREDNESS = 100;

    // game
    private DragonBoatGame game;
    private Player player;
    private Course course;
    private Lane[] lanes;
    private ProgressBar progressBar;
    private Leaderboard leaderboard;
    private Opponent[] opponents;
    private String[] times;
    private boolean started = false;
    private float penalty = 0.016f;

    // screen
    private OrthographicCamera camera;
    private Viewport viewport;

    // graphics
    private SpriteBatch batch;
    private Texture background, healthBarFull, healthBarEmpty, staminaBarFull, staminaBarEmpty;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font28, font44;

    // timing
    private int backgroundOffset;
    private float totalDeltaTime = 0;

    // global parameters
    private final int WIDTH = 1080, HEIGHT = 720;

    /**
     * Sets up everything needed for a race to take place
     * @param game represents the initial state of DragonBoatGame.
     */
    public GameScreen(DragonBoatGame game) {
        // grab game objects from DragonBoatGame
        rnd = new Random();
        this.game = game;
        player = this.game.player;
        course = this.game.course;
        lanes = this.game.lanes;
        progressBar = this.game.progressBar;
        opponents = this.game.opponents;

        ArrayList<Integer> possibleBoats = new ArrayList<Integer>();
        for(int i = 0; i < lanes.length; i++) {
            if(i != game.playerChoice) {
                possibleBoats.add(i);
            }
        }
        for(Opponent o : opponents) {
            int choice = o.SetRandomBoat(possibleBoats);
            possibleBoats.remove(choice);
        }

        leaderboard = this.game.leaderboard;

        // setup view
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH,HEIGHT,camera);

        // texture setting
        background = course.getTexture();
        backgroundOffset = 0;
        batch = new SpriteBatch();
        generator = game.generator;
        parameter = game.parameter;
        parameter.size = 28;
        font28 = generator.generateFont(parameter);
        parameter.size = 44;
        font44 = generator.generateFont(parameter);
        staminaBarFull = new Texture(Gdx.files.internal("core/assets/bar stamina yellow.png"));
        staminaBarEmpty = new Texture(Gdx.files.internal("core/assets/bar stamina grey.png"));
        healthBarFull = new Texture(Gdx.files.internal("core/assets/bar health yellow.png"));
        healthBarEmpty = new Texture(Gdx.files.internal("core/assets/bar health grey.png"));
    }


    /**
     * <p>Rendering function for the game loop, handling all game logic and displaying graphics.</p>
     *
     * <p>GAME LOOP</p>
     *
     * <p>- Spawns any Obstacles that need spawning.</p>
     * <p>- Update Player and Opponent positions.</p>
     * <p>- Check for collisions with Obstacles.</p>
     * <p>- Display Background and Obstacles</p>
     * <p>- Update Obstacle positions.</p>
     * <p>- Display Player, Player UI and Opponents.</p>
     * <p>- Display Progress Bar and checks which boats have finished.</p>
     * <p>- Display Player timer.</p>
     * <p>- Checks if all boats have finished, and displays a Leaderboard if so.</p>
     *
     * @param deltaTime Time passed since render function was last run.
     */
    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // if the game has started, start incrementing time.
        totalDeltaTime += started ? deltaTime : 0;

        // check whether obstacles need to be spawned, and spawns them if so.
        for (int i = 0; i < course.getNoLanes(); i++) {
            if (!started || player.Finished() || this.game.obstacleTimes[i].size() == 0) break;
            if (this.game.obstacleTimes[i].get(0) - player.getY() + player.getHeight() < 1) {
                String[] obstacleTypes = {"Goose", "Log"};
                // spawn an obstacle in lane i.
                int xCoord = lanes[i].GetLeftBoundary() + rnd.nextInt(lanes[i].GetRightBoundary() - lanes[i].GetLeftBoundary() - 15);
                lanes[i].SpawnObstacle(xCoord, HEIGHT + 40, obstacleTypes[rnd.nextInt(obstacleTypes.length)]);
                // make sure obstacle is only spawned once.
                // might implement this as an ordered list if it impacts the performance.
                this.game.obstacleTimes[i].remove(0);
            }
        }
        // move player
        player.GetInput();
        player.MoveForward();
        if (player.getCurrentSpeed() > 0 && !started) {
            // detect start of game (might change this to a countdown)
            started = true;
            progressBar.StartTimer();
        }
        if (player.getY() % 5 == 2) player.AdvanceTextureFrame();

        // move opponents
        for (Opponent o : opponents) {
            if (!started) break;
            o.MoveForward();
            o.CheckCollisions(backgroundOffset);
            if (Math.round(totalDeltaTime) % 2 == 0) {
                o.ai(backgroundOffset);
            }
            if (o.getY() % 5 == 2) o.AdvanceTextureFrame();
        }

        // increase the background offset so the player is centered.

        if (player.getY() + HEIGHT / 2 + player.getHeight() / 2 > course.getTexture().getHeight()) {
            // stop increasing the background offset when the player reaches the end of the course.
        } else if (player.getY() + player.getHeight() / 2 > HEIGHT / 2) {
            // start increasing the background offset when the player is above half the window height.
            backgroundOffset = player.getY() + player.getHeight() / 2 - HEIGHT / 2;
        }

        player.CheckCollisions(backgroundOffset);


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
                o.Move(0.4f + (backgroundOffset > 0 && player.getY() + HEIGHT / 2 + player.getHeight() / 2 < course.getTexture().getHeight() ? player.getCurrentSpeed() : 0), backgroundOffset);
                if (o.getY() < -o.getHeight()) {
                    lane.RemoveObstacle(o);
                }
                batch.draw(o.getTexture(), o.getX(), o.getY());
            }
        }

        // display player and player UI
        batch.draw(player.texture, player.getX(), player.getY() - backgroundOffset);
        batch.draw(staminaBarEmpty, player.lane.GetLeftBoundary(), player.getY() - 20 - backgroundOffset);
        batch.draw(healthBarEmpty, player.lane.GetLeftBoundary(), player.getY() - 40 - backgroundOffset);
        batch.draw(staminaBarFull, player.lane.GetLeftBoundary(), player.getY() - 20 - backgroundOffset, 0, 0, Math.round(staminaBarFull.getWidth() * player.getTiredness() / MAX_TIREDNESS), staminaBarFull.getHeight());
        batch.draw(healthBarFull, player.lane.GetLeftBoundary(), player.getY() - 40 - backgroundOffset, 0, 0, Math.round(healthBarFull.getWidth() * player.getDurability() / MAX_DURABILITY), healthBarFull.getHeight());

        // display opponents
        for (Opponent o : opponents) {
            batch.draw(o.texture, o.getX(), o.getY() - backgroundOffset);
            //font28.draw(batch, o.getX() + ", " + o.getCurrentSpeed(), o.getX() + 20, o.getY() - backgroundOffset);
        }

        // display progress bar
        batch.draw(progressBar.getTexture(), WIDTH - progressBar.getTexture().getWidth() - 60, HEIGHT - progressBar.getTexture().getHeight() - 20);

        // get progress for each boat
        // draw player and opponent icons on progress bar with x coordinates respective to their progress.
        float[] progress = progressBar.getProgress(course.getTexture().getHeight());
        for (int i = 1; i < progress.length; i++) {
            batch.draw(progressBar.getOpponentIcon(), WIDTH - progressBar.getTexture().getWidth() - 50 + progress[i] * (progressBar.getTexture().getWidth() - 214), HEIGHT - progressBar.getTexture().getHeight() / 2 - 10);
        }
        batch.draw(progressBar.getPlayerIcon(), WIDTH - progressBar.getTexture().getWidth() - 50 + progress[0] * (progressBar.getTexture().getWidth() - 214), HEIGHT - progressBar.getTexture().getHeight() / 2 - 10);

        // check if player has finished, if so update the finished boolean
        if (progress[0] == 1 && !player.Finished()) {
            player.setFinished(true);
            player.UpdateFastestTime(progressBar.getPlayerTime());
        }

        //check if opponents have finished, if so update the finished boolean
        for (int i = 0; i < opponents.length; i++) {
            if (progress[i + 1] == 1 && !opponents[i].Finished()) {
                opponents[i].setFinished(true);
                opponents[i].UpdateFastestTime(progressBar.getTime());
            }
        }

        // display player time
        progressBar.IncrementTimer(deltaTime);
        font28.draw(batch, started ? progressBar.getPlayerTimeString() : "", WIDTH - 230, HEIGHT - 40);

        //apply penalties
        //check player boat is in their lane
        if (!player.CheckIfInLane() && !player.Finished()) {
            player.applyPenalty(penalty);
            font28.setColor(Color.RED);
            font28.draw(batch, "Warning! Penalty applied for leaving lane", 240, 100);
            font28.setColor(Color.WHITE);
        }
        //check opponent boats are in their lanes
        for (int i = 0; i < opponents.length; i++) {
            if (!opponents[i].CheckIfInLane() && !opponents[i].Finished()) {
                opponents[i].applyPenalty(penalty);
            }
        }

        /**
         * Check if all boats have passed the finish line
         * if so, generate the leaderboard
         */
        if (progressBar.allFinished(course.getTexture().getHeight()) || (game.difficulty == 4 && player.Finished())) {
            // display leaderboard, if on the third leg, display top 3 boats
            if (game.difficulty < 3) {
                batch.draw(leaderboard.getTexture(), WIDTH / 2 - leaderboard.getTexture().getWidth() / 2, HEIGHT / 2 - leaderboard.getTexture().getHeight() / 2);
                this.times = leaderboard.GetTimes(opponents.length + 1);
                for (int i = 0; i < opponents.length + 1; i++) {
                    font44.draw(batch, this.times[i], WIDTH / 2 - leaderboard.getTexture().getWidth() / 3, 620 - (75 * i));
                }
                font28.draw(batch, "Click anywhere to progress to next leg.", 200, 40);
                /**
                 * Defines how to handle keyboard and mouse inputs
                 */
                Gdx.input.setInputProcessor(new InputAdapter() {
                    @Override
                    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                        game.advanceLeg();
                        Gdx.input.setInputProcessor(null);
                        return super.touchUp(screenX, screenY, pointer, button);
                    }
                });
            } else if (game.difficulty == 3) {
                batch.draw(leaderboard.getTexture(), WIDTH / 2 - leaderboard.getTexture().getWidth() / 2, HEIGHT / 2 - leaderboard.getTexture().getHeight() / 2);
                this.times = leaderboard.GetTimes(opponents.length + 1);
                for (int i = 0; i < opponents.length + 1; i++) {
                    if (i < 3) font44.setColor(Color.GOLD);
                    else font44.setColor(Color.WHITE);
                    font44.draw(batch, this.times[i], WIDTH / 2 - leaderboard.getTexture().getWidth() / 3, 620 - (75 * i));
                }
                if (this.times[0].startsWith("Player") || this.times[1].startsWith("Player") || this.times[2].startsWith("Player")) {
                    font28.draw(batch, "Click anywhere to progress to the final!", 200, 40);
                    /**
                     * Defines how to handle keyboard and mouse inputs
                     */
                    Gdx.input.setInputProcessor(new InputAdapter() {
                        @Override
                        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                            game.advanceLeg();
                            Gdx.input.setInputProcessor(null);
                            return super.touchUp(screenX, screenY, pointer, button);
                        }
                    });
                } else this.game.endGame();
            }
        }
        batch.end();
    }

    /**
     * Resizes the game screen
     * @param width Width of the screen
     * @param height Height of the screen
     */
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

    /**
     * Disposes of the screen when it is no longer needed.
     */
    @Override
    public void dispose() {
        batch.dispose();
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
        staminaBarFull.dispose();
        staminaBarEmpty.dispose();
        healthBarEmpty.dispose();
        healthBarFull.dispose();
        generator.dispose();
        font28.dispose();
        font44.dispose();
        leaderboard.getTexture().dispose();

    }

    @Override
    public void show() {

    }
}

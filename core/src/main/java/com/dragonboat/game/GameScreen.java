package com.dragonboat.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main Game Screen class for Dragon Boat Game. This is the main game loop,
 * handling all the game logic and rendering.
 */
public class GameScreen implements Screen {
    // ENVIRONMENT VARIABLES:
    private final Random rnd;
    private final int MAX_DURABILITY = 50, MAX_TIREDNESS = 100;
    private DragonBoatGame game = null;
    private boolean showWhereSaved = false;
    private Integer CurrentSaveNum;

    // debug booleans
    private boolean debug_speed, debug_positions, debug_norandom, debug_verboseoutput;

    // game
    private final Player player;
    private final Course course;
    private final Lane[] lanes;
    private final ProgressBar progressBar;
    private final Leaderboard leaderboard;
    private final Opponent[] opponents;
    private String[] times;
    private boolean started = false;
    private final float penalty = 0.016f;

    // screen
    private final OrthographicCamera camera;
    private final Viewport viewport;

    // graphics
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Texture background;
    private final Texture healthBarFull;
    private final Texture healthBarEmpty;
    private final Texture powerUpEmpty;
    private final Texture staminaBarFull;
    private final Texture staminaBarEmpty;
    private final FreeTypeFontGenerator generator;
    private final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private final BitmapFont font18, font28, font44;

    // timing
    private int backgroundOffset;
    private float totalDeltaTime = 0;
    private float powerUpTime = 100;

    // global parameters
    private final int WIDTH = 1080, HEIGHT = 720;
    private enum State {
        Paused,Running,Saving
    }
    private State state;

    /**
     * Sets up everything needed for a race to take place.
     *
     * @param game Represents the initial state of DragonBoatGame.
     */
    public GameScreen(DragonBoatGame game) {
        /*
         * Grab game objects from DragonBoatGame.
         */
        debug_speed = game.debug_speed;
        debug_positions = game.debug_positions;
        debug_norandom = game.debug_norandom;
        debug_verboseoutput = game.debug_verboseoutput;

        this.game = game;
        player = this.game.player;
        course = this.game.course;
        lanes = this.game.lanes;
        progressBar = this.game.progressBar;
        opponents = this.game.opponents;
        rnd = this.game.rnd;

        ArrayList<Integer> possibleBoats = new ArrayList<Integer>();
        for (int i = 0; i < lanes.length; i++) {
            if (i != game.playerChoice) {
                possibleBoats.add(i);
            }
        }
        for (Opponent o : opponents) {
            int choice = o.SetRandomBoat(possibleBoats);
            possibleBoats.remove(choice);
        }

        leaderboard = this.game.leaderboard;

        // setup view
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);

        // texture setting
        background = course.getTexture();
        backgroundOffset = 0;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        generator = game.generator;
        parameter = game.parameter;
        parameter.size = 18;
        font18 = generator.generateFont(parameter);
        parameter.size = 28;
        font28 = generator.generateFont(parameter);
        parameter.size = 44;
        font44 = generator.generateFont(parameter);
        staminaBarFull = new Texture(Gdx.files.internal("bar stamina yellow.png"));
        staminaBarEmpty = new Texture(Gdx.files.internal("bar stamina grey.png"));
        healthBarFull = new Texture(Gdx.files.internal("bar health yellow.png"));
        healthBarEmpty = new Texture(Gdx.files.internal("bar health grey.png"));
        powerUpEmpty = new Texture(Gdx.files.internal("powerUpEmpty.png"));
        state = State.Running;
    }

    /**
     * <p>
     * Rendering function for the game loop, handling all game logic and displaying
     * graphics.
     * </p>
     *
     * <p>
     * GAME LOOP
     * </p>
     *
     * <p>
     * - Spawns any Obstacles that need spawning.
     * </p>
     * <p>
     * - Update Player and Opponent positions.
     * </p>
     * <p>
     * - Check for collisions with Obstacles.
     * </p>
     * <p>
     * - Display Background and Obstacles
     * </p>
     * <p>
     * - Update Obstacle positions.
     * </p>
     * <p>
     * - Display Player, Player UI and Opponents.
     * </p>
     * <p>
     * - Display Progress Bar and checks which boats have finished.
     * </p>
     * <p>
     * - Display Player timer.
     * </p>
     * <p>
     * - Checks if all boats have finished, and displays a Leaderboard if so.
     * </p>
     *
     * @param deltaTime Time passed since render function was last run.
     */
    @Override
    public void render(float deltaTime) {
        /*
         * Checks the current game state and shows
         * content accordingly.
         */
        switch (state) {
            case Paused:
                // Returns to the game
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                    state = State.Running;
                }
                // Sets a black background
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(new Color(0, 0, 0, 0.25f));
                shapeRenderer.rect(0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
                shapeRenderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
                // Displays Menu Options
                batch.begin();
                font44.draw(batch,"Game Paused",WIDTH/2 - 135,HEIGHT/2 + 50);
                font28.draw(batch,"Resume",WIDTH/2 - 40,HEIGHT/2);
                font28.draw(batch,"Save",WIDTH/2 - 40,HEIGHT/2 - 40);
                font28.draw(batch,"Exit",WIDTH/2 - 40,HEIGHT/2 - 80);
                batch.end();

                // Handles user input on the pause menu
                pauseMenuInput();
                break;
            case Saving:
                // Returns to the pause menu
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                    state = State.Paused;
                    showWhereSaved = false;
                }

                // Sets a black background
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(new Color(0, 0, 0, 0.25f));
                shapeRenderer.rect(0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
                shapeRenderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);

                /*
                 * Displays Menu Options
                 * Shows the current game saves.
                 * If all slots are full, then tells the player to overwrite an old save or cancel save.
                 * When a player saves, shows the player which save slot was used.
                  */
                batch.begin();

                // Used to access the save files
                Preferences prefs = Gdx.app.getPreferences("GameSave1");
                Preferences prefs2 = Gdx.app.getPreferences("GameSave2");
                Preferences prefs3 = Gdx.app.getPreferences("GameSave3");

                // Gets a unique string to tell if the file is empty or not
                String save1 = prefs.getString("Save");
                String save2 = prefs2.getString("Save");
                String save3 = prefs3.getString("Save");

                // Indicates if the new save button is centered
                boolean buttonCentered = false;

                font44.draw(batch,"Current Saves:",WIDTH/4 + 110,HEIGHT/2 + 100);
                if (save1.equals("Saved")){
                    font44.draw(batch,"Save 1",WIDTH/2 - 63,HEIGHT/2 + 50);
                }

                if (save2.equals("Saved")){
                    font44.draw(batch,"Save 2",WIDTH/2 - 63,HEIGHT/2);
                }

                if (save3.equals("Saved")){
                    font44.draw(batch,"Save 3",WIDTH/2 - 63,HEIGHT/2 - 50);
                }

                if (save1.equals("") && save2.equals("") && save3.equals("")) {
                    font44.draw(batch,"New Save",WIDTH/2 - 100,HEIGHT/2);
                    buttonCentered = true;
                }else {
                    font44.draw(batch,"New Save",WIDTH/2 - 100,HEIGHT/2 - 100);
                }

                if (save1.equals("Saved") && save2.equals("Saved") && save3.equals("Saved")) {
                    font28.draw(batch,"By Saving, You'll be overwriting the oldest save you have.",WIDTH/4 - 120,HEIGHT/2 - 160);
                }

                if (showWhereSaved){
                    font28.draw(batch,"Game Saved to Save " + CurrentSaveNum ,WIDTH/2 - 140,HEIGHT/2 - 200);
                }
                batch.end();

                // Handles user input on the save menu
                saveMenuInput(prefs,prefs2,prefs3,buttonCentered);
                break;
            case Running:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                    state = State.Paused;
                }

                String debug = "";

                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                /*
                 * If the game has started, start incrementing time.
                 */
                totalDeltaTime += started ? deltaTime : 0;

                /*
                 * Check whether obstacles need to be spawned, and spawns them if so. Breaks
                 * instantly if the game hasn't started, if the player has finished, or if there
                 * are no more obstacles to be spawned.
                 *
                 * - IMPORTANT -
                 * It should be noted that the obstacles currently use a
                 * coordinate system relative to the screen, as they are always spawned at
                 * HEIGHT + 40 (y = 760). This means all collision checking methods need to be
                 * passed backgroundOffset to translate the object's y position.
                 */
                for (int i = 0; i < course.getNoLanes(); i++) {
                    if (!started || player.finished() || this.game.obstacleTimes[i].size() == 0)
                        break;
                    if (this.game.obstacleTimes[i].get(0) - player.getY() + player.getHeight() < 1) {
                        // new added rock
                        String[] obstacleTypes = {"Goose", "LogBig", "LogSmall", "Rock"};

                        // spawn an obstacle in lane i.
                        int xCoord = lanes[i].getLeftBoundary()
                                + rnd.nextInt(lanes[i].getRightBoundary() - lanes[i].getLeftBoundary() - 15);
                        lanes[i].SpawnObstacle(xCoord, HEIGHT + 40, obstacleTypes[rnd.nextInt(obstacleTypes.length)]);
                        // make sure obstacle is only spawned once.
                        this.game.obstacleTimes[i].remove(0);
                    }
                    if (!(this.game.powerUpTimes[i].size() == 0)) {
                        if (this.game.powerUpTimes[i].get(0) - player.getY() + player.getHeight() < 1) {
                            String[] powerUpTypes = {"Invincibility", "Maneuverability", "Repair", "SpeedBoost", "TimeReduction"};
                            int xCoord = lanes[i].getLeftBoundary()
                                    + rnd.nextInt(lanes[i].getRightBoundary() - lanes[i].getLeftBoundary() - 15);
                            lanes[i].SpawnPowerUp(xCoord, HEIGHT + 40, powerUpTypes[rnd.nextInt(powerUpTypes.length)]);
                            this.game.powerUpTimes[i].remove(0);
                        }
                    }
                }

                /*
                 * Move player. Advance animation frame.
                 */
                player.GetInput();
                player.MoveForward();
                if (player.getCurrentSpeed() > 0 && !started) {
                    // detect start of game (might change this to a countdown)
                    started = true;
                    // detects if the game is being loaded
                    if (!game.save) {
                        progressBar.StartTimer();
                        game.save = false;
                    }
                }
                if (player.getY() % 5 == 2)
                    player.AdvanceTextureFrame();

                /*
                 * Move opponents. Advance animation frame.
                 */
                for (Opponent o : opponents) {
                    if (!started) {
                        break;
                    }
                    o.MoveForward();
                    o.CheckCollisions(backgroundOffset);

                    if (Math.round(totalDeltaTime) % 2 == 0) {
                        o.ai(backgroundOffset, game.level);
                    }
                    if (o.getY() % 5 == 2)
                        o.AdvanceTextureFrame();
                }

                /*
                 * Increase the background offset so the player is centered.
                 */
                if (player.getY() + HEIGHT / 2 + player.getHeight() / 2 > course.getTexture().getHeight()) {
                    // Stop increasing the background offset when the player reaches the end of the
                    // course.
                } else if (player.getY() + player.getHeight() / 2 > HEIGHT / 2) {
                    // Start increasing the background offset when the player is above half the
                    // window height.
                    backgroundOffset = player.getY() + player.getHeight() / 2 - HEIGHT / 2;
                }

                player.CheckCollisions(backgroundOffset);

                //new
                if (player.getDurability() <= 0) {
                    game.endGame();
                }

                /*
                 * Display background.
                 */
                batch.begin();
                batch.draw(background, 0, 0, 0, background.getHeight() - HEIGHT - backgroundOffset, WIDTH, HEIGHT);
                batch.end();

                /*
                 * Display and move obstacles.
                 */
                for (Lane lane : lanes) {
                    if (!started)
                        break;
                    for (int j = 0; j < lane.obstacles.size(); j++) {
                        Obstacle o = lane.obstacles.get(j);
                        // If the background hasn't started moving yet, or if the player has reached the
                        // top of the course, move obstacle at set speed.
                        // Else add the player speed to the obstacle speed.
                        if (o instanceof Goose) {
                            Goose g = (Goose) o;
                            g.Move(0.4f + (backgroundOffset > 0
                                            && player.getY() + HEIGHT / 2 + player.getHeight() / 2 < course.getTexture().getHeight()
                                            ? player.getCurrentSpeed()
                                            : 0),
                                    backgroundOffset,
                                    game.level);
                        }
                        else {
                            o.Move(0.4f + (backgroundOffset > 0
                                            && player.getY() + HEIGHT / 2 + player.getHeight() / 2 < course.getTexture().getHeight()
                                            ? player.getCurrentSpeed()
                                            : 0),
                                    backgroundOffset);
                        }


                        if (o.getY() < -o.getHeight()) {
                            lane.RemoveObstacle(o);
                        }
                        batch.begin();
                        batch.draw(o.getTexture(), o.getX(), o.getY());
                        batch.end();
                    }

                    for (int j = 0; j < lane.powerUps.size(); j++) {
                        PowerUp p = lane.powerUps.get(j);
                        // If the background hasn't started moving yet, or if the player has reached the
                        // top of the course, move power-up at set speed.
                        // Else add the player speed to the power-up speed.
                        p.Move(0.4f + (backgroundOffset > 0
                                        && player.getY() + HEIGHT / 2 + player.getHeight() / 2 < course.getTexture().getHeight()
                                        ? player.getCurrentSpeed()
                                        : 0),
                                backgroundOffset);
                        if (p.getY() < -p.getHeight()) {
                            lane.RemovePowerUp(p);
                        }
                        batch.begin();
                        batch.draw(p.getMysteryTexture(), p.getX(), p.getY());
                        batch.end();
                    }
                }

                /*
                 * Display player and player UI.
                 */
                batch.begin();
                batch.draw(player.texture, player.getX(), player.getY() - backgroundOffset);
                batch.draw(staminaBarEmpty, player.lane.getLeftBoundary(), player.getY() - 20 - backgroundOffset);
                batch.draw(healthBarEmpty, player.lane.getLeftBoundary(), player.getY() - 40 - backgroundOffset);
                batch.draw(staminaBarFull, player.lane.getLeftBoundary(), player.getY() - 20 - backgroundOffset, 0, 0,
                        Math.round(staminaBarFull.getWidth() * player.getTiredness() / MAX_TIREDNESS),
                        staminaBarFull.getHeight());
                batch.draw(healthBarFull, player.lane.getLeftBoundary(), player.getY() - 40 - backgroundOffset, 0, 0,
                        Math.round(healthBarFull.getWidth() * player.getDurability() / MAX_DURABILITY),
                        healthBarFull.getHeight());
                batch.end();

                if (debug_positions)
                    debug += player.getName() + " pos: (" + player.getX() + "," + player.getY() + ")\n";
                if (debug_speed)
                    debug += player.getName() + " speed: " + player.getCurrentSpeed() + " / " + player.getMaxSpeed() + "\n\n";

                /*
                 * Display opponents.
                 */
                for (Opponent o : opponents) {
                    batch.begin();
                    batch.draw(o.texture, o.getX(), o.getY() - backgroundOffset);
                    batch.end();
                    if (debug_positions) debug += o.getName() + " pos: (" + o.getX() + "," + o.getY() + ")\n";
                    if (debug_speed)
                        debug += o.getName() + " speed: " + o.getCurrentSpeed() + " / " + o.getMaxSpeed() + "\n\n";
                }

                /*
                 * Display progress bar.
                 */
                batch.begin();
                batch.draw(progressBar.getTexture(), WIDTH - progressBar.getTexture().getWidth() - 60,
                        HEIGHT - progressBar.getTexture().getHeight() - 20);

                batch.draw(powerUpEmpty, WIDTH - powerUpEmpty.getWidth() - 100,
                        HEIGHT - powerUpEmpty.getHeight() - 85);

                if (!(player.boatPowerUps[0] == null)) {
                    player.boatPowerUps[0].update(deltaTime);batch.draw(player.boatPowerUps[0].getTexture(), WIDTH - powerUpEmpty.getWidth() - 100,
                            HEIGHT - powerUpEmpty.getHeight() - 85);
                }


                batch.draw(powerUpEmpty, WIDTH - powerUpEmpty.getWidth() - 60,
                        HEIGHT - powerUpEmpty.getHeight() - 85);

                if (!(player.boatPowerUps[1] == null)) {
                    player.boatPowerUps[1].update(deltaTime);batch.draw(player.boatPowerUps[1].getTexture(), WIDTH - powerUpEmpty.getWidth() - 60,
                            HEIGHT - powerUpEmpty.getHeight() - 85);
                }

                batch.end();

                /*
                 * Get progress for each boat. Draw player and opponent icons on progress bar
                 * with x coordinates respective to their progress.
                 */
                float[] progress = progressBar.getProgress(course.getTexture().getHeight());
                for (int i = 1; i < progress.length; i++) {
                    batch.begin();
                    batch.draw(progressBar.getOpponentIcon(),
                            WIDTH - progressBar.getTexture().getWidth() - 50
                                    + progress[i] * (progressBar.getTexture().getWidth() - 214),
                            HEIGHT - progressBar.getTexture().getHeight() / 2 - 10);
                    batch.end();
                }

                batch.begin();
                batch.draw(progressBar.getPlayerIcon(),
                        WIDTH - progressBar.getTexture().getWidth() - 50
                                + progress[0] * (progressBar.getTexture().getWidth() - 214),
                        HEIGHT - progressBar.getTexture().getHeight() / 2 - 10);
                batch.end();

                /*
                 * Check if player and each opponent has finished, and update their finished
                 * booleans respectively.
                 */
                if (progress[0] == 1 && !player.finished()) {
                    player.setFinished(true);
                    player.UpdateFastestTime(progressBar.getPlayerTime());
                }
                for (int i = 0; i < opponents.length; i++) {
                    if (progress[i + 1] == 1 && !opponents[i].finished()) {
                        opponents[i].setFinished(true);
                        opponents[i].UpdateFastestTime(progressBar.getTime());
                    }
                }

                /*
                 * Display player time.
                 */
                progressBar.IncrementTimer(deltaTime);
                batch.begin();
                font28.draw(batch, started ? progressBar.getPlayerTimeString() : "", WIDTH - 230, HEIGHT - 40);
                batch.end();

                /*
                 * Check player boat is in their lane, if not apply penalties.
                 */
                if (!player.CheckIfInLane() && !player.finished()) {
                    player.applyPenalty(penalty);
                    font28.setColor(Color.RED);
                    batch.begin();
                    font28.draw(batch, "Warning! Penalty applied for leaving lane", 240, 100);
                    batch.end();
                    font28.setColor(Color.WHITE);
                }
                /*
                 * Check opponent boats are in their lanes, if not apply penalties.
                 */
                for (Opponent opponent : opponents) {
                    if (!opponent.CheckIfInLane() && !opponent.finished()) {
                        opponent.applyPenalty(penalty);
                    }
                }

                /*
                 * Display debug stats.
                 */
                if (debug_positions || debug_speed) {
                    batch.begin();
                    font18.draw(batch, debug, 5, HEIGHT - 60);
                    batch.end();
                }

                if (debug_verboseoutput) {
                    System.out.println("-----------------------");
                    System.out.println("Total time: " + totalDeltaTime + "\nDelta time: " + deltaTime);
                    System.out.println("-----------------------");
                    System.out.println(" -- Variables --\n"
                            + "backgroundOffset: " + backgroundOffset);
                    for (int i = 0; i < lanes.length; i++) {
                        System.out.println("Lane " + i + " obstacles: " + lanes[i].obstacles.size());
                    }
                    System.out.println("\n\n\n");
                }


                /*
                 * Check if all boats have passed the finish line, if so, generate the
                 * leaderboard.
                 */
                if (progressBar.allFinished(course.getTexture().getHeight()) || (game.difficulty == 4 && player.finished())) {
                    // Display leaderboard, if on the third leg, display top 3 boats.
                    if (game.difficulty < 3) {
                        batch.begin();
                        batch.draw(leaderboard.getTexture(), WIDTH / 2 - leaderboard.getTexture().getWidth() / 2,
                                HEIGHT / 2 - leaderboard.getTexture().getHeight() / 2);
                        batch.end();
                        this.times = leaderboard.getTimes(opponents.length + 1);
                        for (int i = 0; i < opponents.length + 1; i++) {
                            batch.begin();
                            font44.draw(batch, this.times[i], WIDTH / 2 - leaderboard.getTexture().getWidth() / 3,
                                    620 - (75 * i));
                            batch.end();
                        }
                        batch.begin();
                        font28.draw(batch, "Click anywhere to progress to next leg.", 200, 40);
                        batch.end();
                        /*
                         * Defines how to handle keyboard and mouse inputs.
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
                        batch.begin();
                        batch.draw(leaderboard.getTexture(), WIDTH / 2 - leaderboard.getTexture().getWidth() / 2,
                                HEIGHT / 2 - leaderboard.getTexture().getHeight() / 2);
                        batch.end();
                        this.times = leaderboard.getTimes(opponents.length + 1);
                        for (int i = 0; i < opponents.length + 1; i++) {
                            if (i < 3)
                                font44.setColor(Color.GOLD);
                            else
                                font44.setColor(Color.WHITE);

                            batch.begin();
                            font44.draw(batch, this.times[i], WIDTH / 2 - leaderboard.getTexture().getWidth() / 3,
                                    620 - (75 * i));
                            batch.end();
                        }
                        if (this.times[0].startsWith("Player") || this.times[1].startsWith("Player")
                                || this.times[2].startsWith("Player")) {
                            batch.begin();
                            font28.draw(batch, "Click anywhere to progress to the final!", 200, 40);
                            batch.end();
                            /*
                             * Defines how to handle keyboard and mouse inputs.
                             */
                            Gdx.input.setInputProcessor(new InputAdapter() {
                                @Override
                                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                                    game.advanceLeg();
                                    Gdx.input.setInputProcessor(null);
                                    return super.touchUp(screenX, screenY, pointer, button);
                                }
                            });
                        } else {
                            game.endGame();
                        }
                    } else {
                        game.endGame();
                    }
                }
        }
    }

    /**
     * Resizes the game screen.
     *
     * @param width  Width of the screen.
     * @param height Height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
        state = State.Paused;

    }

    @Override
    public void resume() {
        state = State.Running;
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
            // new
            for (int k = 0; k < lane.powerUps.size(); k++) {
                lane.powerUps.get(k).getTexture().dispose();
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

    /**
     * Defines how to handle mouse inputs for the pause menu.
     */
    public void pauseMenuInput() {
        Gdx.input.setInputProcessor(new InputAdapter(){
            /**
             * Used to receive input events from the mouse.
             *
             * @param screenX X-position of the cursor.
             * @param screenY Y-position of the cursor (top left is 0,0).
             * @param pointer Pointer object.
             * @param button  Number representing mouse button clicked (0 = left click, 1 =
             *                right click, 2 = middle mouse button, etc.).
             * @return The output of touchUp(...), a boolean representing whether the input
             *         was processed (unused in this scenario).
             * @see InputAdapter
             */
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                int screenHeight = viewport.getScreenHeight();
                int screenWidth = viewport.getScreenWidth();
                if (screenX >= (0.46 * screenWidth) && screenX <= (0.55 * screenWidth)) {
                    if (screenY >= (0.5 * screenHeight) && screenY <= (0.54 * screenHeight)){
                        state = State.Running;
                    }
                    if (screenY >= (0.55 * screenHeight) && screenY <= (0.59 * screenHeight)){
                        state = State.Saving;
                    }
                    if (screenY >= (0.61 * screenHeight) && screenY <= (0.64 * screenHeight)){
                        Gdx.app.exit();
                    }
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }

    /**
     * Defines how to handle mouse inputs for the save menu.
     * @param prefs A Preferences instance that connects to the first save slot
     * @param prefs2 A Preferences instance that connects to the second save slot
     * @param prefs3 A Preferences instance that connects to the third save slot
     * @param buttonCentered A boolean that indicates if the save button is centered.
     */
    public void saveMenuInput(final Preferences prefs, final Preferences prefs2,final Preferences prefs3, final boolean buttonCentered) {
        final boolean[] saveButtonPressed = {false};
        Gdx.input.setInputProcessor(new InputAdapter() {
            /**
             * Used to receive input events from the mouse.
             *
             * @param screenX X-position of the cursor.
             * @param screenY Y-position of the cursor (top left is 0,0).
             * @param pointer Pointer object.
             * @param button  Number representing mouse button clicked (0 = left click, 1 =
             *                right click, 2 = middle mouse button, etc.).
             * @return The output of touchUp(...), a boolean representing whether the input
             * was processed (unused in this scenario).
             * @see InputAdapter
             */
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int screenHeight = viewport.getScreenHeight();
                int screenWidth = viewport.getScreenWidth();
                System.out.println(screenX);
                System.out.println(screenY);
                if (screenX >= (0.4 * screenWidth) && screenX <= (0.6 * screenWidth)) {
                    if (screenY >= (0.63 * screenHeight) && screenY <= (0.69 * screenHeight) && !buttonCentered){
                        saveButtonPressed[0] = true;
                    }
                    if (screenY >= (0.49 * screenHeight) && screenY <= (0.55 * screenHeight) && buttonCentered){
                        saveButtonPressed[0] = true;
                    }
                }
                /*
                 * Checks which slot is free and uses it.
                 * If no slots are available, then overwrites
                 * the oldest slot.
                 */
                if (saveButtonPressed[0]){
                    // The file where the current save will be
                    Preferences SaveFile;

                    // A save log which tells us what the oldest file is
                    Preferences SaveLog = Gdx.app.getPreferences("SaveLog");
                    // The current number of oldest file as stored in SaveLog
                    Integer SaveLogNum = SaveLog.getInteger("OldestFile",1);

                    // A tracker used to save the number of the oldest file in the save log
                    Integer oldestFile;

                    if (prefs.getString("Save").equals("")){
                        SaveFile = prefs;
                        oldestFile = 1;
                        CurrentSaveNum = 1;
                    }else if (prefs2.getString("Save").equals("")){
                        SaveFile = prefs2;
                        oldestFile = 1;
                        CurrentSaveNum = 2;
                    }else if (prefs3.getString("Save").equals("")){
                        SaveFile = prefs3;
                        oldestFile = 1;
                        CurrentSaveNum = 3;
                    }else {

                        if (SaveLogNum == 1){
                            SaveFile = prefs;
                            oldestFile = 2;
                            CurrentSaveNum = 1;
                        }else if (SaveLogNum == 2){
                            oldestFile = 3;
                            SaveFile = prefs2;
                            CurrentSaveNum = 2;
                        }else{
                            SaveFile = prefs3;
                            oldestFile = 1;
                            CurrentSaveNum = 3;
                        }
                    }

                    // Clears any old data
                    SaveFile.clear();

                    // Indicates that the file is a game save
                    SaveFile.putString("Save","Saved");
                    // Game Parameters
                    SaveFile.putInteger("difficulty",game.difficulty);
                    SaveFile.putString("level",game.level);

                    //Player Data
                    SaveFile.putInteger("playerChoice",game.playerChoice);
                    SaveFile.putFloat("playerXPos",player.xPosition);
                    SaveFile.putFloat("playerYPos",player.yPosition);
                    SaveFile.putFloat("playerPenalties",player.penalties);
                    SaveFile.putFloat("playerFastestTime",player.getFastestTime());
                    SaveFile.putInteger("playerDurability",player.getDurability());
                    SaveFile.putInteger("playerRobustness",player.getRobustness());
                    SaveFile.putFloat("playerCurrentSpeed",player.getCurrentSpeed());
                    SaveFile.putFloat("playerAcceleration",player.getAcceleration());
                    SaveFile.putFloat("playerManeuverability",player.getManeuverability());
                    SaveFile.putFloat("playerTiredness",player.getTiredness());
                    // Player PowerUp Data
                    for (int i = 0; i < player.boatPowerUps.length; i++) {
                        if (player.boatPowerUps[i] != null){
                            SaveFile.putString(i + "playerPowerUpObstacleType", player.boatPowerUps[i].obstacleType);
                            SaveFile.putFloat(i + "playerPowerUpXPos", player.boatPowerUps[i].xPosition);
                            SaveFile.putFloat(i + "playerPowerUpYPos", player.boatPowerUps[i].yPosition);
                        }
                    }

                    //Progress Bar Data
                    SaveFile.putFloat("PlayerTime", progressBar.getPlayerTime());
                    SaveFile.putFloat("timeSeconds", progressBar.getTime());

                    // Opponent Data
                    SaveFile.putInteger("numberOfOpponents",opponents.length);
                    for (int i = 0; i< opponents.length; i++){
                        SaveFile.putFloat("opponent" + i +"XPos",opponents[i].xPosition);
                        SaveFile.putFloat("opponent" + i +"YPos",opponents[i].yPosition);
                        SaveFile.putFloat("opponent" + i +"Penalties",opponents[i].penalties);
                        SaveFile.putFloat("opponent" + i + "FastestTime",opponents[i].getFastestTime());
                        SaveFile.putInteger("opponent" + i +"Durability",opponents[i].getDurability());
                        SaveFile.putInteger("opponent" + i +"Robustness",opponents[i].getRobustness());
                        SaveFile.putFloat("opponent" + i +"CurrentSpeed",opponents[i].getCurrentSpeed());
                        SaveFile.putFloat("opponent" + i +"Acceleration",opponents[i].getAcceleration());
                        SaveFile.putFloat("opponent" + i +"Maneuverability",opponents[i].getManeuverability());
                        SaveFile.putFloat("opponent" + i +"Tiredness",opponents[i].getTiredness());
                        // Opponent PowerUp Data
                        for (int x = 0; x < opponents[i].boatPowerUps.length; x++) {
                            if (opponents[i].boatPowerUps[x] != null){
                                SaveFile.putString(i + "." + x + "opponentPowerUpObstacleType", opponents[i].boatPowerUps[x].obstacleType);
                                SaveFile.putFloat(i + "." + x + "opponentPowerUpXPos", opponents[i].boatPowerUps[x].xPosition);
                                SaveFile.putFloat(i + "." + x +"opponentPowerUpYPos", opponents[i].boatPowerUps[x].yPosition);
                            }
                        }
                    }

                    // Obstacles on Lane Data
                    Json json = new Json();
                    SaveFile.putString("obstacleTimes",json.toJson(game.obstacleTimes,game.obstacleTimes.getClass()));
                    SaveFile.putString("powerUpTimes",json.toJson(game.powerUpTimes,game.powerUpTimes.getClass()));
                    for (int i = 0; i < lanes.length; i++){
                        SaveFile.putInteger(i + "numberOfObstacles",lanes[i].obstacles.size());
                        for (int x = 0; x < lanes[i].obstacles.size(); x++){
                            Obstacle obstacle = lanes[i].obstacles.get(x);
                            SaveFile.putString(i + "." + x + "ObstacleType",obstacle.obstacleType);
                            SaveFile.putFloat(i + "." + x + "ObstacleXPosition",obstacle.xPosition);
                            SaveFile.putFloat(i + "." + x + "ObstacleYPosition",obstacle.yPosition);
                        }
                        // PowerUp on Lane Data
                        SaveFile.putInteger(i + "numberOfPowerUps",lanes[i].powerUps.size());
                        for (int x = 0; x < lanes[i].powerUps.size(); x++){
                            Obstacle powerUp = lanes[i].powerUps.get(x);
                            SaveFile.putString(i + "." + x + "PowerUpType",powerUp.obstacleType);
                            SaveFile.putFloat(i + "." + x + "PowerUpXPosition",powerUp.xPosition);
                            SaveFile.putFloat(i + "." + x + "PowerUpYPosition",powerUp.getY());
                        }
                    }

                    SaveFile.flush();

                    SaveLog.putInteger("OldestFile",oldestFile);
                    System.out.println(oldestFile);
                    showWhereSaved = true;
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }
}

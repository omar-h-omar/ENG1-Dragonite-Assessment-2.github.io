package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Screen class for the Menu Screen. Allows the user to select a Boat, and shows
 * the controls of the game. Once the user clicks within set boundaries, the
 * game starts within GameScreen.
 *
 * @see GameScreen
 * @see Screen
 */
public class WelcomeScreen implements Screen {
    //graphics
    private Texture background;
    private final FreeTypeFontGenerator generator;
    private final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private final BitmapFont font18, font28, font44;
    private final SpriteBatch batch;

    // global parameters
    private final int WIDTH = 1080, HEIGHT = 720;
    final DragonBoatGame game;

    // used for setting boundaries for mouse clicks
    private final OrthographicCamera camera;
    private final Viewport viewport;

    /**
     * Presents the player with an option to start a new game or load the previous one.
     * Creates an Input Processor to listen for a mouse click within set boundaries.
     *
     * @param Game represents the initial state of DragonBoatGame.
     * @see com.badlogic.gdx.InputProcessor
     */
    public WelcomeScreen(DragonBoatGame Game) {
        game = Game;

        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("end screen.png"));
        generator = game.generator;
        parameter = game.parameter;
        parameter.size = 18;
        font18 = generator.generateFont(parameter);
        parameter.size = 28;
        font28 = generator.generateFont(parameter);
        parameter.size = 44;
        font44 = generator.generateFont(parameter);

        final WelcomeScreen welcomeScreen = this;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(1080, 720, camera);

        /*
         * Defines how to handle mouse inputs.
         */
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
             *         was processed (unused in this scenario).
             * @see InputAdapter
             */
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                /*
                 * First check whether the cursor is in right x-bounds, as these are all the
                 * same for all boats.
                 */
                int screenHeight = viewport.getScreenHeight();
                int screenWidth = viewport.getScreenWidth();
                if (screenX >= (0.386 * screenWidth) && screenX <= (0.60 * screenWidth)) {
                    /*
                     * Then check if the mouse is in each set of y-bounds, if so, start
                     * a new game or load the previous game.
                     *
                     */
                    if (screenY >= (0.5 * screenHeight) && screenY <= (0.55 * screenHeight)){
                        MenuScreen menuScreen = new MenuScreen(game);
//                        welcomeScreen.dispose();
                        game.setScreen(menuScreen);
                    }
                    if (screenY >= (0.58 * screenHeight) && screenY <= (0.64 * screenHeight)){
                        loadSave();
                    }
                }
                if (screenY >= (0.551 * screenHeight) && screenY <= (0.91 * screenHeight)) {

                    if (screenX >= (0.041 * screenWidth) && screenX <= (0.1638 * screenWidth)) {
                        game.player.ChooseBoat(0);
                        game.playerChoice = 0;
                        welcomeScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }

    /**
     * Rendering function for the menu screen.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        font44.draw(batch,"New Game",WIDTH/2 - 120,HEIGHT/2);
        font44.draw(batch,"Load Game",WIDTH/2 - 120,HEIGHT/2 - 60);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
    public void show() {

    }

    /**
     * Disposes of the screen when it is no longer needed.
     */
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        background.dispose();
        font18.dispose();
        font28.dispose();
        font44.dispose();
        batch.dispose();
    }

    /**
     * Loads the old game save and starts the game.
     */
    public void loadSave() {
        game.save = true;
        Preferences prefs = Gdx.app.getPreferences("GameSave");

        // Loads Game Parameters
        Integer difficulty = prefs.getInteger("difficulty");

        // Loads Player Data
        Integer playerChoice = prefs.getInteger("playerChoice");
        Float playerXPos = prefs.getFloat("playerXPos");
        Float playerYPos = prefs.getFloat("playerYPos");
        Float playerPenalties = prefs.getFloat("playerPenalties");

        // Loads Progress Bar Data
        Float playerTime = prefs.getFloat("PlayerTime");
        Float timeSeconds = prefs.getFloat("timeSeconds");

        // Loads and Sets Opponent Data
        Integer opponentCount = prefs.getInteger("numberOfOpponents");
        for (int i = 0; i < opponentCount; i++) {
            game.opponents[i].xPosition = prefs.getFloat("opponent" + i +"XPos");
            game.opponents[i].yPosition = prefs.getFloat("opponent" + i +"YPos");
            game.opponents[i].penalties = prefs.getFloat("opponent" + i +"Penalties");
            game.opponents[i].UpdateFastestTime(prefs.getFloat("opponent" + i +"FastestTime"));
            game.opponents[i].setDurability(prefs.getInteger("opponent" + i +"Durability"));
            game.opponents[i].setROBUSTNESS(prefs.getInteger("opponent" + i +"Robustness"));
            game.opponents[i].setCurrentSpeed(prefs.getFloat("opponent" + i +"CurrentSpeed"));
            game.opponents[i].setACCELERATION(prefs.getFloat("opponent" + i +"Acceleration"));
            game.opponents[i].setMANEUVERABILITY(prefs.getFloat("opponent" + i +"Maneuverability"));
            game.opponents[i].setTiredness(prefs.getFloat("opponent" + i +"Tiredness"));
            // Opponent PowerUp Data
            for (int x = 0; x < 2; x++) {
                String powerUpType = prefs.getString(i + "." + x + "opponentPowerUpObstacleType");
                Float powerUpXPos = prefs.getFloat(i + "." + x + "opponentPowerUpXPos");
                Float powerUpYPos = prefs.getFloat(i + "." + x +"opponentPowerUpYPos");
                if (powerUpType.equals("Invincibility")) {
                    Invincibility invincibility = new Invincibility(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.lanes[i]);
                    game.opponents[i].boatPowerUps[x] = invincibility;
                }
                else if (powerUpType.equals("Maneuverability")) {
                    Maneuverability maneuverability = new Maneuverability(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.lanes[i]);
                    game.opponents[i].boatPowerUps[x] = maneuverability;
                }
                else if (powerUpType.equals("Repair")) {
                    Repair repair = new Repair(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.lanes[i]);
                    game.opponents[i].boatPowerUps[x] = repair;
                }
                else if (powerUpType.equals("SpeedBoost")) {
                    SpeedBoost speedBoost = new SpeedBoost(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.lanes[i]);
                    game.opponents[i].boatPowerUps[x] = speedBoost;
                }
                else if (powerUpType.equals("TimeReduction")) {
                    TimeReduction timeReduction = new TimeReduction(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.lanes[i]);
                    game.opponents[i].boatPowerUps[x] = timeReduction;
                }
            }
        }

        /*
         * Checks if only 2 opponents are left and
         * assigns the correct lanes to them.
         */
        if (opponentCount == 2) {
            Opponent[] opponentsList = new Opponent[2];
            game.opponents[0].lane = game.opponents[2].lane;
            game.opponents[1].lane = game.opponents[3].lane;
            opponentsList[0] = game.opponents[0];
            opponentsList[1] = game.opponents[1];
            game.opponents = opponentsList;
        }

        // Sets Game Parameters
        game.difficulty = difficulty;

        // Sets Player Data
        game.player.ChooseBoat(playerChoice);
        game.playerChoice = playerChoice;
        game.player.xPosition = playerXPos;
        game.player.yPosition = playerYPos;
        game.player.penalties = playerPenalties;
        game.player.UpdateFastestTime(prefs.getFloat("playerFastestTime"));
        game.player.setDurability(prefs.getInteger("playerDurability"));
        game.player.setROBUSTNESS(prefs.getInteger("playerRobustness"));
        game.player.setCurrentSpeed(prefs.getFloat("playerCurrentSpeed"));
        game.player.setACCELERATION(prefs.getFloat("playerAcceleration"));
        game.player.setMANEUVERABILITY(prefs.getFloat("playerManeuverability"));
        game.player.setTiredness(prefs.getFloat("playerTiredness"));
        // Player PowerUp Data
        for (int i = 0; i < 2; i++) {
            String powerUpType = prefs.getString(i+"playerPowerUpObstacleType");
            Float powerUpXPos = prefs.getFloat(i + "playerPowerUpXPos");
            Float powerUpYPos = prefs.getFloat(i + "playerPowerUpYPos");
            if (powerUpType.equals("Invincibility")) {
                Invincibility invincibility = new Invincibility(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.player.lane);
                game.player.boatPowerUps[i] = invincibility;
            }
            else if (powerUpType.equals("Maneuverability")) {
                Maneuverability maneuverability = new Maneuverability(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.player.lane);
                game.player.boatPowerUps[i] = maneuverability;
            }
            else if (powerUpType.equals("Repair")) {
                Repair repair = new Repair(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.player.lane);
                game.player.boatPowerUps[i] = repair;
            }
            else if (powerUpType.equals("SpeedBoost")) {
                SpeedBoost speedBoost = new SpeedBoost(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.player.lane);
                game.player.boatPowerUps[i] = speedBoost;
            }
            else if (powerUpType.equals("TimeReduction")) {
                TimeReduction timeReduction = new TimeReduction(Math.round(powerUpXPos), Math.round(powerUpYPos), new Texture(Gdx.files.internal("itemBox.png")), game.player.lane);
                game.player.boatPowerUps[i] = timeReduction;
            }
        }

        // Sets Progress Bar Data
        game.progressBar.setTimeSeconds(timeSeconds);
        game.progressBar.setPlayerTime(playerTime);

        // Loads and Sets Obstacles and PowerUps on Lanes
        for (int i = 0; i < 7; i++){
            Integer numberOfObstacles = prefs.getInteger(i +"numberOfObstacles");
            Lane lane = game.lanes[i];
            for (int x = 0; x < numberOfObstacles; x++){
                String obstacleType = prefs.getString(i + "." + x + "ObstacleType");
                Float xPosition = prefs.getFloat(i + "." + x + "ObstacleXPosition");
                Float yPosition = prefs.getFloat(i + "." + x + "ObstacleYPosition");
                if (obstacleType.equals("Goose")){
                    lane.SpawnObstacle(Math.round(xPosition),Math.round(yPosition),"Goose");
                }
                if (obstacleType.equals("LogBig")){
                    lane.SpawnObstacle(Math.round(xPosition),Math.round(yPosition),"LogBig");
                }
                if (obstacleType.equals("LogSmall")){
                    lane.SpawnObstacle(Math.round(xPosition),Math.round(yPosition),"LogSmall");
                }
                if (obstacleType.equals("Rock")){
                    lane.SpawnObstacle(Math.round(xPosition),Math.round(yPosition),"Rock");
                }
            }

            Integer numberOfPowerUps = prefs.getInteger(i +"numberOfPowerUps");
            for (int x = 0; x < numberOfPowerUps; x++){
                String PowerUpType = prefs.getString(i + "." + x + "PowerUpType");
                Float xPosition = prefs.getFloat(i + "." + x + "PowerUpXPosition");
                Float yPosition = prefs.getFloat(i + "." + x + "PowerUpYPosition");
                if (PowerUpType.equals("Invincibility")){
                    lane.SpawnPowerUp(Math.round(xPosition),Math.round(yPosition),"Invincibility");
                }
                if (PowerUpType.equals("Maneuverability")){
                    lane.SpawnPowerUp(Math.round(xPosition),Math.round(yPosition),"Maneuverability");
                }
                if (PowerUpType.equals("Repair")){
                    lane.SpawnPowerUp(Math.round(xPosition),Math.round(yPosition),"Repair");
                }
                if (PowerUpType.equals("SpeedBoost")){
                    lane.SpawnPowerUp(Math.round(xPosition),Math.round(yPosition),"SpeedBoost");
                }
                if (PowerUpType.equals("TimeReduction")){
                    lane.SpawnPowerUp(Math.round(xPosition),Math.round(yPosition),"TimeReduction");
                }
            }
        }

        String obstacleTimes = prefs.getString("obstacleTimes");
        Json json = new Json();
        game.obstacleTimes = json.fromJson(game.obstacleTimes.getClass(),obstacleTimes);
        String powerUpTimes = prefs.getString("powerUpTimes");
        game.powerUpTimes = json.fromJson(game.powerUpTimes.getClass(),powerUpTimes);


        this.dispose();
        game.setScreen(new GameScreen(game));
    }

}
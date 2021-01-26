package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Screen class for the Menu Screen. Allows the user to select a Boat, and shows
 * the controls of the game. Once the user clicks within set boundaries, the
 * game starts within GameScreen.
 *
 * @see GameScreen
 * @see Screen
 */
public class MenuScreen implements Screen {
    //made this texture private for encapsulation
    private Texture startScreen;
    final DragonBoatGame game;
    private final SpriteBatch batch;
    // used for setting boundaries for mouse clicks
    private final OrthographicCamera camera;
    private final Viewport viewport;
    /**
     * Creates an Input Processor to listen for a mouse click within set boundaries.
     *
     * @param Game represents the initial state of DragonBoatGame.
     * @see com.badlogic.gdx.InputProcessor
     */
    public MenuScreen(DragonBoatGame Game) {
        game = Game;
        batch = new SpriteBatch();
        startScreen = new Texture(Gdx.files.internal("start screen w fade w controls.png"));
        final MenuScreen menuScreen = this;
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
                 * First check whether the cursor is in right y-bounds, as these are all the
                 * same for all boats.
                 */
                int screenHeight = viewport.getScreenHeight();
                int screenWidth = viewport.getScreenWidth();
                if (screenY >= (0.551 * screenHeight) && screenY <= (0.91 * screenHeight)) {
                    /*
                     * Then check if the mouse is in each set of x-bounds, if so, set the player
                     * boat to the corresponding boat, and initialise the game.
                     *
                     * - NOTE - These values don't work if the window is made to be resizable, and
                     * is then resized by the user.
                     */
                    if (screenX >= (0.041 * screenWidth) && screenX <= (0.1638 * screenWidth)) {
                        game.player.ChooseBoat(0);
                        game.playerChoice = 0;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= (0.173 * screenWidth) && screenX <= (0.296 * screenWidth)) {
                        game.player.ChooseBoat(1);
                        game.playerChoice = 1;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= (0.305 * screenWidth) && screenX <= (0.423 * screenWidth)) {
                        game.player.ChooseBoat(2);
                        game.playerChoice = 2;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= (0.438 * screenWidth) && screenX <= (0.561 * screenWidth)) {
                        game.player.ChooseBoat(3);
                        game.playerChoice = 3;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= (0.5703 * screenWidth) && screenX <= (0.694 * screenWidth)) {
                        game.player.ChooseBoat(4);
                        game.playerChoice = 4;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= (0.703 * screenWidth) && screenX <= (0.826 * screenWidth)) {
                        game.player.ChooseBoat(5);
                        game.playerChoice = 5;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= (0.835 * screenWidth) && screenX <= (0.958 * screenWidth)) {
                        game.player.ChooseBoat(6);
                        game.playerChoice = 6;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }

    private void btnLoginClicked() {
        game.setScreen(new GameScreen(game));
    }

    /**
     * Rendering function for the menu screen.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(startScreen, 0, 0);
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
        startScreen.dispose();
        batch.dispose();
    }


}

package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Screen class for the Menu Screen. Allows the user to select a Boat, and shows
 * the controls of the game. Once the user clicks within set boundaries, the
 * game starts within GameScreen.
 * 
 * @see GameScreen
 * @see Screen
 */
public class MenuScreen implements Screen {
    Texture startScreen;
    final DragonBoatGame game;
    private final SpriteBatch batch;

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
                if (screenY >= 397 && screenY <= 655) {
                    /*
                     * Then check if the mouse is in each set of x-bounds, if so, set the player
                     * boat to the corresponding boat, and initialise the game.
                     * 
                     * - NOTE - These values don't work if the window is made to be resizable, and
                     * is then resized by the user.
                     */
                    if (screenX >= 44 && screenX <= 177) {
                        game.player.ChooseBoat(0);
                        game.playerChoice = 0;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= 187 && screenX <= 320) {
                        game.player.ChooseBoat(1);
                        game.playerChoice = 1;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= 330 && screenX <= 463) {
                        game.player.ChooseBoat(2);
                        game.playerChoice = 2;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= 473 && screenX <= 606) {
                        game.player.ChooseBoat(3);
                        game.playerChoice = 3;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= 616 && screenX <= 749) {
                        game.player.ChooseBoat(4);
                        game.playerChoice = 4;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= 759 && screenX <= 892) {
                        game.player.ChooseBoat(5);
                        game.playerChoice = 5;
                        menuScreen.dispose();
                        game.setScreen(new GameScreen(game));
                    }
                    if (screenX >= 902 && screenX <= 1035) {
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

    /**
     * Rendering function for the menu screen.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(startScreen, 0, 0);
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

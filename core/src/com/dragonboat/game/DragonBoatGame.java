package com.dragonboat.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Game Class for Dragon Boat Game.
 * Initialises all the objects necessary for the game, starts music, creates Lanes, randomises Obstacle spawns,
 * initialises blank Player and Opponents, initialises a Progress Bar and Leaderboard, and instantiates a Menu Screen.
 * @see MenuScreen
 */
public class DragonBoatGame extends Game {
	private MenuScreen menuScreen;
	public Lane[] lanes;
	public Player player;
	public Course course;
	public Opponent[] opponents;
	public ProgressBar progressBar;
	public Leaderboard leaderboard;
	public ArrayList<Integer>[] obstacleTimes;
	public int noOfObstacles;
	public int playerChoice;
	public int difficulty = 1;
	public Music music;
	public boolean ended = false;
	public FreeTypeFontGenerator generator;
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private SpriteBatch batch;
	private BitmapFont font28;

	/**
	 * Sets up the game with settings and instantiation of objects
	 */
	@Override
	public void create () {
		int w = Gdx.graphics.getWidth() - 80;
		int h = Gdx.graphics.getHeight();
		Random rnd = new Random();

		music = Gdx.audio.newMusic(Gdx.files.internal("cantgobackwards.mp3"));
		music.setLooping(true);
		music.setVolume(0.4f);
		music.play();

		lanes = new Lane[7];
		noOfObstacles = 8;
		obstacleTimes = new ArrayList[lanes.length];
		for(int x = 0; x < lanes.length; x++) {
			obstacleTimes[x] = new ArrayList<Integer>();
			lanes[x] = new Lane((x*w/lanes.length) + 40, (((x+1)*w)/lanes.length) + 40);
			int maxY = (2880 - (5 * noOfObstacles))/noOfObstacles;
			for(int y = 0; y < noOfObstacles; y++) {
				obstacleTimes[x].add(rnd.nextInt(maxY - 5) + 5 + maxY * y);
			}
			Collections.sort(obstacleTimes[x]);
		}

		course = new Course(new Texture(Gdx.files.internal("core/assets/background sprite.png")), lanes);
		player = new Player(0,56, 182, lanes[3], "Player");

		opponents = new Opponent[6];
		for(int i = 0; i < opponents.length; i++) {
			int lane = i >= 3 ? i+1 : i;
			opponents[i] = new Opponent(0,56,182, lanes[lane], "Opponent" + (i+1));
		}

		progressBar = new ProgressBar(player, opponents);
		leaderboard = new Leaderboard(player, opponents);

		generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/8bitOperatorPlus-Regular.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 28;
		font28 = generator.generateFont(parameter);

		batch = new SpriteBatch();
		menuScreen = new  MenuScreen(this);
		setScreen(menuScreen);
	}

	/**
	 * Changes the screen to a new GameScreen and resets necessary attributes
	 */
	public void advanceLeg() {
		/**
		 * Increase difficulty and set up next leg.
		 */
		difficulty += 1;
		int w = Gdx.graphics.getWidth() - 80;
		int h = Gdx.graphics.getHeight();
		Random rnd = new Random();

		noOfObstacles = 8 * difficulty;
		obstacleTimes = new ArrayList[lanes.length];
		for(int x = 0; x < lanes.length; x++) {
			lanes[x].obstacles = new ArrayList<>();
			obstacleTimes[x] = new ArrayList<>();
			int maxY = (2880 - (5 * noOfObstacles))/noOfObstacles;
			for(int y = 0; y < noOfObstacles; y++) {
				obstacleTimes[x].add(rnd.nextInt(maxY - 5) + 5 + maxY * y);
			}
			Collections.sort(obstacleTimes[x]);
		}
		player.Reset();

		/**
		 * Set up final leg.
		 */
		if(difficulty==4) {
			Boat[] finalists = leaderboard.GetPodium();
			opponents = new Opponent[2];
			for(Boat b : finalists) {
				if(b.getName().startsWith("Opponent")) {
					// set opponents lanes so that only the middle 3 lanes are used.
					if(opponents[0] == null) {
						opponents[0] = (Opponent) b;
						b.setLane(lanes[2]);
					}
					else {
						opponents[1] = (Opponent) b;
						b.setLane(lanes[4]);
					}
				}
				b.ResetFastestLegTime();
			}
		}
		for(Opponent o : opponents) {
			o.Reset();
		}
		progressBar = new ProgressBar(player, opponents);
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		final DragonBoatGame game = this;
		if(!this.ended) {
			super.render();
		}
		else {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			boolean playerWon = false;
			batch.begin();
			batch.draw(new Texture(Gdx.files.internal("core/assets/end screen.png")),0,0);
			Boat[] podium = leaderboard.GetPodium();
			for(int i = 0; i < podium.length; i++) {
				if(podium[i].getName().startsWith("Player")) {
					playerWon = true;
					batch.draw(player.texture,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);
					switch(i) {
						case 0:
							batch.draw(new Texture(Gdx.files.internal("core/assets/medal gold.png")),Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
							break;
						case 1:
							batch.draw(new Texture(Gdx.files.internal("core/assets/medal silver.png")),Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
							break;
						case 2:
							batch.draw(new Texture(Gdx.files.internal("core/assets/medal bronze.png")),Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
							break;
					}
					font28.draw(batch, "Congratulations! You reached Super Saiyan!", 140, 140);
				}
			}
			if(!playerWon) {
				font28.draw(batch, "Unlucky, would you like to try again?", 140, 200);
			}
			batch.end();
		}
	}

	public void endGame() {
		this.ended = true;

	}
	/**
	 * Resizes the game screen
	 * @param width Width of the screen
	 * @param height Height of the screen
	 */
	@Override
	public void resize(int width, int height) {
		this.getScreen().resize(width,height);
	}

	/**
	 * Disposes of the current screen when it's no longer needed
	 */
	@Override
	public void dispose () {
		this.getScreen().dispose();
		batch.dispose();
		font28.dispose();

	}
}

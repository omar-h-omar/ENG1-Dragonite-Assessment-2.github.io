package com.dragonboat.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import com.badlogic.gdx.audio.Music;

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
	public ArrayList<Float>[] obstacleTimes;
	public int noOfObstacles;
	public int playerChoice;
	public int difficulty = 1;
	public Music music;

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
		music.setVolume(0.02f);
		music.play();

		lanes = new Lane[7];
		noOfObstacles = 10;
		obstacleTimes = new ArrayList[lanes.length];
		for(int x = 0; x < lanes.length; x++) {
			obstacleTimes[x] = new ArrayList<Float>();
			lanes[x] = new Lane((x*w/lanes.length) + 40, (((x+1)*w)/lanes.length) + 40);
			for(int y = 0; y < noOfObstacles; y++) {
				obstacleTimes[x].add(3 * (rnd.nextInt(y+1) + rnd.nextFloat()));
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
		menuScreen = new  MenuScreen(this);
		setScreen(menuScreen);
	}

	/**
	 * Changes the screen to a new GameScreen and resets necessary attributes
	 */
	public void advanceLeg() {
		difficulty += 1;
		int w = Gdx.graphics.getWidth() - 80;
		int h = Gdx.graphics.getHeight();
		Random rnd = new Random();

		noOfObstacles = 8 * difficulty;
		obstacleTimes = new ArrayList[lanes.length];
		for(int x = 0; x < lanes.length; x++) {
			obstacleTimes[x] = new ArrayList<Float>();
			for(int y = 0; y < noOfObstacles; y++) {
				obstacleTimes[x].add(4 * (rnd.nextFloat() + y/3 + 0.3f));
			}
			Collections.sort(obstacleTimes[x]);
		}
		player.Reset();
		for(Opponent o : opponents) {
			o.Reset();
		}
		progressBar = new ProgressBar(player, opponents);
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
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
	}
}

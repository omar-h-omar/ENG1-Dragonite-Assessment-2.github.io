package com.dragonboat.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import com.badlogic.gdx.audio.Music;

public class DragonBoatGame extends Game {
	private GameScreen gameScreen;
	public Lane[] lanes;
	public Player player;
	public Course course;
	public Opponent[] opponents;
	public ProgressBar progressBar;
	public Leaderboard leaderboard;
	public ArrayList<Float>[] obstacleTimes;
	public int noOfObstacles;
	public Music music;

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
				obstacleTimes[x].add(3 * (rnd.nextFloat() + y/3 + 0.3f));
			}
			Collections.sort(obstacleTimes[x]);
		}

		course = new Course(new Texture(Gdx.files.internal("core/assets/background sprite.png")), lanes);
		player = new Player(0,56, 182, lanes[3], "Player");
		player.setTexture(new Texture(Gdx.files.internal("core/assets/boatA sprite1.png")));
		player.setTextureFrames(generateTextureFrames('A'));
		player.SetStats(5,2,6f,3f);

		opponents = new Opponent[6];
		opponents[0] = new Opponent(0,56,182,lanes[0], "Opponent1");
		opponents[0].setTexture(new Texture(Gdx.files.internal("core/assets/boatB sprite1.png")));
		opponents[0].setTextureFrames(generateTextureFrames('B'));
		opponents[0].SetStats(3,4,2f,8f);

		opponents[1] = new Opponent(0,56,182,lanes[1], "Opponent2");
		opponents[1].setTexture(new Texture(Gdx.files.internal("core/assets/boatC sprite1.png")));
		opponents[1].setTextureFrames(generateTextureFrames('C'));
		opponents[1].SetStats(4,1,8f,3f);

		opponents[2] = new Opponent(0,56,182,lanes[2], "Opponent3");
		opponents[2].setTexture(new Texture(Gdx.files.internal("core/assets/boatD sprite1.png")));
		opponents[2].setTextureFrames(generateTextureFrames('D'));
		opponents[2].SetStats(4,4,4f,4f);

		opponents[3] = new Opponent(0,56,182,lanes[4], "Opponent4");
		opponents[3].setTexture(new Texture(Gdx.files.internal("core/assets/boatE sprite1.png")));
		opponents[3].setTextureFrames(generateTextureFrames('E'));
		opponents[3].SetStats(3,8,3f,2f);

		opponents[4] = new Opponent(0,56,182,lanes[5], "Opponent5");
		opponents[4].setTexture(new Texture(Gdx.files.internal("core/assets/boatF sprite1.png")));
		opponents[4].setTextureFrames(generateTextureFrames('F'));
		opponents[4].SetStats(8,3,1f,0.5f);

		opponents[5] = new Opponent(0,56,182,lanes[6], "Opponent6");
		opponents[5].setTexture(new Texture(Gdx.files.internal("core/assets/boatG sprite1.png")));
		opponents[5].setTextureFrames(generateTextureFrames('G'));
		opponents[5].SetStats(4,5,2f,5f);

		progressBar = new ProgressBar(player, opponents);
		leaderboard = new Leaderboard(player, opponents);
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	// All drawing is done in the GameScreen class.
	public Texture[] generateTextureFrames(char boatName) {
		Texture[] frames = new Texture[4];
		for(int i = 1; i <= frames.length; i++) {
			frames[i-1] = new Texture(Gdx.files.internal("core/assets/boat"+ boatName +" sprite"+ Integer.toString(i) +".png"));
		}
		return frames;
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		gameScreen.resize(width,height);
	}

	@Override
	public void dispose () {
		gameScreen.dispose();
	}
}

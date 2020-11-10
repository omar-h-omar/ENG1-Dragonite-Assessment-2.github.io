package com.dragonboat.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;

public class DragonBoatGame extends Game {
	private GameScreen gameScreen;
	public Lane[] lanes;
	public Player player;
	public Course course;
	public Opponent[] opponents;
	public ProgressBar progressBar;
	public float[][] obstacleTimes;
	public int noOfObstacles;

	@Override
	public void create () {
		int w = Gdx.graphics.getWidth() - 80;
		int h = Gdx.graphics.getHeight();
		Random rnd = new Random();


		lanes = new Lane[7];
		noOfObstacles = 15;
		obstacleTimes = new float[lanes.length][noOfObstacles];

		for(int x = 0; x < lanes.length; x++) {
			lanes[x] = new Lane((x*w/lanes.length) + 40, (((x+1)*w)/lanes.length) + 40);
			for(int y = 0; y < noOfObstacles; y++) {
				obstacleTimes[x][y] = 3 * (rnd.nextFloat() + y + 0.5f);
			}
		}

		course = new Course(new Texture(Gdx.files.internal("core/assets/background sprite.png")), lanes);
		player = new Player(0,56, 182, lanes[3], "Player");
		player.setTexture(new Texture(Gdx.files.internal("core/assets/boatA sprite1.png")));
		player.setStats(5,2,6f,3f);

		opponents = new Opponent[6];
		opponents[0] = new Opponent(0,56,182,lanes[0], "Opponent1");
		opponents[0].setTexture(new Texture(Gdx.files.internal("core/assets/boatB sprite1.png")));
		opponents[0].setStats(3,4,1f,8f);

		opponents[1] = new Opponent(0,56,182,lanes[1], "Opponent2");
		opponents[1].setTexture(new Texture(Gdx.files.internal("core/assets/boatC sprite1.png")));
		opponents[1].setStats(4,1,8f,3f);

		opponents[2] = new Opponent(0,56,182,lanes[2], "Opponent3");
		opponents[2].setTexture(new Texture(Gdx.files.internal("core/assets/boatD sprite1.png")));
		opponents[2].setStats(4,4,4f,4f);

		opponents[3] = new Opponent(0,56,182,lanes[4], "Opponent4");
		opponents[3].setTexture(new Texture(Gdx.files.internal("core/assets/boatE sprite1.png")));
		opponents[3].setStats(3,8,3f,2f);

		opponents[4] = new Opponent(0,56,182,lanes[5], "Opponent5");
		opponents[4].setTexture(new Texture(Gdx.files.internal("core/assets/boatF sprite1.png")));
		opponents[4].setStats(8,3,0.5f,1f);

		opponents[5] = new Opponent(0,56,182,lanes[6], "Opponent6");
		opponents[5].setTexture(new Texture(Gdx.files.internal("core/assets/boatG sprite1.png")));
		opponents[5].setStats(4,5,2f,5f);

		progressBar = new ProgressBar(player, opponents, course);
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	// All drawing is done in the GameScreen class.

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

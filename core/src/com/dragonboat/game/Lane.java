package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;


public class Lane {
    private final int LEFTBOUNDARY, RIGHTBOUNDARY;
    protected ArrayList<Obstacle> obstacles;
    private int obstacleLimit;

    public Lane(int leftBoundary, int rightBoundary) {
       this.LEFTBOUNDARY = leftBoundary;
       this.RIGHTBOUNDARY = rightBoundary;
       this.obstacleLimit = 10;

        obstacles = new ArrayList<>();
    }

    public Lane(int leftBoundary, int rightBoundary, int obstacleLimit) {
        this.LEFTBOUNDARY = leftBoundary;
        this.RIGHTBOUNDARY = rightBoundary;
        this.obstacleLimit = obstacleLimit;

        obstacles = new ArrayList<>();
    }

    public void SpawnObstacle(int x, int y, String obstacleType) {
        /*
        Method for spawning an Obstacle of specified type in this lane.
        First we check if we are at the obstacle limit, if we are not then
        we check to see if the specified type of Obstacle is either Goose or Log,
        if it is we create a new Obstacle instance of either Goose or Log and add
        it to this Lane's Obstacle list.
         */
        if(this.obstacles.size() <= this.obstacleLimit) {
            if (obstacleType.equals("Goose")) {
                Goose goose = new Goose(x, y, new Texture(Gdx.files.internal("gooseSouthsprite.png")));
                this.obstacles.add(goose);

            } else if (obstacleType.equals("Log")) {
                Log log = new Log(x, y, new Texture(Gdx.files.internal("logBig sprite.png")));
                this.obstacles.add(log);

            }
        } else System.out.println("Obstacle limit reached.");
    }
    
	public void RemoveObstacle(Obstacle obstacle) {
		/* 
		 Method for removing an Obstacle from the lane's Obstacle list at given index.
		 NOT SOLD ON THIS IMPLEMENTATION. 
		 Obstacle should be removed upon collision with boat or leaving the course area.
		 */
		this.obstacles.remove(obstacle);
	}

    // getters and setters

    public int GetLeftBoundary() {
        return this.LEFTBOUNDARY;
    }
    public int GetRightBoundary() {
        return this.RIGHTBOUNDARY;
    }
}

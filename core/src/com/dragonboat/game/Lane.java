package com.dragonboat.game;

import com.sun.corba.se.impl.io.TypeMismatchException;

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
                Goose goose = new Goose(x, y);
                this.obstacles.add(goose);

            } else if (obstacleType.equals("Log")) {
                Log log = new Log(x, y);
                this.obstacles.add(log);

            } else {
                throw new TypeMismatchException("Obstacle type not valid.");
            }
        } else System.console().printf("Obstacle limit reached.");
    }

    // getters and setters

    public int GetLeftBoundary() {
        return this.LEFTBOUNDARY;
    }
    public int GetRightBoundary() {
        return this.RIGHTBOUNDARY;
    }
}

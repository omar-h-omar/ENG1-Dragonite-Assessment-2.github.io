package com.dragonboat.game;

import com.sun.corba.se.impl.io.TypeMismatchException;
import org.omg.IOP.CodecPackage.TypeMismatch;

public class Lane {
    private final int LEFTBOUNDARY, RIGHTBOUNDARY;
    private Obstacle[] obstacles;
    private int obstacleLimit;
    public Lane(int leftBoundary, int rightBoundary) {
       this.LEFTBOUNDARY = leftBoundary;
       this.RIGHTBOUNDARY = rightBoundary;
       this.obstacleLimit = 10;

       obstacles = new Obstacle[obstacleLimit];
    }

    public void SpawnObstacle(int x, int y, String obstacleType) {
        if(obstacleType.equals("Goose")) {

        }
        if(obstacleType.equals("Log")) {

        }
        else {
            throw new TypeMismatchException("Obstacle type not valid.");
        }
    }

    // getters and setters

    public int GetLeftBoundary() {
        return this.LEFTBOUNDARY;
    }
    public int GetRightBoundary() {
        return this.RIGHTBOUNDARY;
    }
}

package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;

public class Boat {
    /*
     Direct representation based off the UML diagram
     https://drive.google.com/file/d/15O95umnJIoApnsj8I9ejEtMxrDGYJWAC/view?usp=sharing
     may need to lump data about the object we'll be displaying into a Box2D object.
     */
    private int robustness, maxSpeed, durability, tiredness, penalties, legTime;
    private float acceleration, maneuverability;
    public int xPosition, yPosition, width, height;
    public float currentSpeed, progress;
    private Lane lane;
    private Texture texture;

    public Boat(int xPosition, int yPosition, int width, int height, Lane lane) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.currentSpeed = 0f;
        this.maxSpeed = 5;
        this.penalties = 0;
        this.durability = 100;
        this.robustness = 10;
        this.tiredness = 0;
        this.progress = 0f;
        this.lane = lane;
        this.acceleration = 0.05f;
        this.maneuverability = 1;
        
        
    }

    public void SteerLeft() {
        xPosition -= maneuverability * currentSpeed;
    }

    public void SteerRight() {
        xPosition += maneuverability * currentSpeed;
    }

    public void IncreaseSpeed() {
        currentSpeed = (currentSpeed + acceleration) >= maxSpeed ?
                maxSpeed : currentSpeed + acceleration;
    }

    public void DecreaseSpeed() {
        /*
         Very basic decceleration function, acting as water friction.
         could be updated using functions from
         https://denysalmaral.com/2019/05/boat-sim-notes-1-water-friction.html
         to be more realistic.
         */
        currentSpeed = (currentSpeed - acceleration) <= 0 ? 0 : currentSpeed - 0.5f;
    }

    public boolean CheckCollisions(Obstacle[] obstacles) {
        /*
        Iterate through obstacles,
         */
        return false;
    }
}

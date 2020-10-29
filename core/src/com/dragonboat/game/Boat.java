package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;

public class Boat {
    /*
     Direct representation based off the UML diagram
     https://drive.google.com/file/d/15O95umnJIoApnsj8I9ejEtMxrDGYJWAC/view?usp=sharing
     may need to lump data about the object we'll be displaying into a Box2D object.
     */

    private final int ROBUSTNESS, MAXSPEED;
    private final float ACCELERATION, MANEUVERABILITY;

    private int durability, tiredness, penalties;
    private long fastestLegTime;
    public int xPosition, yPosition, width, height;
    public float currentSpeed, progress;
    private Lane lane;
    private Texture texture;

    public Boat(int xPosition, int yPosition, int width, int height, Lane lane) {
        /*
        These 4 attributes will be unique to each boat. The values used are placeholders for now.
        We will need some function to set these attributes depending on which team the player selects.
        (or whichever team is randomly chosen for the opponents).
         */
        this.ROBUSTNESS = 10;
        this.MAXSPEED = 5;
        this.ACCELERATION = 0.05f;
        this.MANEUVERABILITY = 1;

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.currentSpeed = 0f;
        this.penalties = 0;
        this.durability = 100;
        this.tiredness = 0;
        this.progress = 0f;
        this.lane = lane;
        this.fastestLegTime = 0;
        
    }

    public void SteerLeft() {
        this.xPosition -= this.MANEUVERABILITY * this.currentSpeed;
    }

    public void SteerRight() {
        this.xPosition += this.MANEUVERABILITY * this.currentSpeed;
    }

    public void IncreaseSpeed() {
        this.currentSpeed = (this.currentSpeed + this.ACCELERATION) >= this.MAXSPEED ?
                this.MAXSPEED : this.currentSpeed + this.ACCELERATION;
    }

    public void DecreaseSpeed() {
        /*
         Very basic deceleration function, acting as water friction.
         could be updated using functions from
         https://denysalmaral.com/2019/05/boat-sim-notes-1-water-friction.html
         to be more realistic.
         */
        this.currentSpeed = (this.currentSpeed - this.ACCELERATION) <= 0 ? 0 : this.currentSpeed - 0.5f;
    }

    public boolean CheckCollisions(Obstacle[] obstacles) {
        /*
        Iterate through obstacles,
         */
        return false;
    }

    public boolean ApplyDamage(int obstacleDamage) {
        /*
         Applies damage to boat depending on what kind of obstacle it hits.

         returns true if the boat goes below 0 durability.
         returns false if the boat is above 0 durability.
         */
        this.durability -= obstacleDamage/this.ROBUSTNESS;
        return this.durability <= 0;
    }

    public boolean CheckIfInLane() {
        /*
        Just checks whether this boat is within it's allocated lane's X boundaries
        returns true if it is within the boundaries
        returns false if not.

        May need to add check whether 0 < y < finish y position.
         */
        return this.xPosition > this.lane.getLeftBoundary() &&
                this.xPosition < this.lane.getRightBoundary();
    }

    public void UpdateFastestTime(long elapsedTime) {
        if(this.fastestLegTime == 0){
            this.fastestLegTime = elapsedTime;
        }
        else if(this.fastestLegTime > elapsedTime) {
            this.fastestLegTime = elapsedTime;
        }
    }

    // GETTERS / SETTERS \\

    public long GetFastestTime() {
        return this.fastestLegTime;
    }

    public int GetX() {
        return this.xPosition;
    }

    public int GetY() {
        return this.yPosition;
    }

}

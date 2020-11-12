package com.dragonboat.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Boat {
    /*
     Direct representation based off the UML diagram
     https://drive.google.com/file/d/15O95umnJIoApnsj8I9ejEtMxrDGYJWAC/view?usp=sharing
     may need to lump data about the object we'll be displaying into a Box2D object.
     */

    private int ROBUSTNESS, MAXSPEED;
    private float ACCELERATION, MANEUVERABILITY;

    private int durability, penalties;
    protected float yPosition, xPosition;
    protected int width, height;
    private float currentSpeed, progress, fastestLegTime, tiredness;
    protected Lane lane;
    private Texture[] textureFrames;
    private int frameCounter;
    public Texture texture;
    private String name;
    private boolean finished;

    public Boat(int yPosition, int width, int height, Lane lane, String name) {
        /*
        These 4 attributes will be unique to each boat. The values used are placeholders for now.
        We will need some function to set these attributes depending on which team the player selects.
        (or whichever team is randomly chosen for the opponents).
         */
        this.xPosition = lane.GetRightBoundary() - (lane.GetRightBoundary() - lane.GetLeftBoundary())/2 - width/2;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.currentSpeed = 0f;
        this.penalties = 0;
        this.durability = 50;
        this.tiredness = 0f;
        this.progress = 0f;
        this.lane = lane;
        this.fastestLegTime = 0;
        this.textureFrames = new Texture[4];
        frameCounter = 0;
    }

    public void SteerLeft() {
        this.xPosition -= this.MANEUVERABILITY * this.currentSpeed;
        this.currentSpeed *= 0.97;
    }

    public void SteerRight() {
        this.xPosition += this.MANEUVERABILITY * this.currentSpeed;
        this.currentSpeed *= 0.97;
    }

    public void MoveForward() {
        this.yPosition += this.currentSpeed;
        this.currentSpeed *= 0.9992;
    }

    public void IncreaseSpeed() {
        if(this.tiredness <= 75) {
            this.currentSpeed = (this.currentSpeed + this.ACCELERATION) >= this.MAXSPEED ?
                    this.MAXSPEED : this.currentSpeed + this.ACCELERATION;
        }
    }

    public void DecreaseSpeed() {
        /*
         Very basic deceleration function, acting as water friction.
         could be updated using functions from
         https://denysalmaral.com/2019/05/boat-sim-notes-1-water-friction.html
         to be more realistic.
         */
        this.currentSpeed = (this.currentSpeed - this.ACCELERATION) <= 0 ? 0 : this.currentSpeed - 0.015f;
    }

    public boolean CheckCollisions(int backgroundOffset) {
        /*
        Iterate through obstacles,
         */
        ArrayList<Obstacle> obstacles = this.lane.obstacles;
        ArrayList<Integer> obstaclesToRemove = new ArrayList<>();
        for(Obstacle o : obstacles) {
            if(o.getX() > this.xPosition && o.getX() < this.xPosition + this.width) {
                if(o.getY() + backgroundOffset > this.yPosition && o.getY() + backgroundOffset < this.yPosition + this.height) {
                    this.ApplyDamage(o.getDamage());
                    obstaclesToRemove.add(obstacles.indexOf(o));
                }
            }
        }
        for(int i : obstaclesToRemove) {
            this.lane.RemoveObstacle(obstacles.get(i));
        }
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
        return this.xPosition > this.lane.GetLeftBoundary() &&
                this.xPosition < this.lane.GetRightBoundary();
    }

    public void UpdateFastestTime(float elapsedTime) {
        if(this.fastestLegTime == 0){
            this.fastestLegTime = elapsedTime;
        }
        else if(this.fastestLegTime > elapsedTime) {
            this.fastestLegTime = elapsedTime;
        }
    }

    // getters and setters

    public void setTexture(Texture t) {
        this.texture = t;
    }

    public void setTextureFrames(Texture[] frames) {this.textureFrames = frames; }

    public void AdvanceTextureFrame() {
        this.frameCounter = this.frameCounter == this.textureFrames.length - 1 ? 0 : this.frameCounter + 1;
        this.setTexture(this.textureFrames[this.frameCounter]);
    }

    public float getFastestTime() {
        return this.fastestLegTime;
    }

    public int getX() {
        return Math.round(this.xPosition);
    }

    public int getY() {
        return Math.round(this.yPosition);
    }

    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }

    public boolean Finished() {
        return this.finished;
    }

    public void setFinished(boolean f) {
        this.finished = f;
    }

    public float getCurrentSpeed() {
        return this.currentSpeed;
    }

    public float getProgress(int finishY) {
        return Math.min((this.yPosition) / finishY, 1);
    }

    public void SetStats(int maxspeed, int robustness, float acceleration, float maneuverability) {
        this.MAXSPEED = maxspeed;
        this.ROBUSTNESS = robustness;
        this.ACCELERATION = acceleration / 64;
        this.MANEUVERABILITY = maneuverability / 8;
    }

    public void IncreaseTiredness() {
        this.tiredness += this.tiredness >= 100 ? 0 : 0.75f;
    }
    public void DecreaseTiredness() {
        this.tiredness -= this.tiredness <= 0 ? 0 : 1f;
    }

    public float getManeuverability() {
        return this.MANEUVERABILITY;
    }

    public float getAcceleration() {
        return this.ACCELERATION;
    }

    public int getRobustness() {
        return this.ROBUSTNESS;
    }

    public int getDurability() {
        return this.durability;
    }

    public int getMaxSpeed() {
        return this.MAXSPEED;
    }

    public float getTiredness() {
        return this.tiredness;
    }
}

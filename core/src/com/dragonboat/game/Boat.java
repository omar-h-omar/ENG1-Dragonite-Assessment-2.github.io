package com.dragonboat.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a Boat, controlled by either a Player or Opponent.
 * 
 * @see Player
 * @see Opponent
 */
public class Boat {
    /*
     * Direct representation based off the UML diagram
     * https://drive.google.com/file/d/15O95umnJIoApnsj8I9ejEtMxrDGYJWAC/view?usp=sharing
     */

    private int ROBUSTNESS, MAXSPEED;
    private float ACCELERATION, MANEUVERABILITY;

    private int durability;
    protected float yPosition, xPosition, penalties;
    protected int width, height;
    private float currentSpeed, fastestLegTime, tiredness;
    protected Lane lane;
    private Texture[] textureFrames;
    private int frameCounter;
    public Texture texture;
    private String name;
    private boolean finished;
    private int threshold = 5;

    /**
     * Creates a Boat instance in a specified Lane.
     * 
     * @param yPosition Y-position of the boat.
     * @param width     Width of the boat.
     * @param height    Height of the boat.
     * @param lane      Lane object.
     * @param name      String identifier.
     */
    public Boat(float yPosition, int width, int height, Lane lane, String name) {
        this.xPosition = lane.getRightBoundary() - (lane.getRightBoundary() - lane.getLeftBoundary()) / 2 - width / 2;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.currentSpeed = 0f;
        this.penalties = 0;
        this.durability = 50;
        this.tiredness = 0f;
        this.lane = lane;
        this.fastestLegTime = 0;
        this.textureFrames = new Texture[4];
        frameCounter = 0;
        this.name = name;
    }

    /**
     * Decreases the x-position of the boat respective to the boat's maneuverability
     * and speed, and decreases the speed by 3%.
     */
    public void SteerLeft() {
        if (this.xPosition >= 0) {
            this.xPosition -= this.MANEUVERABILITY * this.currentSpeed;
            this.currentSpeed *= 0.985;
        }

    }

    /**
     * Increases the x-position of the boat respective to the boat's maneuverability
     * and speed, and decreases the speed by 3%.
     */
    public void SteerRight() {
        if (this.xPosition + this.width <= Gdx.graphics.getHeight()) {
            this.xPosition += this.MANEUVERABILITY * this.currentSpeed;
            this.currentSpeed *= 0.985;
        }

    }

    /**
     * Increases the y-position of the boat respective to the boat's speed, and
     * decreases the speed by 0.08%.
     */
    public void MoveForward() {
        this.yPosition += this.currentSpeed;
        this.currentSpeed *= 0.9992;
    }

    /**
     * If the boat has enough stamina, increase the speed of the boat by the boat's
     * acceleration, if not, do nothing.
     */
    public void IncreaseSpeed() {
        if (this.tiredness <= 75) {
            this.currentSpeed = (this.currentSpeed + this.ACCELERATION) >= this.MAXSPEED ? this.MAXSPEED
                    : this.currentSpeed + this.ACCELERATION;
        }
    }

    /**
     * Decreases the speed of the boat by 0.015 if the resulting speed is greater
     * than 0.
     */
    public void DecreaseSpeed() {
        /*
         * Very basic deceleration function, acting as water friction. could be updated
         * using functions from
         * https://denysalmaral.com/2019/05/boat-sim-notes-1-water-friction.html to be
         * more realistic.
         */
        this.currentSpeed = (this.currentSpeed - 0.015) <= 0 ? 0 : this.currentSpeed - 0.015f;
    }

    /**
     * Checks each obstacle in the Lane for a collision.
     * 
     * @param backgroundOffset How far up the course the player is.
     * @return Boolean representing if a collision occurs.
     */
    public boolean CheckCollisions(int backgroundOffset) {
        // Iterate through obstacles.
        ArrayList<Obstacle> obstacles = this.lane.obstacles;
        ArrayList<Integer> obstaclesToRemove = new ArrayList<>();
        for (Obstacle o : obstacles) {
            if (o.getX() > this.xPosition + threshold && o.getX() < this.xPosition + this.width - threshold) {
                if (o.getY() + backgroundOffset > this.yPosition + threshold
                        && o.getY() + backgroundOffset < this.yPosition + this.height - threshold) {
                    this.ApplyDamage(o.getDamage());
                    obstaclesToRemove.add(obstacles.indexOf(o));
                }
            }
        }
        for (int i : obstaclesToRemove) {
            this.lane.RemoveObstacle(obstacles.get(i));
            return true;
        }
        return false;
    }

    /**
     * Decreases the durability of the boat by the obstacle damage divided by the
     * boat's robustness.
     * 
     * @param obstacleDamage Amount of damage an Obstacle inflicts on the boat.
     * @return Boolean representing whether the durability of the boat is below 0.
     */
    public boolean ApplyDamage(int obstacleDamage) {
        this.durability -= obstacleDamage / this.ROBUSTNESS;
        this.currentSpeed *= 0.9;
        return this.durability <= 0;
    }

    /**
     * Checks if the boat is between the left boundary and the right boundary of the Lane.
     * 
     * @return Boolean representing whether the Boat is in the Lane.
     */
    public boolean CheckIfInLane() {
        return this.xPosition + threshold > this.lane.getLeftBoundary()
                && this.xPosition + this.width - threshold < this.lane.getRightBoundary();
    }

    /**
     * Updates the boat's fastest time.
     * 
     * @param elapsedTime Time it took the boat to finish the current race.
     */
    public void UpdateFastestTime(float elapsedTime) {
        if (this.fastestLegTime > elapsedTime + this.penalties || this.fastestLegTime == 0) {
            this.fastestLegTime = elapsedTime + this.penalties;
        }
    }

    /**
     * Increases the tiredness of the boat by 0.75 if the tiredness is less than
     * 100.
     */
    public void IncreaseTiredness() {
        this.tiredness += this.tiredness >= 100 ? 0 : 0.75f;
    }

    /**
     * Decreases the tiredness of the boat by 1 if the tiredness is greater than 0.
     */
    public void DecreaseTiredness() {
        this.tiredness -= this.tiredness <= 0 ? 0 : 1f;
    }

    /**
     * Keeps track of which frame of the animation the boat's texture is on, and
     * sets the texture accordingly.
     */
    public void AdvanceTextureFrame() {
        this.frameCounter = this.frameCounter == this.textureFrames.length - 1 ? 0 : this.frameCounter + 1;
        this.setTexture(this.textureFrames[this.frameCounter]);
    }

    /**
     * Generates all frames for animating the boat.
     * 
     * @param boatName Boat name, used to get correct asset.
     */
    public void GenerateTextureFrames(char boatName) {
        Texture[] frames = new Texture[4];
        for (int i = 1; i <= frames.length; i++) {
            frames[i - 1] = new Texture(Gdx.files.internal("boat" + boatName + " sprite" + i + ".png"));
        }
        this.setTextureFrames(frames);
    }

    /**
     * Resets necessary stats for the next race.
     */
    public void Reset() {
        this.xPosition = lane.getRightBoundary() - (lane.getRightBoundary() - lane.getLeftBoundary()) / 2 - width / 2;
        this.yPosition = 0;
        this.currentSpeed = 0f;
        this.penalties = 0;
        this.durability = 50;
        this.tiredness = 0f;
        this.finished = false;

    }

    /**
     * Resets the boat's fastest leg time.
     */
    public void ResetFastestLegTime() {
        this.fastestLegTime = 0;
    }

    // getters and setters

    /**
     * 
     * @param t Texture to use.
     */
    public void setTexture(Texture t) {
        this.texture = t;
    }

    /**
     * 
     * @param frames Texture frames for animation.
     */
    public void setTextureFrames(Texture[] frames) {
        this.textureFrames = frames;
    }

    /**
     * 
     * @return Float representing fastest race/leg time.
     */
    public float getFastestTime() {
        return this.fastestLegTime;
    }

    /**
     * 
     * @return Int representing x-position of boat.
     */
    public int getX() {
        return Math.round(this.xPosition);
    }

    /**
     * 
     * @return Int representing y-position of boat.
     */
    public int getY() {
        return Math.round(this.yPosition);
    }

    /**
     * 
     * @return Int representing the y coordinate range of the boat (length).
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * 
     * @return String representing name of the boat.
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @return Boolean representing if the boat has finished the current race.
     */
    public boolean finished() {
        return this.finished;
    }

    /**
     * 
     * @param f Boolean representing if the boat has finished the race.
     */
    public void setFinished(boolean f) {
        this.finished = f;
    }

    /**
     * 
     * @return Float representing the current speed of the boat.
     */
    public float getCurrentSpeed() {
        return this.currentSpeed;
    }

    /**
     * 
     * @param finishY Y-position of the finish line.
     * @return Float representing the progress of the boat from 0 to 1.
     */
    public float getProgress(int finishY) {
        return Math.min((this.yPosition) / finishY, 1);
    }

    /**
     * Implicitly sets the stats of the boat, given each attribute.
     * 
     * @param maxspeed        Top speed the boat can reach.
     * @param robustness      How resilient to obstacle damage the boat is.
     * @param acceleration    How much the speed increases each frame.
     * @param maneuverability How easily the boat can move left or right.
     */
    public void setStats(int maxspeed, int robustness, float acceleration, float maneuverability) {
        this.MAXSPEED = maxspeed / 2;
        this.ROBUSTNESS = robustness;
        this.ACCELERATION = acceleration / 64;
        this.MANEUVERABILITY = maneuverability / 8;
    }

    /**
     * Interpolates predetermined stats from a boat label, and sets the stats based
     * on those.
     * 
     * @param boatLabel A character between A-G representing a specific boat.
     */
    public void setStats(char boatLabel) {
        int[] maxspeeds = { 5, 4, 5, 5, 4, 7, 5 };
        int[] robustnesses = { 2, 4, 1, 4, 8, 3, 5 };
        float[] accelerations = { 6f, 2f, 8f, 4f, 3f, 1.4f, 2f };
        float[] maneuverabilities = { 3f, 8f, 3f, 4f, 2f, 1f, 5f };

        int boatNo = boatLabel - 65;

        this.setStats(maxspeeds[boatNo], robustnesses[boatNo], accelerations[boatNo], maneuverabilities[boatNo]);
    }

    /**
     * 
     * @return Float representing the manouverability of the boat.
     */
    public float getManeuverability() {
        return this.MANEUVERABILITY;
    }

    /**
     * 
     * @return Float representing the acceleration of the boat.
     */
    public float getAcceleration() {
        return this.ACCELERATION;
    }

    /**
     * 
     * @return Int representing the robustness of the boat.
     */
    public int getRobustness() {
        return this.ROBUSTNESS;
    }

    /**
     * 
     * @return Int representing the durability of the boat.
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * 
     * @return Int representing the maximum speed of the boat.
     */
    public int getMaxSpeed() {
        return this.MAXSPEED;
    }

    /**
     * 
     * @return Float representing the tiredness of the boat crew.
     */
    public float getTiredness() {
        return this.tiredness;
    }

    /**
     * 
     * @return Float representing the time penalty incurred for the current race.
     */
    public float getPenalty() {
        return this.penalties;
    }

    /**
     * 
     * @param penalty Float to add to the boat's penalty total for the current race.
     */
    public void applyPenalty(float penalty) {
        this.penalties += penalty;
    }

    /**
     * 
     * @param lane Lane object for the boat.
     */
    public void setLane(Lane lane) {
        this.lane = lane;
        this.xPosition = lane.getRightBoundary() - (lane.getRightBoundary() - lane.getLeftBoundary()) / 2 - width / 2;
    }

}

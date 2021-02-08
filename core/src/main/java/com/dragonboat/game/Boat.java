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
     * https://lucid.app/lucidchart/1722c0c4-170a-41ba-a8ca-cebedb17eab6/edit?shared=true&page=0_0#?folder_id=home&browser=icon
     */

    private int ROBUSTNESS, MAXSPEED;
    private float ACCELERATION, MANEUVERABILITY;

    private int durability;
    protected float yPosition, xPosition, penalties;
    protected int width, height;
    private float currentSpeed, fastestLegTime, tiredness;
    protected Lane lane;

    private Texture[] textureFrames;
    public int frameCounter;
    public Texture texture;
    private String name;
    private boolean finished;
    private int threshold = 5;

 // "ASSESSMENT2:START"

    public PowerUp[] boatPowerUps;
    public float powerUpTimer;
    private boolean isInvincible;
    private int invCounter;
    private float reductions;

 // "ASSESSMENT2:END"
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
     // "ASSESSMENT2:START"
        this.boatPowerUps = new PowerUp[2];
        this.isInvincible = false;
        this.invCounter = 3;
        this.reductions = 0;
     // "ASSESSMENT2:END"
    }

    /**
     * Decreases the x-position of the boat respective to the boat's maneuverability
     * and speed, and decreases the speed by 3%.
     */
    public void SteerLeft(Course course) {
        if (this.xPosition >= course.getLeftBoundary()) {
            this.xPosition -= this.MANEUVERABILITY * this.currentSpeed;
            this.currentSpeed *= 0.985;
        }

    }

    /**
     * Increases the x-position of the boat respective to the boat's maneuverability
     * and speed, and decreases the speed by 3%.
     */
    public void SteerRight(Course course) {
        if (this.xPosition + this.width <= course.getRightBoundary()) {
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
     // "ASSESSMENT2:START"
        ArrayList<PowerUp> powerUps = this.lane.powerUps;
     // "ASSESSMENT2:END"
        ArrayList<Integer> obstaclesToRemove = new ArrayList<>();
     // "ASSESSMENT2:START"
        ArrayList<Integer> powerUpsToRemove = new ArrayList<>();

        for (PowerUp p : powerUps) {
            // new changed so that it accommodates for obstacles with different width
            if (p.getX() < this.xPosition + this.width && this.xPosition < p.getX() + p.getTexture().getWidth()) {
                // new changed for detection of y as it would not collide on the side of boats
                if (this.yPosition + this.height > p.getY() + backgroundOffset
                        && this.yPosition < p.getY() + p.texture.getHeight() + backgroundOffset) {
                    this.AddPowerUp(p);
                    powerUpsToRemove.add(powerUps.indexOf(p));
                }
            }
        }
        for (int j : powerUpsToRemove) {
            this.lane.RemovePowerUp(powerUps.get(j));
        }

        if (isInvincible == true) {
            invCounter -= 1;
            if (invCounter <= 0){
                isInvincible = false;
            }
            return false;
        }
     // "ASSESSMENT2:END"
        for (Obstacle o : obstacles) {
            // new changed so that it accommodates for obstacles with different width
            if (o.getX() < this.xPosition + this.width && this.xPosition < o.getX() + o.getTexture().getWidth()) {
                // new changed for detection of y as it would not collide on the side of boats
                if (this.yPosition + this.height > o.getY() + backgroundOffset
                        && this.yPosition < o.getY() + o.texture.getHeight() + backgroundOffset) {

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
 // "ASSESSMENT2:START"

    /**
     * Adds the power up to one of boat's 2 power up slots if it's empty
     * @param p A power up which gives boats a perk
     */
    public void AddPowerUp(PowerUp p) {
        if (boatPowerUps[0] == null){
            boatPowerUps[0] = p;
        }
        else if (boatPowerUps[1] == null){
            boatPowerUps[1] = p;
        }
    }

    /**
     * Checks the type of power up and applies it to the boat
     * @param p A power up which gives boats a perk
     */
    public void ApplyPowerUp(PowerUp p){
        if (p.type == "Maneuverability"){
            MANEUVERABILITY += 1;
        }
        else if (p.type == "Repair"){
            durability = 50;
        }
        else if (p.type == "SpeedBoost"){
            currentSpeed *= 4;
        }
        else if (p.type == "TimeReduction"){
            reductions += 5;
        }
        else if (p.type == "Invincibility"){
            isInvincible = true;
            invCounter = 3;
        }
    }
 // "ASSESSMENT2:END"
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
            this.fastestLegTime = elapsedTime + this.penalties - this.reductions;
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
     * Sets the texture
     * @param t Texture to use.
     */
    public void setTexture(Texture t) {
        this.texture = t;
    }

    /**
     * Sets textureFrames
     * @param frames Texture frames for animation.
     */
    public void setTextureFrames(Texture[] frames) {
        this.textureFrames = frames;
    }

    /**
     * Gets the fastestLegTime of the boat
     * @return Float representing fastest race/leg time.
     */
    public float getFastestTime() {
        return this.fastestLegTime;
    }

    /**
     * Gets the boat's x position
     * @return Int representing x-position of boat.
     */
    public int getX() {
        return Math.round(this.xPosition);
    }

    /**
     * Gets the boat's y position
     * @return Int representing y-position of boat.
     */
    public int getY() {
        return Math.round(this.yPosition);
    }

    /**
     * Gets the height of the boat
     * @return Int representing the y coordinate range of the boat (length).
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Gets the name of the boat
     * @return String representing name of the boat.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns whether a boat has finished or not
     * @return Boolean representing if the boat has finished the current race.
     */
    public boolean finished() {
        return this.finished;
    }

    /**
     * Sets whether a boat has finished or not
     * @param f Boolean representing if the boat has finished the race.
     */
    public void setFinished(boolean f) {
        this.finished = f;
    }

    /**
     * Gets the boat's current speed
     * @return Float representing the current speed of the boat.
     */
    public float getCurrentSpeed() {
        return this.currentSpeed;
    }

    /**
     * Gets the current progress of a boat given its current y position
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
        int[] maxspeeds = {5, 4, 5, 5, 4, 7, 5};
        int[] robustnesses = {2, 4, 1, 4, 8, 3, 5};
        float[] accelerations = {6f, 2f, 8f, 4f, 3f, 1.4f, 2f};
        float[] maneuverabilities = {3f, 8f, 3f, 4f, 2f, 1f, 5f};

        int boatNo = boatLabel - 65;

        this.setStats(maxspeeds[boatNo], robustnesses[boatNo], accelerations[boatNo], maneuverabilities[boatNo]);
    }

    /**
     * Gets the boat's maneuverability
     * @return Float representing the maneuverability of the boat.
     */
    public float getManeuverability() {
        return this.MANEUVERABILITY;
    }

    /**
     * Gets the boat's acceleration
     * @return Float representing the acceleration of the boat.
     */
    public float getAcceleration() {
        return this.ACCELERATION;
    }

    /**
     * Gets the boat's robustness
     * @return Int representing the robustness of the boat.
     */
    public int getRobustness() {
        return this.ROBUSTNESS;
    }

    /**
     * Gets the boat's durability
     * @return Int representing the durability of the boat.
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Gets the boat's maximum speed
     * @return Int representing the maximum speed of the boat.
     */
    public int getMaxSpeed() {
        return this.MAXSPEED;
    }

    /**
     * Gets the boat's tiredness
     * @return Float representing the tiredness of the boat crew.
     */
    public float getTiredness() {
        return this.tiredness;
    }

    /**
     * Gets the boat's penalty
     * @return Float representing the time penalty incurred for the current race.
     */
    public float getPenalty() {
        return this.penalties;
    }

    /**
     * Applies a time penalty to the boat
     * @param penalty Float to add to the boat's penalty total for the current race.
     */
    public void applyPenalty(float penalty) {
        this.penalties += penalty;
    }

    /**
     * Sets a lane to the boat
     * @param lane Lane object for the boat.
     */
    public void setLane(Lane lane) {
        this.lane = lane;
        this.xPosition = lane.getRightBoundary() - (lane.getRightBoundary() - lane.getLeftBoundary()) / 2 - width / 2;
    }
 // "ASSESSMENT2:START"
    /**
     * Sets the robustness of the boat
     * @param ROBUSTNESS an integer representing how resilient to obstacle damage the boat is.
     */
    public void setROBUSTNESS(int ROBUSTNESS) {
        this.ROBUSTNESS = ROBUSTNESS;
    }

    /**
     * Sets the acceleration of the boat
     * @param ACCELERATION A float representing how much the speed increases each frame.
     */
    public void setACCELERATION(float ACCELERATION) {
        this.ACCELERATION = ACCELERATION;
    }

    /**
     * Sets the maneuverability of the boat
     * @param MANEUVERABILITY A float representing how easily the boat can move left or right.
     */
    public void setMANEUVERABILITY(float MANEUVERABILITY) {
        this.MANEUVERABILITY = MANEUVERABILITY;
    }

    /**
     * Sets the durability of the boat
     * @param durability an integer representing the current health of the boat.
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }

    /**
     * Sets the current speed of the boat
     * @param currentSpeed A float representing the current speed of the boat.
     */
    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    /**
     * Sets the tiredness of the boat
     * @param tiredness A float representing the tiredness of the boat crew.
     */
    public void setTiredness(float tiredness) {
        this.tiredness = tiredness;
    }

    /**
     * Sets the x position of the boat
     * @param xPosition A float representing the position of the boat on the x axis.
     */
    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Sets the penalties of the boat
     * @param penalties A float representing the penalties of the boat on the y axis.
     */
    public void setPenalties(float penalties) {
        this.penalties = penalties;
    }

    /**
     * Sets the y position of the boat
     * @param yPosition A float representing the position of the boat on the y axis.
     */
    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

}
//"ASSESSMENT2:END"

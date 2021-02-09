package com.dragonboat.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a lane on the course.
 */
public class Lane {
    private final int LEFTBOUNDARY, RIGHTBOUNDARY;
    protected ArrayList<Obstacle> obstacles;
  //"ASSESSMENT2:START"
    protected ArrayList<PowerUp> powerUps;
  //"ASSESSMENT2:END"
    private int obstacleLimit;
    private int powerUpLimit;

    /**
     * Creates a lane instance.
     *
     * @param leftBoundary  X-position for the left boundary of the lane.
     * @param rightBoundary X-position for the right boundary of the lane.
     */
    public Lane(int leftBoundary, int rightBoundary) {
        this.LEFTBOUNDARY = leftBoundary;
        this.RIGHTBOUNDARY = rightBoundary;
        this.obstacleLimit = 10;
        // "ASSESSMENT2:START"
        this.powerUpLimit = 4;
        //"ASSESSMENT2:END"

        obstacles = new ArrayList<>();
        //"ASSESSMENT2:START"
        powerUps = new ArrayList<>();
        //"ASSESSMENT2:END"
    }

    /**
     * Creates a lane instance.
     *
     * @param leftBoundary  X-position for the left boundary of the lane.
     * @param rightBoundary X-position for the right boundary of the lane.
     * @param obstacleLimit Limit for the number of obstacles in the lane.
     */
    public Lane(int leftBoundary, int rightBoundary, int obstacleLimit) {
        this.LEFTBOUNDARY = leftBoundary;
        this.RIGHTBOUNDARY = rightBoundary;
        this.obstacleLimit = obstacleLimit;
      //"ASSESSMENT2:START"
        this.powerUpLimit = 4;
      //"ASSESSMENT2:END"
        obstacles = new ArrayList<>();
      //"ASSESSMENT2:START"
        powerUps = new ArrayList<>();
      //"ASSESSMENT2:END"
    }

    /**
     * <p>
     * Spawns obstacle in the lane.
     * </p>
     * <p>
     * Spawns specified obstacle in the lane. Checks that the obstacle limit hasn't
     * been reached, if not checks the obstacle type for Goose or Log and
     * instantiates it as the corresponding obstacle, with the correct texture. Then
     * adds it to the Lane's obstacle list.
     * </p>
     *
     * @param x            X-position for the obstacle spawn location.
     * @param y            Y-position for the obstacle spawn location.
     * @param obstacleType Obstacle type.
     */
    public void SpawnObstacle(int x, int y, String obstacleType) {
        if (this.obstacles.size() <= this.obstacleLimit) {
            if (obstacleType.equals("Goose")) {
                Goose goose = new Goose(x, y, new Texture(Gdx.files.internal("gooseSouthsprite.png")), this);
                this.obstacles.add(goose);

                //"ASSESSMENT2:START"
            } else if (obstacleType.equals("OakLog")) {
                Log log = new Log(x, y, new Texture(Gdx.files.internal("OakLog.png")),"OakLog");
                this.obstacles.add(log);

            } else if (obstacleType.equals("OakLogShort")) {
                Log log = new Log(x, y, new Texture(Gdx.files.internal("OakLogShort.png")),"OakLogShort");
                this.obstacles.add(log);

            } else if (obstacleType.equals("BirchLog")) {
                Log log = new Log(x, y, new Texture(Gdx.files.internal("BirchLog.png")),"BirchLog");
                this.obstacles.add(log);

            } else if (obstacleType.equals("BirchLogShort")) {
                Log log = new Log(x, y, new Texture(Gdx.files.internal("BirchLogShort.png")),"BirchLogShort");
                this.obstacles.add(log);

            } else if (obstacleType.equals("Rock1")) {
                Rock rock = new Rock(x, y, new Texture(Gdx.files.internal("Rock1.png")),"Rock1");
                this.obstacles.add(rock);

            } else if (obstacleType.equals("Rock2")) {
            Rock rock = new Rock(x, y, new Texture(Gdx.files.internal("Rock2.png")),"Rock2");
            this.obstacles.add(rock);

            } else if (obstacleType.equals("Rock3")) {
                Rock rock = new Rock(x, y, new Texture(Gdx.files.internal("Rock3.png")),"Rock3");
                this.obstacles.add(rock);
            }
            //"ASSESSMENT2:START"
        } else
            System.out.println("Obstacle limit reached.");
    }
  //"ASSESSMENT2:START"
    /**
     * <p>
     * Spawns power-ups in the lane.
     * </p>
     * <p>
     * Spawns specified power-up in the lane. Checks that the power-up limit hasn't
     * been reached, if not checks the obstacle type for Invincibility, Repair, TimeReduction,
     * Maneuverability or Acceleration and instantiates it as the corresponding power-up,
     * with the correct texture. Then adds it to the Lane's power-ups list.
     * </p>
     *
     * @param x            X-position for the power-up spawn location.
     * @param y            Y-position for the power-up spawn location.
     * @param powerUpType  Power-up type.
     */
    public void SpawnPowerUp(int x, int y, String powerUpType) {
        if (this.powerUps.size() <= this.powerUpLimit) {
            if (powerUpType.equals("Invincibility")) {
                Invincibility invincibility = new Invincibility(x, y, new Texture(Gdx.files.internal("itemBox.png")), this);
                this.powerUps.add(invincibility);
            }
            else if (powerUpType.equals("Maneuverability")) {
                Maneuverability maneuverability = new Maneuverability(x, y, new Texture(Gdx.files.internal("itemBox.png")), this);
                this.powerUps.add(maneuverability);
            }
            else if (powerUpType.equals("Repair")) {
                Repair repair = new Repair(x, y, new Texture(Gdx.files.internal("itemBox.png")), this);
                this.powerUps.add(repair);
            }
            else if (powerUpType.equals("SpeedBoost")) {
                SpeedBoost speedBoost = new SpeedBoost(x, y, new Texture(Gdx.files.internal("itemBox.png")), this);
                this.powerUps.add(speedBoost);
            }
            else if (powerUpType.equals("TimeReduction")) {
                TimeReduction timeReduction = new TimeReduction(x, y, new Texture(Gdx.files.internal("itemBox.png")), this);
                this.powerUps.add(timeReduction);
            }
        } else
            System.out.println("Power-up limit reached.");
    }
  //"ASSESSMENT2:END"
    
    /**
     * <p>
     * Removes obstacle from obstacle list.
     * </p>
     * <p>
     * Obstacle should be removed upon collision with boat or leaving the course.
     * area.
     * </p>
     *
     * @param obstacle Obstacle to be removed.
     */
    public void RemoveObstacle(Obstacle obstacle) {
        this.obstacles.remove(obstacle);
    }
  //"ASSESSMENT2:START"
    /**
     * <p>
     * Removes power-up from powerUps list.
     * </p>
     * <p>
     * PowerUps should be removed upon collision with boat or leaving the course.
     * area.
     * </p>
     *
     * @param powerUp PowerUp to be removed.
     */
    public void RemovePowerUp(PowerUp powerUp) {
        this.powerUps.remove(powerUp);
    }
  //"ASSESSMENT2:END"
    // getters and setters

    /**
     * Gets the lane's left boundary.
     * @return Int representing the x-position of the lane's left boundary.
     */
    public int getLeftBoundary() {
        return this.LEFTBOUNDARY;
    }

    /**
     * Gets the lane's right boundary.
     * @return Int representing the x-position of the lane's right boundary.
     */
    public int getRightBoundary() {
        return this.RIGHTBOUNDARY;
    }

    /**
     * Gets the obstacle limit
     * @return Int representing the maximum number of obstacles in a lane.
     */
    public int getObstacleLimit() {
        return this.obstacleLimit;
    }

    /**
     * Gets the total number of obstacles
     * @return Int representing the total number of obstacles in a lane.
     */
    public int getTotalObstacles() {
        return obstacles.size();
    }

    /**
     * Gets the list of obstacle in a lane
     * @return ArrayList containing all the obstacles in a lane.
     */
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}

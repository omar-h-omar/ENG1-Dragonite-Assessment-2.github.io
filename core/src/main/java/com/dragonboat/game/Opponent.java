package com.dragonboat.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Represents a opponent boat with AI.
 */
public class Opponent extends Boat {

    public String steering = "None";
    private ArrayList<Obstacle> sortedIncomingObstacles;
  //"ASSESSMENT2:START"
    public Rectangle rightBox; // a rectangle on the far right in front of the boat.
    public Rectangle midBox; // a rectangle on the middle in front of the boat.
    public Rectangle leftBox; // a rectangle on the far left in front of the boat.
    public Rectangle rightSideBox; // a rectangle on the right of the boat.
    public Rectangle leftSideBox; // a rectangle on the left of the boat.
  //"ASSESSMENT2:END"
    /**
     * Creates a opponent instance.
     *
     * @param yPosition Y-position.
     * @param width     Width of the boat.
     * @param height    Height of the boat.
     * @param lane      Lane for the boat.
     * @param name      Name of the opponent.
     */
    public Opponent(int yPosition, int width, int height, Lane lane, String name) {
        super(yPosition, width, height, lane, name);
        sortedIncomingObstacles = new ArrayList<Obstacle>();
     }

    /**
     * <p>
     * Controls the AI behaviour of the boat.
     * </p>
     * <p>
     * Changes the movement path of the boat.
     * </p>
     * <p>
     * AI new path selection:
     * </p>
     * <p>
     * 1) If not in lane, go back to lane.
     * </p>
     * <p>
     * 2) If obstacle ahead, avoid the obstacle. If dead ahead, slow down.
     * </p>
     * <p>
     * 3) If nothing, speed up.
     * </p>
     *
     * @param backgroundOffset Int representing the background offset
     * @param level            The current difficulty level
     * @param course           The race course
     */

    public void ai(int backgroundOffset, String level, Course course) {

        int leftSide = Math.round(xPosition);
        int rightSide = Math.round(xPosition + width);

        int arbitrary = 50;
        int fov = Math.round(arbitrary * this.getManeuverability()); // Determine a good field of view for the Opponents
        // to start reacting to incoming obstacles.
        int visionDistance = Math.round(yPosition + height) + fov;

        ArrayList<Obstacle> allIncomingObstacles = this.lane.obstacles;

        boolean noNewPath = true; // Set to false whenever the Opponent has decided on a new path.

        if (this.steering == "Left") {
            this.SteerLeft(course);
            this.steering = "None";
            noNewPath = false;
        } else if (this.steering == "Right") {
            this.SteerRight(course);
            this.steering = "None";
            noNewPath = false;
        } else {
            noNewPath = true;
        }

        /*
         * 1) If not in lane, go back to lane.
         */

        if (this.CheckIfInLane() == false || !noNewPath) {
            // Commence route back into lane.
            if (leftSide - this.lane.getLeftBoundary() <= 0) {
                // Will only be negative if the boat is further left than the left boundary of the lane.
                this.SteerRight(course);
                this.steering = "Right";
            } else if (rightSide - this.lane.getRightBoundary() >= 0) {
                // Will only be positive if the boat is further right than the right boundary of the lane.
                this.SteerLeft(course);
                this.steering = "Left";
            }
            noNewPath = false;
        }



        /*
         * 2) If obstacle ahead, avoid the obstacle. If dead ahead, slow down.
         */
        if (noNewPath) { // If still no new path, if there is one, skip all this code for speed's sake.

            // Insertion sort Obstacles in incomingObstacles from lowest to highest
            // y-position (proximity to the Boat, even).
            for (Obstacle obs : allIncomingObstacles) {
                if (obs.getY() + obs.height + backgroundOffset > this.getY()) {
                    if (sortedIncomingObstacles.size() == 0) {
                        sortedIncomingObstacles.add(obs);
                    } else {
                        boolean inserted = false;
                        int index = 0;
                        while (inserted == false) {
                            Obstacle thisObstacle;
                            if (index < sortedIncomingObstacles.size()) {
                                thisObstacle = sortedIncomingObstacles.get(index);
                                // If not reached end of sortedIncomingObstacles.
                                if (thisObstacle.getY() > obs.getY()) {
                                    // Keep looking for place to insert.
                                    index += 1;
                                } else {
                                    // Found place to insert!
                                    sortedIncomingObstacles.add(index, obs);
                                    inserted = true;
                                }
                            } else {
                                // End of ArrayList reached.
                                sortedIncomingObstacles.add(obs);
                                inserted = true;
                            }
                        }
                    }
                }
            }

            for (Obstacle obs : sortedIncomingObstacles) {

                if (obs.getY() + backgroundOffset <= visionDistance && obs.getY() + backgroundOffset > this.yPosition) {
                    // The obstacle is visible from the boat.
                    if (obs.getX() + obs.width < leftSide) {
                        // The obstacle is far left of the boat.

                        if ((obs.getClass() == Goose.class) && ((Goose) obs).direction == "East") {
                            this.SteerRight(course);
                            this.steering = "Right";
                        }

                    } else if (obs.getX() > rightSide) {
                        /*
                         * The obstacle is far right of the boat.
                         *
                         * Do nothing? A moving Obstacle (Goose) might currently be far right but
                         * heading left on a collision course. Maybe check type of Obstacle then; if
                         * it's a Goose, check the direction. If it's headed your way, do something
                         * about that.
                         */

                        if ((obs.getClass() == Goose.class) && ((Goose) obs).direction == "West") {
                            this.SteerLeft(course);
                            this.steering = "Left";
                        }
                    } else {
                        /*
                         * Part or all of the obstacle is on a collision course with the boat. Move in
                         * the appropriate direction. Depends on whether the Obstacle is to the left or
                         * right, on whether you are close to the Lane boundary, etc. If the obstacle is
                         * dead ahead, slow down also. If there is a goose ahead and it is moving
                         * horizontally, steer around it and opposite it.
                         */

                        if ((obs.getClass() == Goose.class) && ((Goose) obs).direction == "East") {
                            this.SteerLeft(course);
                            this.steering = "Left";
                        } else if ((obs.getClass() == Goose.class) && ((Goose) obs).direction == "West") {
                            this.SteerRight(course);
                            this.steering = "Right";
                        } else {
                            // For objects moving vertically, calculate whether steering left of it or right
                            // of it is the easiest course.
                            int leftMargin = Math.round(leftSide - obs.getX());
                            int rightMargin = Math.round(obs.getX()) + obs.width - rightSide;

                            // Check to go left or right.
                            if (leftMargin <= rightMargin) {
                                // If easier to dodge to the left of the obstacle, steer to the left of the
                                // obstacle.
                                this.SteerLeft(course);
                                this.steering = "Left";
                            } else {
                                // If not, steer to the right of the obstacle.
                                this.SteerRight(course);
                                this.steering = "Right";
                            }
                        }

                        noNewPath = false;
                        break;
                    }
                }
            }
        }

        /*
         * 2.5) Move to middle.
         */
        if (noNewPath) {
            int middle = lane.getRightBoundary() - (lane.getRightBoundary() - lane.getLeftBoundary()) / 2
                    - this.width / 2;
            if (Math.abs(leftSide - middle) < 0.1) {
                steering = "None";
            } else if (leftSide < middle) {
                this.SteerRight(course);
                steering = "Right";
            } else {
                this.SteerLeft(course);
                this.SteerLeft(course);
                steering = "Left";
            }
        }

        // 3) If nothing, speed up.
        if (this.getTiredness() < 70)
            this.IncreaseSpeed();
  //"ASSESSMENT2:START"
        if (level == "Easy") {
            setCurrentSpeed(getCurrentSpeed()-0.02f);
        }else if (level == "Hard"){
            setCurrentSpeed(getCurrentSpeed() + 0.02f);
        }
    }
  //"ASSESSMENT2:END"

    /**
     * <p>
     * Assigns a random boat template to the boat.
     * </p>
     * <p>
     * This includes stats and texture.
     * </p>
     *
     * @param possibleBoats List of remaining boat templates that haven't been
     *                      assigned yet.
     * @return Int representing the index of the boat template that was assigned.
     */
    public int SetRandomBoat(ArrayList<Integer> possibleBoats) {
        Random rnd = new Random();
        int randIndex = rnd.nextInt(possibleBoats.size());
        char boatLabel = (char) (65 + possibleBoats.get(randIndex));
        this.setTexture(new Texture(Gdx.files.internal("boat" + boatLabel + " sprite1.png")));
        this.GenerateTextureFrames(boatLabel);
        this.setStats(boatLabel);
        return randIndex;
    }
}

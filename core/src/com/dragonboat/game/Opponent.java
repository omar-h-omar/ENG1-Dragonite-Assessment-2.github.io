package com.dragonboat.game;
import java.util.ArrayList;

public class Opponent extends Boat {

    public Opponent(int yPosition, int width, int height, Lane lane, String name) {
        super(yPosition, width, height, lane, name);
    }

    public void ai() {
        /*
        One method to control the AI behaviour of one boat.
        Every so often in the game loop, Opponent.ai() gets called and the movement path of the Opponent boats will change.

        AI new path selection:
            1) If not in lane, go back to lane.
            2) If obstacle ahead, avoid the obstacle. If dead ahead, slow down.
            3) If nothing, speed up.
        */

        int leftSide = xPosition;
        int rightSide = xPosition + width;

        int fov = 0; //Determine a good field of view for the Opponents to start reacting to incoming obstacles.
        int visionDistance = yPosition + height + fov;

        ArrayList<Obstacle> allIncomingObstacles = this.lane.obstacles;
        ArrayList<Obstacle> sortedIncomingObstacles = new ArrayList<Obstacle>();

        boolean noNewPath = true; //Set to false whenever the Opponent has decided on a new path.

        /*
        1) If not in lane, go back to lane.
        */
        if(this.CheckIfInLane() == false) {
            //Commence route back into lane.
            if(leftSide - this.lane.GetLeftBoundary() <= 0) {
                //Will only be negative if the boat is further left than the leftBoundary.
                this.SteerRight();
            }
            else if(rightSide - this.lane.GetRightBoundary() >= 0) {
                //Will only be positive if the boat is further right than the rightBoundary.
                this.SteerLeft();
            }
            noNewPath = false;
        }

        /*
        2) If obstacle ahead, avoid the obstacle. If dead ahead, slow down.
        */
        if(noNewPath) { //If still no new path, if there is one, skip all this code for speed's sake.

            //Insertion sort Obstacles in incomingObstacles from lowest to highest yPosition (proximity to the Boat, even)
            for(Obstacle obs : allIncomingObstacles) {
                if(sortedIncomingObstacles.size() == 0) {
                    sortedIncomingObstacles.add(obs);
                }
                else {
                    boolean inserted = false;
                    int index = 0;
                    while(inserted == false) {
                        Obstacle thisObstacle = sortedIncomingObstacles.get(index);
                        if(index < sortedIncomingObstacles.size()) {
                            //If not reached end of sortedIncomingObstacles.
                            if(thisObstacle.getY() > obs.getY()) {
                                //Keep looking for place to insert.
                                index += 1; 
                            }
                            else {
                                //Found place to insert!
                                sortedIncomingObstacles.add(index, obs); 
                                inserted = true;
                            }
                        }
                        else {
                            //End of ArrayList reached.
                            sortedIncomingObstacles.add(obs);
                            inserted = true;
                        }  
                    }
                }
            }

            for(Obstacle obs : sortedIncomingObstacles) {
                if(obs.getY() <= visionDistance) {
                    //The obstacle is visible from the boat.
                    if(obs.getY() + obs.width < leftSide) {
                        /*
                        The obstacle is far left of the boat.

                        Do nothing? A moving Obstacle (Goose) might currently be far left but heading right on a collision course.
                        Maybe check type of Obstacle then; if it's a Goose, check the direction. If it's headed your way, do something about that.
                        */
                        
                        if((obs.getClass() == Goose.class) && ((Goose) obs).direction == "East") {
                            this.SteerRight();
                        }
                        
                    }
                    else if(obs.getX() + obs.width > rightSide) {
                        /*
                        The obstacle is far right of the boat.

                        Do nothing? A moving Obstacle (Goose) might currently be far right but heading left on a collision course.
                        Maybe check type of Obstacle then; if it's a Goose, check the direction. If it's headed your way, do something about that.
                        */

                        if((obs.getClass() == Goose.class) && ((Goose) obs).direction == "West") {
                            this.SteerLeft();
                        }
                    }
                    else {
                        /*
                        Part or all of the obstacle is on a collision course with the boat.
                        Move in the appropriate direction. Depends on whether the Obstacle is to the left or right, on whether you are close to the Lane boundary, etc.
                        If the obstacle is dead ahead, slow down also.
                        If there is a goose ahead and it is moving horizontally, steer around it and opposite it.
                        */

                        if((obs.getClass() == Goose.class) && ((Goose) obs).direction == "East") {
                            this.SteerLeft();
                        }
                        
                        else if((obs.getClass() == Goose.class) && ((Goose) obs).direction == "West") {
                            this.SteerRight();
                        }
                        
                        else {
                            //For objects moving vertically, calculate whether steering left of it or right of it is the easiest course. 
                            int leftMargin = leftSide - obs.getX();
                            int rightMargin = obs.getX() + obs.width - rightSide;
                            
                            //Check to slow down.
                            if((leftMargin <= 0 && rightMargin <= 0) || (leftMargin >= 0 && rightMargin >= 0)){
                                //Obstacle is dead ahead. Slow down.
                                this.DecreaseSpeed();
                            }
        
                            //Check to go left or right.
                            if(leftMargin <= rightMargin) {
                                //If easier to dodge to the left of the obstacle, steer to the left of the obstacle.
                                this.SteerLeft();
                            }
                            else {
                                //If not, steer to the right of the obstacle.
                                this.SteerRight();
                            }
                        }

                        noNewPath = false;
                        break;
                    }
                }
            }
        }
        
        /*
        3) If nothing, speed up.
        */
        if(noNewPath) {
            //Still no new path? Just speed up in the forwards direction!
            this.IncreaseSpeed();
        }

    }
}

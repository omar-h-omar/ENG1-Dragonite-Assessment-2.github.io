package com.dragonboat.game;

public class Opponent extends Boat {

    public Opponent(int xPosition, int yPosition, int width, int height, Lane lane) {
        super(xPosition, yPosition, width, height, lane);
    }

    public void ai() {
        /*
        One method to control the AI behaviour of one boat.
        Every so often in the game loop, Opponent.ai() gets called and the movement path of the Opponent boats will change.
        */

        int left = xPosition;
        int right = xPosition + width;

        int fov = 0; //Determine a good field of view for the Opponents to start reacting to incoming obstacles.
        int visionDistance = yPosition + height + fov;

        Obstacle[] incomingObstacles;
        //Obstacle[] incomingObstacles = this.lane.obstacles; 
        //Sort Obstacles in incomingObstacles by proximity to the boat.

        boolean noNewPath = true; //Set to false whenever the Opponent has decided on a new path.

        for(Obstacle obs : incomingObstacles) {
            if(obs.yPosition <= visionDistance) {
                //The obstacle is visible from the boat.
                if(obs.xPosition + obs.width < left || obs.xPosition + obs.width > right) {
                    /*
                    The obstacle is far left or far right of the boat.

                    Do nothing? A moving Obstacle (Goose) might currently be far left but heading right on a collision course.

                    Maybe check type of Obstacle then; if it's a Goose, check the direction. If it's headed your way, do something about that.

                    if(A Goose is moving towards me) {
                        noNewPath = false;
                        break;
                    }
                    */
                }
                else {
                    /*
                    Part or all of the obstacle is on a collision course with the boat.
                    Move in the appropriate direction. Depends on whether the Obstacle is to the left or right, on whether you are close to the Lane boundary, etc.
                    */
                    noNewPath = false;
                    break;
                }
            }
        }

        if(noNewPath && this.CheckIfInLane() == false) {
            //Commence route back into Lane.
            noNewPath = false;
        }
    }
}

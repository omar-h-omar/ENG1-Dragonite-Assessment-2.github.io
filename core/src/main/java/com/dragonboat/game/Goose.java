package com.dragonboat.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

//"ASSESSMENT2:START"
import com.badlogic.gdx.Gdx;
//"ASSESSMENT2:END"
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a goose obstacle on the course.
 */
public class Goose extends Obstacle {

    public String direction = "South"; // Facing south by default.
    public Lane givenLane;

    /**
     * <p>
     * Creates a goose instance.
     * </p>
     * <p>
     * Geese can face North, East, South or West. Width and height switch when
     * changing between North or South and East or West.
     * </p>
     *
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the goose.
     * @param lane      Lane the goose will spawn in.
     */
    public Goose(int xPosition, int yPosition, Texture texture, Lane lane) {
        super(10, xPosition, yPosition, texture.getWidth(), texture.getHeight(), texture, "Goose");
        this.givenLane = lane;
    }

    /**
     * Changes the direction of the Goose to an appropriate, random cardinal
     * direction.
     */
    public void changeDirection() {
        HashMap<String, ArrayList<String>> cardinals = new HashMap<String, ArrayList<String>>();

        cardinals.put("East", new ArrayList<String>(Arrays.asList("South")));
        cardinals.put("South", new ArrayList<String>(Arrays.asList("East", "West")));
        cardinals.put("West", new ArrayList<String>(Arrays.asList("South")));

        direction = cardinals.get(direction).get(new Random().nextInt(cardinals.get(direction).size()));
    }

    /**
     * Moves the goose.
     *
     * @param moveVal          Distance to move Goose by.
     * @param backgroundOffset Offset from screen to course coordinates.
     * @param level            The current difficulty level
     */
    public void Move(float moveVal, int backgroundOffset, String level) {

        boolean canGoEast, canGoWest;

        if (this.getX() > givenLane.getLeftBoundary() && this.getX() + this.width < givenLane.getRightBoundary()) {
            // Goose is within the lane boundaries.
            canGoEast = true;
            canGoWest = true;
        } else if (this.getX() <= givenLane.getLeftBoundary()) {
            // Goose is on left boundary.
            canGoEast = true;
            canGoWest = false;
        } else {
            // Goose is on right boundary.
            canGoEast = false;
            canGoWest = true;
        }
        
        
      //"ASSESSMENT2:START"
        int randomMove;
        // Chance of goose changing direction.

        if (level == "Medium") {
            randomMove = 30;
        }else if (level == "Hard"){
            randomMove = 10;
        }else{
            randomMove = 50;
        }

        if (new Random().nextInt(randomMove) == randomMove - 1) {
            changeDirection();
        }
      //"ASSESSMENT2:END"

        if (canGoEast && this.direction == "East") {
            this.setX(this.getX() + moveVal);
            texture = new Texture(Gdx.files.internal("gooseEast sprite.png"));

            if (backgroundOffset > 0 && backgroundOffset < 2160) {
                this.setY(this.getY() - moveVal);
            }
        } else if (canGoWest && this.direction == "West") {
            this.setX(this.getX() - moveVal);
            texture = new Texture(Gdx.files.internal("gooseWest sprite.png"));
            if (backgroundOffset > 0 && backgroundOffset < 2160) {
                this.setY(this.getY() - moveVal);
            }
        } else {
            this.setY(this.getY() - moveVal);
        }
    }

}

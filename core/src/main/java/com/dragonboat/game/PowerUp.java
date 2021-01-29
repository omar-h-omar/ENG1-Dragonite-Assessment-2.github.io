package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PowerUp extends Obstacle{
    protected float yPosition, xPosition;
    public int width, height;
    public String type;
    private Texture[] textureFrames;
    private int frameCounter;
    public Texture texture;
    private final float maxFrameTime; // The maximum time allowed allowed for a frame.
    private float currentFrameTime; // The current time for a frame.
    private Texture[] frames; // a integer representing which boat texture would be loaded.
    public Lane givenLane;
    private int frameCount;
    private int noOfFrames;



    /**
     * Creates an obstacle instance.
     * @param type      Type of obstacle
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the obstacle.
     */
    public PowerUp(String type, int xPosition, int yPosition, Texture texture, Lane lane) {
        super(0, xPosition, yPosition, texture.getWidth(), texture.getHeight(), texture, type);
        this.givenLane = lane;
        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.texture = texture;

        currentFrameTime = 0;
        maxFrameTime = 0.05f;
        frameCount = 0;

        switch (type){
            case "Invincibility":
                noOfFrames = 20;
                break;
            case "Maneuverability":
                noOfFrames = 33;
                break;
            case "SpeedBoost":
                noOfFrames = 13;
                break;
            case "TimeReduction":
                noOfFrames = 21;
                break;
            case "Repair":
                noOfFrames = 29;
                break;
        }

        frames = new Texture[noOfFrames];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new Texture(Gdx.files.internal(type + "\\sprite_" + i + ".png"));
        }
    }

    /**
     * @return Texture asset for obstacle.
     */

    public Texture getTexture() {
        return frames[frameCount];
    }

    /**
     * Generates all frames for animating the power-up.
     *
     */

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frameCount = (frameCount + 1)%noOfFrames;
            currentFrameTime = 0;
        }
    }


    /**
     * Moves the power-up.
     *
     * @param moveVal          Distance to move the object by.
     * @param backgroundOffset Offset from screen to course coordinates.
     */
    public void Move(float moveVal, int backgroundOffset) {
        this.setY(this.getY() - moveVal);
    }

    // getters and setters

    /**
     * @return String representing type of power-up.
     */
    public String getType() {
        return this.type;
    }

    public Texture getMysteryTexture(){
        return new Texture(Gdx.files.internal("itemBox.png"));
    }
    /**
     * @return Float representing the x-position.
     */
    public float getX() {
        return this.xPosition;
    }

    /**
     * @return Float representing the y-position.
     */
    public float getY() {
        return this.yPosition;
    }

    /**
     * @param yPosition Y-position.
     */
    public void setY(float yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * @param xPosition X-position.
     */
    public void setX(float xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * @return Int representing the height of the obstacle.
     */
    public int getHeight() {
        return this.height;
    }

}
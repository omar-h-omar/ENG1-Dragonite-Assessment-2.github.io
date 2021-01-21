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
    private int frame; // a integer representing which boat texture would be loaded.
    public Lane givenLane;


    /**
     * Creates an obstacle instance.
     * @param type      Type of obstacle
     * @param xPosition X-position.
     * @param yPosition Y-position.
     * @param texture   Texture asset for the obstacle.
     */
    public PowerUp(String type, int xPosition, int yPosition, Texture texture, Lane lane) {
        super(0, xPosition, yPosition, texture.getWidth(), texture.getHeight(), texture);
        this.givenLane = lane;
        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.texture = texture;

        frameCounter = 0;
        maxFrameTime = 0.5f/2;
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
     * @return Texture asset for obstacle.
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * @return Int representing the height of the obstacle.
     */
    public int getHeight() {
        return this.height;
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
     * @param type for type of power-up, used to get correct asset.
     */
    public void GenerateTextureFrames(String type) {
        Texture[] frames = new Texture[textureFrames.length];
        for (int i = 1; i <= frames.length; i++) {
            frames[i - 1] = new Texture(Gdx.files.internal(type + "/sprite_" + i + ".png"));
        }
        this.setTextureFrames(frames);
    }

    /**
     * @param t Texture to use.
     */
    public void setTexture(Texture t) {
        this.texture = t;
    }

    /**
     * @param frames Texture frames for animation.
     */
    public void setTextureFrames(Texture[] frames) {
        this.textureFrames = frames;
    }


    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame ++;
            currentFrameTime = 0;
        }
        if (frame >= 2) {
            frame = 0;
        }
    }

    public int getFrame() {
        return frame;
    }

}
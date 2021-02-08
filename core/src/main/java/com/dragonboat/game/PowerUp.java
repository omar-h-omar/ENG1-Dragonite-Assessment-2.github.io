//"ASSESSMENT2:START"
package com.dragonboat.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents an obstacle on the course.
 *
 * @see Maneuverability
 * @see Invincibility
 * @see Repair
 * @see TimeReduction
 * @see SpeedBoost
 */
public class PowerUp extends Obstacle{
    protected float yPosition, xPosition;
    public int width, height;
    public String type;
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
        this.texture = texture;

        currentFrameTime = 0;
        maxFrameTime = 0.05f;
        frameCount = 0;

        frames = GenerateTextureFrames();
    }

    /**
     * Gets the current power up texture.
     * @return Texture asset for obstacle.
     */
    @Override
    public Texture getTexture() {
        return frames[frameCount];
    }

    /**
     * Generates all frames for animating the power-up.
     *
     * @return a texture array with all the textures of the power up
     */
    private Texture[] GenerateTextureFrames(){
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

        return frames;
    }

    /**
     * Updates the current frame time
     * @param dt A float representing delta time.
     */
    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frameCount = (frameCount + 1)%noOfFrames;
            currentFrameTime = 0;
        }
    }

    /**
     * Gets the type of power up
     * @return String representing type of power-up.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the texture of the mystery box.
     * @return A texture of the mystery box
     */
    public Texture getMysteryTexture(){
        return new Texture(Gdx.files.internal("itemBox.png"));
    }

}
//"ASSESSMENT2:END"
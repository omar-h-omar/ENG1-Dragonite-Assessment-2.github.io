package com.dragonboat.game;
import com.badlogic.gdx.graphics.Texture;

public class Boat {
    private int robustness, maxSpeed, durability, tiredness, penalties, legTime;
    private float acceleration, maneuverability;
    public int xPosition, yPosition, width, height;
    public float currentSpeed, progress;
    private Lane lane;
    private Texture texture;

    public Boat(int xPosition_, int yPosition_, int width_, int height_, Lane lane_) {
        xPosition = xPosition_;
        yPosition = yPosition_;
        width = width_;
        height = height_;
        currentSpeed = 0;
        penalties = 0;
        durability = 100;
        tiredness = 0;
        progress = 0;
        lane = lane_;
        acceleration = 0.05f;
    }
}
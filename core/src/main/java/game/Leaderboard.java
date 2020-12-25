package com.dragonboat.game;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a leaderboard for after each race.
 */
public class Leaderboard {
    private Boat[] sortedBoats;
    static private Comparator<Boat> ascRaceTime;
    private Texture texture;

    /**
     * Creates a leaderboard with an array of all boats.
     * 
     * @param player    Player object.
     * @param opponents Array of opponent boats.
     */
    public Leaderboard(Player player, Opponent[] opponents) {
        this.sortedBoats = new Boat[opponents.length + 1];
        this.sortedBoats[0] = player;
        for (int i = 0; i < opponents.length; i++) {
            this.sortedBoats[i + 1] = opponents[i];
        }

        this.texture = new Texture(Gdx.files.internal("leaderboard nolines.png"));
    }

    /**
     * Sorts the array of boats by fastest race time, increasing.
     */
    public void UpdateOrder() {
        Arrays.sort(this.sortedBoats, ascRaceTime);
    }

    static {
        /**
         * Defines the comparator used to compare fastest times of boats.
         */
        ascRaceTime = new Comparator<Boat>() {
            @Override
            public int compare(Boat boat1, Boat boat2) {
                return Float.compare(boat1.getFastestTime(), boat2.getFastestTime());
            }
        };
    }

    // getters and setters

    /**
     * Gets the names of the boats and their fastest times, in order of fastest
     * time.
     * 
     * @param places Number of boats to get.
     * @return Array representing boats.
     */
    public String[] getTimes(int places) {
        this.UpdateOrder();
        String[] out = new String[places];
        DecimalFormat df = new DecimalFormat("###.##");
        for (int i = 0; i < places; i++) {
            out[i] = sortedBoats[i].getName() + ": " + df.format(sortedBoats[i].getFastestTime());
        }

        return out;
    }

    /**
     * Gets the leaderboard texture.
     * 
     * @return A Texture representing the sprite.
     */
    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Gets top boats, in order of fastest race time.
     * 
     * @param places Number of boats to get.
     * @return Array representing the boats.
     */
    public Boat[] getFinalists(int places) {
        Boat[] finalists = new Boat[places];
        for (int i = 0; i < places; i++) {
            finalists[i] = sortedBoats[i];
        }

        return finalists;
    }

    /**
     * Gets the top 3 boats, in order of fastest times.
     * 
     * @return Array representing boats.
     */
    public Boat[] getPodium() {
        return getFinalists(3);
    }
}

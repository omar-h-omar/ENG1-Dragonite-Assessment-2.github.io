package com.dragonboat.game;

import java.util.Arrays;
import java.util.Comparator;

public class Leaderboard {
    //attributes
    private Boat[] boats;
    private Boat[] sortedBoats;
    private int[] raceTimes;
    static private Comparator<Boat> ascRaceTime;

    public Leaderboard(Boat[] boats){
        this.boats = boats;
        this.sortedBoats = boats;
    }

    private void updateTimes(){
        Arrays.sort(this.sortedBoats, ascRaceTime);
    }

    public Boat[] GetFinalists(){
        Boat[] finalists = new Boat[4];
        for(int i = 0; i < 4; i++){
            finalists[i] = sortedBoats[i];
        }

        return finalists;
    }

    //custom number of places
    public Boat[] GetFinalists(int places){
        Boat[] finalists = new Boat[places];
        for(int i = 0; i < places; i++){
            finalists[i] = sortedBoats[i];
        }

        return finalists;
    }

    public Boat[] GetPodium(){
        return GetFinalists(3);
    }

    static{
        //allows boat times to be compared
        ascRaceTime = new Comparator<Boat>(){
            @Override
            public int compare(Boat boat1, Boat boat2){
                return Long.compare(boat1.GetFastestTime(), boat2.GetFastestTime());
            }
        };
    }
}

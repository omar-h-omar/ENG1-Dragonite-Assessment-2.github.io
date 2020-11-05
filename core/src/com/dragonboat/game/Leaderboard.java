package com.dragonboat.game;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;

public class Leaderboard {
    //attributes
    private Boat[] sortedBoats;
    static private Comparator<Boat> ascRaceTime;

    public Leaderboard(Boat[] boats){
        this.sortedBoats = boats;
    }

    //sorts order of boats
    public void UpdateOrder(){
        Arrays.sort(this.sortedBoats, ascRaceTime);
    }

    //gets top boats
    public Boat[] GetFinalists(int places){
        Boat[] finalists = new Boat[places];
        for(int i = 0; i < places; i++){
            finalists[i] = sortedBoats[i];
        }

        return finalists;
    }

    //gets top 3 places
    public Boat[] GetPodium(){
        return GetFinalists(3);
    }

    //defining comparators
    static{
        //allows boat times to be compared
        ascRaceTime = new Comparator<Boat>(){
            @Override
            public int compare(Boat boat1, Boat boat2){
                return Float.compare(boat1.getFastestTime(), boat2.getFastestTime());
            }
        };
    }

    //get names and times of top boats
    public String[] GetTimes(int places){
        String[] out = new String[places];
        DecimalFormat df = new DecimalFormat("###.##");
        for(int i = 0; i < places; i++){
            out[i] = sortedBoats[i].getName() + df.format(sortedBoats[i].getFastestTime());
        }

        return out;
    }
}

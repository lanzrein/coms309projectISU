package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * Created by Joseph on 11/5/2017.
 * <p>
 * Contains an ArrayList of units to be sent against a player in each wave
 */

public class UnitWave {

    private ArrayList<Unit> units;
    private ArrayList<Unit> spawned;
    private int density, spawnCounter;

    /**
     * Constructs a Unit wave that will consist of the given units and spawn them in according to the unit density
     * @param units ArrayList of units that this wave will consist of
     * @param unitDensity an integer that represents the frequency at which these units will spawn in after each other. There is an inverse relation ship between the unitDensity and how frequently units spawn
     */
    public UnitWave(ArrayList<Unit> units, int unitDensity) {
        this.units = units;
        this.density = unitDensity;
        spawnCounter = unitDensity;
        spawned = new ArrayList<>();
    }

    /**
     * Constructs an empty UnitWave
     */
    public UnitWave() {
        units = new ArrayList<>();
        spawned = new ArrayList<>();
    }


    /**
     * Returns the unit wave as a json string
     * @return wave as a json string
     */
    @Deprecated
    public String toJson() {
        ArrayList<Integer> bytes = new ArrayList<>();
        for (Unit u : units) {
            bytes.add(u.ID);
        }

        Gson gson = new Gson();
        return gson.toJson(bytes);
    }

    /**
     * @return contents of ArrayList spawned; spawned contains units that have been spawned in game and are now interacting with game
     */
    public ArrayList<Unit> getSpawnedUnits() {
        return spawned;
    }


    /**
     * @return contents of ArrayList units; units contains a list of units that may or may not yet be spawned for the particular wave
     */
    public ArrayList<Unit> getAllUnits() {
        return units;
    }


    /**
     *
     */
    public void update() {
        if (!units.isEmpty()) {
            if (spawnCounter >= density) {
                spawned.add(units.remove(0));
                spawnCounter = 0;
            } else {
                spawnCounter++;
            }
        }
    }


    /**
     * @return if the game is over based on whether their are any units that are still alive
     */
    public boolean roundOver() {
        if (units.isEmpty()) {
            for (Unit u : spawned) {
                if (u.isAlive()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

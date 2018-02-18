package edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;

/**
 * A helper for the map editing.
 * This allows to have a map stored thorough the program.
 * Created by johan on 27.11.2017.
 */

public class MapHelper {
    private MapHelper(){}

    /**
     * Our map
     */
    public static Map map = Map.empty;

    /**
     * Delete the map - like freeing it.
     */
    public static void delete() {
        map = Map.empty;
    }

    /**
     * Check if there is a map available
     * @return true iff there is an availablemap
     */
    public static boolean isAvailable() {
        return !map.equals(Map.empty);
    }

    /**
     * Sets the map
     * @param m the map to be set
     */
    public static void set(Map m) {
        map = m;
    }
}

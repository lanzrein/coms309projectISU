package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap;

/**
 * Created by Joseph on 9/21/2017.
 *
 * Enum containg all the different Terrain Types
 */

public enum TerrainType {

    GRASS,
    PATH,
    WATER,
    ROCK,
    BASE,
    SPAWN;

    /**
     * Returns true if the terrain is walkable
     * @return true if walkable, false otherwise
     */
    public boolean isWalkable() {
        return this == PATH || this == SPAWN;
    }




}

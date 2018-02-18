package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.GameObject;

/**
 * Created by Joe on 11/22/2017.
 *
 * interface for buyables
 */

public interface Buyable {
    /**
     * Returns the name of the buyable
     * @return the name of the buyable
     */
    public String getName();

    /**
     * Returns the cost of the buyable
     * @return cost of the buyable
     */
    public int getCost();

    /**
     * Returns the GameObject that this buyable represents.
     * @return
     */
    public GameObject getItem();
}

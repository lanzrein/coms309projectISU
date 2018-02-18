package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.GameObject;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Turret;

/**
 * Created by Joe on 11/22/2017.
 */

/**
 * This buyable is used for selling turrets in the GameStore
 */
public class TurretBuyable implements Buyable{

    private String name;
    private int cost;
    private Turret turret;

    /**
     * Creates a new TurretBuyable
     * @param name the name of this buyable
     * @param cost the cost of this buyable
     * @param turret the turret that this buyable is representing
     */
    public TurretBuyable(String name, int cost, Turret turret) {
        this.name = name;
        this.cost = cost;
        this.turret = turret;
    }

    /**
     * Returns the name of this buyable
     * @return name of this buyable
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the cost of this buyable
     * @return cost of this buyable
     */
    public int getCost(){
        return  cost;
    }

    /**
     * Returns the Turret that this buyable represents
     * @return the Turret that this buyable represents
     */
    public Turret getItem(){
        return turret;
    }

}

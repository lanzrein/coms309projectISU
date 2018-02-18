package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * Created by Joseph on 11/29/2017.
 */

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Turret;
/**
 * This buyable is used for selling Units in the GameStore
 */
public class UnitBuyable implements Buyable {

    private String name;
    private int cost;
    private Unit unit;
    /**
     * Creates a new UnitBuyable
     * @param name the name of this buyable
     * @param cost the cost of this buyable
     * @param unit the unit that this buyable is representing
     */
    public UnitBuyable(String name, int cost, Unit unit) {
        this.name = name;
        this.cost = cost;
        this.unit = unit;
    }


    /**
     * Returns the name of this UnitBuyable
     * @return name of the UnitBuyable
     */
    @Override
    public String getName() {
        return name;
    }


    /**
     * Returns the cost of this UnitBuyable
     * @return cost in game currency units
     */
    @Override
    public int getCost() {
        return cost;
    }


    /**
     * Returns the unit that this Buyable is representing
     * @return a copy of the unit. Functionally the player purchases said unit
     */
    @Override
    public Unit getItem() {
        return unit;
    }

    @Override
    public int hashCode() {
        int hash = name.hashCode();
        hash = hash * 31 + cost;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj!=null) {
            if (obj instanceof UnitBuyable) {
                UnitBuyable t = (UnitBuyable) obj;
                return name.equals(t.getName());
            }
        }
        return false;
    }

    /**
     *
     * @return unit's ID which is used to determine what race and type of unit it is
     */
    public int getId() {
        return unit.getID();
    }
}


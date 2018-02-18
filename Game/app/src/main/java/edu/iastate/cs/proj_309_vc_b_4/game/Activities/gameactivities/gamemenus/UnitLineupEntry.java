package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.Buyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.UnitBuyable;

/**
 * Created by Joseph on 11/30/2017.
 *
 * The UnitLineupEntry is an entry for the UnitLineupViewController.
 */
public class UnitLineupEntry {

    private UnitLineupEditor container;
    public UnitLineupEntry next,prev;
    private UnitBuyable unit;
    private int count;

    /**
     * Constructs a new UnitLineupEntry
     * @param container The UnitLineupEditor that will contain this entry
     * @param unit The UnitBuyable that this entry will represent
     * @param count The number of Units that this entry represents
     */
    public UnitLineupEntry(UnitLineupEditor container,UnitBuyable unit,int count){
        this.container = container;
        this.unit = unit;
        next = null;
        prev = null;
        this.count = count;
    }

    /**
     * Returns the unit that this entry represents
     * @return an instance of the UnitBuyable that this entry represents
     */
    public Buyable getUnit(){
        return unit;
    }

    /**
     * Returns the number of units this entry represnts
     * @return
     */
    public int getCount(){
        return count;
    }

    /**
     * Increase the number of units this entry represents by 1
     */
    public void incCount(){
        if(container.getRemaining(unit)>0)
            count++;
    }

    /**
     * Decreases the number of units this entry represents by 1
     */
    public void decCount(){
        if (count > 1)
            count--;
    }
}

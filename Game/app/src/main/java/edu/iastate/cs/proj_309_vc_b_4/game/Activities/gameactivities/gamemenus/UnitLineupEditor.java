package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import java.util.ArrayList;
import java.util.HashMap;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.UnitBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * Created by Joseph on 11/30/2017.
 *
 * This class contains the lineup that is displayed in the lineup editor
 */
public class UnitLineupEditor {

    private UnitLineupEntry root,tail;
    private int size;
    private HashMap<UnitBuyable,Integer> unitsBought;

    /**
     * Contructs a new, empty, UnitLineupEditor.
     * @param unitsBought
     */
    public UnitLineupEditor(HashMap<UnitBuyable,Integer> unitsBought){
        this.unitsBought = unitsBought;
        root = tail = null;
        size = 0;
    }

    /**
     * Adds the given entry at the end of the list
     * @param e The given entry to add at the end of the list
     */
    public void appendEntry(UnitLineupEntry e){
        if(root==null){
            root = tail = e;
        } else if (root == tail) {
            tail = e;
            root.next = e;
            tail.prev = root;
        }else {
            tail.next = e;
            e.prev = tail;

            tail = e;
            e.next = null;
        }
        size++;
    }

    /**
     * Returns the Entry at the given index in the list
     * @param index integer index
     * @return Returns the entry if the index is valid, else returns null.
     */
    public UnitLineupEntry getEntry(int index){
        UnitLineupEntry e = root;
        if (index > size) {
            return null;
        }
        for(int i = 0;i<index;i++){
            if(e!=null) {
                e = e.next;
            } else {
                return null;
            }
        }
        return e;
    }

    /**
     * Returns the size of the lineup.
     * This will be the number of entries, not necessarily the number of units in the lineup as there can be
     * multiple units in one entry.
     * @return The number of entries in the lineup
     */
    public int getSize(){
        return size;
    }

    /**
     * Removes a unit from the lineup
     * @param e the unit to be removed from the lineup
     */
    public void remove(UnitLineupEntry e){
        if(e!=null) {
            UnitLineupEntry temp;
            if (tail == root && tail == e) {
                tail = root = null;
            } else if (e == root) {
                e.next.prev = null;
                root = root.next;
            } else if(e == tail){
                e.prev.next = null;
                tail = tail.prev;
            } else {
                temp = e.prev;
                temp.next = e.next;
                temp.next.prev = temp;
            }
            size--;
        }
    }


    /**
     * Swaps the units position in the lineup
     * @param a An instance of a UnitLineupEntry
     * @param b An instance of a UnitLineupEntry
     */
    public void swap(UnitLineupEntry a, UnitLineupEntry b) {
        if (a == b)
            return;

        if (a.next == b) {
            a.next = b.next;
            b.prev = a.prev;

            if (a.next != null)
                a.next.prev = a;

            if (b.prev != null)
                b.prev.next = b;


            b.next = a;
            a.prev = b;
        } else {
            UnitLineupEntry p = b.prev;
            UnitLineupEntry n = b.next;

            b.prev = a.prev;
            b.next = a.next;

            a.prev = p;
            a.next = n;

            if (b.next != null)
                b.next.prev = b;
            if (b.prev != null)
                b.prev.next = b;

            if (a.next != null)
                a.next.prev = a;
            if (a.prev != null)
                a.prev.next = a;

        }
    }

    /**
     * Returns the ammount of remaning units that can be put into the lineup
     * @param unitBuyable
     * @return
     */
    public int getRemaining(UnitBuyable unitBuyable){
        int remaining = unitsBought.get(unitBuyable);
        UnitLineupEntry t = root;
        while(t!=null){
            if(t.getUnit().equals(unitBuyable)){
                remaining = remaining- t.getCount();
            }
            t=t.next;
        }
        return remaining;
    }

    /**
     * Returns the unit wave as an arraylist of units. The units still need their path and game instance set.
     * @return ArrayList of units
     */
    public ArrayList<Unit> compileUnitList(){
        ArrayList<Unit> units = new ArrayList<>();
        UnitLineupEntry entry = root;
        while(entry!=null){
            for(int i = 0;i<entry.getCount();i++){
                units.add(((Unit)entry.getUnit().getItem()).hardCopy());
            }
            entry=entry.next;
        }
        return units;
    }




}

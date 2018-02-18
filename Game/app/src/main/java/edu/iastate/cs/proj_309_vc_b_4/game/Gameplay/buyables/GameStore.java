package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.GamePanel;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Turrets.Dwarf_Towers;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units.Dwarf_Units;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units.Goblin_Units;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units.Human_Units;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.Buyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.TurretBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.UnitWave;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Turret;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * Created by Joe on 11/19/2017.
 *
 * The GameStore class is a container for all buyables in the game.
 */
public class GameStore {

    private ArrayList<Buyable> units;
    private ArrayList<Buyable> turrets;

    /**
     * populates the store with buyables units and turrets which are stored in arraylists
     */
    public GameStore(){
        units = new ArrayList<>();
        turrets = new ArrayList<>();

        //TODO fill store with different races depending on the player
        units.add(new UnitBuyable("Dwarf",10, Dwarf_Units.make_Dwarf_Ground_Unit(null,null)));
        units.add(new UnitBuyable("Flying Dwarf",30, Dwarf_Units.make_Dwarf_Flying_Unit(null,null)));
        units.add(new UnitBuyable("Burrowoing Dwarf ",20, Dwarf_Units.make_Dwarf_Burrowing_Unit(null,null)));

        units.add(new UnitBuyable("Soldier",10, Human_Units.make_Human_Ground_Unit(null,null)));
        units.add(new UnitBuyable("Airship",30, Human_Units.make_Human_Flying_Unit(null,null)));
        units.add(new UnitBuyable("Ground Troop",20, Human_Units.make_Human_Burrowing_Unit(null,null)));

        units.add(new UnitBuyable("Goblin",10, Goblin_Units.make_Goblin_Ground_Unit(null,null)));
        units.add(new UnitBuyable("Demon",30, Goblin_Units.make_Goblin_Flying_Unit(null,null)));
        units.add(new UnitBuyable("Goblin-In-A-Hole",20, Goblin_Units.make_Goblin_Burrowing_Unit(null,null)));


        turrets.add(new TurretBuyable("Dwarf Tower ",10, Dwarf_Towers.dwarf_basic_turret(null,null)));
        turrets.add(new TurretBuyable("Dwarf AA Tower ",25, Dwarf_Towers.dwarf_anti_air(null,null)));
        turrets.add(new TurretBuyable("Dwarf Burrowing Tower ",20, Dwarf_Towers.dwarf_anti_burrowing(null,null)));
        turrets.add(new TurretBuyable("Super Dwarf Tower ",75, Dwarf_Towers.dwarf_all_turret(null,null)));

    }


    /**
     * Returns a list of all UnitBuyables
     * @return ArrayList of UnitBuyables
     */
    public ArrayList<Buyable> getUnitBuyables(){
        return units;
    }

    /**
     * Returns a list of all TurretBuyables
     * @return ArrayList of TurretBuyables
     */
    public ArrayList<Buyable> getTurretBuyables(){
        return turrets;
    }




}

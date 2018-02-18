package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units;


import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * Created by Alex on 11/29/2017.
 *
 *Provides static methods as templates for other classes to spawn dwarven units by type:flying, ground, and burrowing
 *
 */

public class Dwarf_Units {

    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @return a copy of a flying dwarf unit
     */
    public static Unit make_Dwarf_Flying_Unit(GameLogic game, ArrayList<Position> path) {
        return new Unit(game, path, 0, 10, 7, DamageTypes.BLUDGEONING, 10, true, false);

    }

    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @return a copy of a ground dwarf unit
     */
    public static Unit make_Dwarf_Ground_Unit(GameLogic game, ArrayList<Position> path) {

        return new Unit(game, path, 1, 10, 7, DamageTypes.BLUDGEONING, 10, false, false);

    }

    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @return a copy of a burrowing dwarf unit
     */
    public static Unit make_Dwarf_Burrowing_Unit(GameLogic game, ArrayList<Position> path) {
        return new Unit(game, path, 2, 10, 7, DamageTypes.BLUDGEONING, 10, false, true);

    }
}
package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * Created by Alex on 11/29/2017.
 * Provides static methods as templates for other classes to spawn human units by type:flying, ground, and burrowing
 *
 */

public class Human_Units {


    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @return a copy of a flying human unit
     */
    public static Unit make_Human_Flying_Unit(GameLogic game, ArrayList<Position> path) {
        return new Unit(game, path, 6, 10, 7, DamageTypes.BLUDGEONING, 10, true, false);

    }

    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @return a copy of a ground human unit
     */
    public static Unit make_Human_Ground_Unit(GameLogic game, ArrayList<Position> path) {

        return new Unit(game, path, 7, 10, 7, DamageTypes.BLUDGEONING, 10, true, false);

    }

    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @return a copy of a human burrowing unit
     */
    public static Unit make_Human_Burrowing_Unit(GameLogic game, ArrayList<Position> path) {
        return new Unit(game, path, 8, 10, 7, DamageTypes.BLUDGEONING, 10, false, true);

    }
}


package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * Created by Alex on 11/29/2017.
 * Provides static methods as templates for other classes to spawn goblin units by type:flying, ground, and burrowing
 *
 */

public class Goblin_Units {


    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @return a copy of a flying goblin unit
     */
    public static Unit make_Goblin_Flying_Unit(GameLogic game, ArrayList<Position> path) {
        return new Unit(game, path, 3, 10, 7, DamageTypes.BLUDGEONING, 10, true, false);

    }

    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @returna copy of a ground goblin unit
     */
    public static Unit make_Goblin_Ground_Unit(GameLogic game, ArrayList<Position> path) {

        return new Unit(game, path, 4, 10, 7, DamageTypes.BLUDGEONING, 10, true, false);

    }

    /**
     *
     * @param game
     * @param path ArrayList providing the path the unit will follow when spawned in game
     * @return a copy of a burrowing goblin unit
     */
    public static Unit make_Goblin_Burrowing_Unit(GameLogic game, ArrayList<Position> path) {
        return new Unit(game, path, 5, 10, 7, DamageTypes.BLUDGEONING, 10, false, true);

    }
}


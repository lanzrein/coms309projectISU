package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Turrets;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Turret;

/**
 * Created by Alex on 11/30/2017.
 *
 *
 * Provides static methods as templates for other classes to create human turrets
 */

public class Human_Towers {

    /**
     *
     * @param gameLogic
     * @param position position of the turret on screen
     * @return a human turret that can target flying and ground units
     */
    Turret human_anti_air(GameLogic gameLogic, Position position){

        return new Turret(gameLogic, 10, 10, DamageTypes.PIERCING, 10, position, false, true
        );
    }
    /**
     *
     * @param gameLogic
     * @param position position of the turret on screen
     * @return a human turret that can target burrowing and ground units
     */
    Turret human_anti_burrowing(GameLogic gameLogic, Position position){

        return new Turret(gameLogic, 10, 10, DamageTypes.PIERCING, 10, position, true, false
        );
    }


    /**
     *
     * @param gameLogic
     * @param position position of the turret on screen
     * @return a human turret that can only target ground
     */
    Turret human_basic_turret(GameLogic gameLogic, Position position){

        return new Turret(gameLogic, 10, 10, DamageTypes.PIERCING, 10, position, false, false
        );
    }

    /**
     *
     * @param gameLogic
     * @param position position of the turret on screen
     * @return a human turret that can target any and all units
     */
    Turret human_all_turret(GameLogic gameLogic, Position position){

        return new Turret(gameLogic, 10, 10, DamageTypes.PIERCING, 10, position, true, true
        );
    }

}

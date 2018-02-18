package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects;

import android.graphics.Canvas;

/**
 * Created by Alex on 10/7/2017.
 *
 * Interface for GameObjects: units and turrets
 *
 *
 */

public interface GameObject {

    /**
     * updates the GaemeObject's position allowing them to change positions on the screen
     */
    public void update();

    /**
     *
     * @return GameObject's current position on screen
     */
    public Position getPosition();

}

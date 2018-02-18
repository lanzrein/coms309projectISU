package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay;

import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Time.S_PER_MIN;

/**
 * Interface to represent how long something lasts.
 *
 */
public interface Speed{
    public static final int ROUND_LENGTH = S_PER_MIN*3;
    public static final int GAME_LENGTH = ROUND_LENGTH*5;
    public static final int FRAME_PER_SECOND = 30;
    public static final int DURATION_FRAME_NANOSECOND = Time.NS_PER_S/FRAME_PER_SECOND;



    /**HERE WE CAN IMPLEMENT THE SPEED OF VARIOUS UNIT AND SAY THEY CAN HAVE A SPEED MORE OR LESS QUICK***********/
    //TODO

}

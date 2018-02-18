package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay;

import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Time.S_PER_MIN;

/**
 * Simple interface to represent time
 * Created by johan on 14.09.2017.
 */

public interface Time {
    public final static int S_PER_MIN = 60;
    public final static int MS_PER_S = 1000;
    public final static int US_PER_S = 1000*MS_PER_S;
    public final static int NS_PER_S = 1000*US_PER_S;

}


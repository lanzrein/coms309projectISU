package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities;

import edu.iastate.cs.proj_309_vc_b_4.game.User.User;

/**
 * This is  a helper class to represent the opponent.
 * This way we can have global access to it
 * Created by johan on 02.12.2017.
 */

public class Opponent {
    /**
     * The opponent as  a user
     */
    public static User opponent;
    private static boolean set;

    /**
     *
     * @return true iff there is an opponent.
     */
    public boolean isSet(){return set;}

    /**
     * Set the opponent to be the argument user .
     * @param opp a User to be the opponent.
     */
    public static void setOpponent(User opp){
        opponent = opp;
        set = true;
    }

    /**
     * Clear the opponent so that isSet returns false
     */
    public static void clearOpponent(){
        opponent = new User("","",-1,-1,-1,-1);
        set = false;
    }
}

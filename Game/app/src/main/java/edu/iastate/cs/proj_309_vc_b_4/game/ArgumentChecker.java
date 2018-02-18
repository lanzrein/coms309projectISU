package edu.iastate.cs.proj_309_vc_b_4.game;

/**
 * Class that will check if arguments are correct.
 * It will allow to encapsulate lots of exception handling.
 * Created by johan on 14.09.2017.
 */

public class ArgumentChecker {

    private ArgumentChecker(){};

    public static void requirePositive(int x){
        if(x < 0){
            throw new IllegalArgumentException(x+" was expected to be positive.");
        }
        return;
    }

}

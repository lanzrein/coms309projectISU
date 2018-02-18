package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * Created by johan on 03.12.2017.
 * This class is used as a container for unit waves for networking purposes.
 */
public class SendingWave {
    private static ArrayList<Unit> enemyWave = new ArrayList<>();
    private static ArrayList<Unit> ownWave = new ArrayList<>();

    /**
     * Sets the enemy wave
     * @param wave enemy weave
     */
    public static void setEnemyWave(ArrayList<Unit> wave){
        enemyWave = wave;
    }

    /**
     * Sets the currentPlayer's wave
     * @param wave currentPlayer's wave
     */
    public static void setOwnWave(ArrayList<Unit> wave){
        ownWave = wave;
    }

    /**
     * Returns the enemy wave
     * @return the enemy wave
     */
    public static ArrayList<Unit> getEnemyWave(){
        ArrayList<Unit> toSend = new ArrayList<>(enemyWave);
        return toSend;
    }

    /**
     * Returns the currentPlayer's wave
     * @return currentPlayer's wave
     */
    public static ArrayList<Unit> getOwnWave(){
        return new ArrayList<>(ownWave);
    }

    /**
     * clears the enemy wave
     */
    public static void clearEnemyWave(){
        enemyWave = new ArrayList<>();
    }

    /**
     * clears the current players wave
     */
    public static void clearOwnWave(){
        ownWave = new ArrayList<>();
    }


}

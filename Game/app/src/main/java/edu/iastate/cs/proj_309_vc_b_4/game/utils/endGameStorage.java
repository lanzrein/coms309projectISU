package edu.iastate.cs.proj_309_vc_b_4.game.utils;

/**
 * Created by JeremyC on 12/3/2017.
 */

/**
 *
 * stores the information temporary. It will be retrieved and disply in the end game screen.
 */
public class endGameStorage
{
    private static int mapID;
    private static long score;

    public endGameStorage(int mapID, long score){
        this.mapID = mapID;
        this.score = score;
    }

    /**
     * obatained the score of the player.
     * @return score of the player,
     */
    public static long getScore(){
        return score;
    }

    /**
     * obtained the map ID that was used in a certain game session.
     * @return ID of the map tha was played in
     */
    public static int getMapID(){
        return mapID;
    }

    public static void setMapID(int map){
        mapID = map;
    }

    public static void setScore(long s){
        score = s;
    }
}

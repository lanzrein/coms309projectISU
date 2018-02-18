package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

/**
 * Handles the requests to the server for the game
 * Created by johan on 30.11.2017.
 */

public final class GamestateServerReqs extends RequestAsync {
    private GamestateServerReqs(){};
    private static final String SERVER_ADDRESS = VolleySingleton.SERVER_ADDRESS+"/game/gamestate/";

    public static boolean push_gamestate(String gamestate, int playerID, int oppID){

        String url = SERVER_ADDRESS+"gamestate_update.php?playerID="+playerID+"&adversaryID="+oppID+"&" +
                "gamestate="+gamestate;
        AsyncTask<String, Void, String > at = asyncTaskGen();
        at.execute(url);
        return checkResponse(at);

    }

    /**
     * Poll the server to get the current gamelogic in form of a string encoded.
     * @param playerID the player id
     * @param adversaryID opponent id
     * @return the encoded string.
     */
    public static String poll_server(int playerID, int adversaryID){
        String url = SERVER_ADDRESS+"game_polling.php?playerID="+playerID+"&adversaryID="+adversaryID;
        AsyncTask<String, Void, String > at = asyncTaskGen();
        at.execute(url);
        String res = "";
        try {
            res = at.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return res;
    }
}

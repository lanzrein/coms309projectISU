package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.AsyncTask;

import static edu.iastate.cs.proj_309_vc_b_4.game.utils.VolleySingleton.SERVER_ADDRESS;

/**
 * Created by JeremyC on 12/3/2017.
 */

/**
 * A class that updates the status of the players win and loss counts.
 */
public class UpdateWinLoss extends RequestAsync
{
    String link = SERVER_ADDRESS+"/game/endGame/";
    /**
     * Increase the number of wins to the opponents stats
     * @param id Opponents Player ID
     * @return true if no error occurred. False, otherwise.
     */
    public static boolean increaseWin(int id)
    {
        String url = SERVER_ADDRESS+"IncreaseWin.php?PlayerID="+id;
        AsyncTask<String, Void, String> mp = asyncTaskGen();
        mp.execute(url);
        return checkResponse(mp);
    }

    /**
     * Increase the number of losses to the opponents stats
     * @param id Opponents Player ID
     * @return true if no error occurred. False, otherwise.
     */
    public static boolean increaseLoss(int id)
    {
        String url = SERVER_ADDRESS+"IncreaseLoss.php?PlayerID="+id;
        AsyncTask<String, Void, String> mp = asyncTaskGen();
        mp.execute(url);
        return checkResponse(mp);
    }
}

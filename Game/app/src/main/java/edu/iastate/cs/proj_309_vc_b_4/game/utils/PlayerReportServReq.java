package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import edu.iastate.cs.proj_309_vc_b_4.game.User.AdminPlayer;


/**
 * This class handles the players' data that is retrieved from the database.
 * Created by JeremyC on 11/27/2017.
 */
public class PlayerReportServReq extends RequestAsync
{
    private final static String SERVER_ADDRESS = VolleySingleton.SERVER_ADDRESS;

    /**
     * Retrieve a list of Players' information who were reported.
     * @param link the url of the php file in the server
     * @return a list of AdminPlayer or null if an error orcurred.
     */
    public static List<AdminPlayer> getReports(String link)
    {
        String url = SERVER_ADDRESS + link;
        AsyncTask<String, Void, String> reportRetvr = asyncTaskGen();
        ArrayList<AdminPlayer> reports = new ArrayList<>();
        reportRetvr.execute(url);

        try
        {
            Gson gson = new Gson();
            String response = reportRetvr.get();
            if(!response.contains("ERR") || !response.isEmpty())
            {
                String[] xs = response.split(Pattern.quote("#"));
                for(String rs : xs)
                {
                    AdminPlayer admin = gson.fromJson(rs, AdminPlayer.class);
                    if(admin != null)
                    {
                        reports.add(admin);
                    }
                }
            }

            else
            {
                return null;
            }
        }

        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        return reports;

    }

    /**
     * Delete player from the database when the player
     * received numerous reports.
     * @param id ID of the reported player.
     * @return True if the player was successfully removed. false, otherwise.
     */
    public static boolean deletePlayer(int id)
    {
        String url = SERVER_ADDRESS+"/game/admin/removePlayer.php?PlayerID="+id;
        AsyncTask<String, Void, String> mp = asyncTaskGen();
        mp.execute(url);
        return checkResponse(mp);
    }

    /**
     * Report a player with misbehavior conduct in the game.
     * @param id id of the misbehaviored player
     * @return True if the player was successfully reported. false, otherwise.
     */
    public static boolean reportPlayer(int id)
    {
        String url = SERVER_ADDRESS+"/game/admin/IncPlayerRep.php?PlayerID="+id;
        AsyncTask<String, Void, String> mp = asyncTaskGen();
        mp.execute(url);
        return checkResponse(mp);
    }

    /**
     * Check if the current user is an admin.
     * @param username username of the current user.
     * @param password password of the current user.
     * @return True if the user is an admin. false, otherwise.
     */
    public static boolean checkAdmin(String username, String password)
    {
        String url = SERVER_ADDRESS+"/game/admin/checkAdmin.php?username="+username+"&password="+password;
        AsyncTask<String, Void, String> mp = asyncTaskGen();
        mp.execute(url);
        return checkResponse(mp);
    }
}



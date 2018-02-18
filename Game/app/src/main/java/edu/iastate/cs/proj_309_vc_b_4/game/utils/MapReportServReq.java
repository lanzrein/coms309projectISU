package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import edu.iastate.cs.proj_309_vc_b_4.game.User.AdminMap;

/**
 * This class handles the map data retrieval process with the provided url.
 * Created by JeremyC
 */
public class MapReportServReq extends RequestAsync
{
    private final static String SERVER_ADDRESS = VolleySingleton.SERVER_ADDRESS;

    /**
     * Retrieves a list of maps that was reported with its respective information.
     * Where it is return as a list of AdminMap.
     * @param link the url of the php file in the server
     * @return An array list of Maps with their respective info or null if an error occurred.
     */
    public static List<AdminMap> getReports(String link)
    {
        String url = SERVER_ADDRESS + link;
        AsyncTask<String, Void, String> reportRetvr = asyncTaskGen();
        ArrayList<AdminMap> reports = new ArrayList<>();
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
                    AdminMap admin = gson.fromJson(rs, AdminMap.class);
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
     * Delete the map and its information in the database.
     * @param id ID of the map
     * @return true if the map is delete successfully. Otherwise, false.
     */
    public static boolean deleteMap(int id)
    {
        String url = SERVER_ADDRESS+"/game/admin/removeMap.php?PlayerID="+id;
        AsyncTask<String, Void, String> mp = asyncTaskGen();
        mp.execute(url);
        return checkResponse(mp);
    }

    /**
     * Update and increase the number of unfair report of Map table in the database.
     * @param id Reported player's ID
     * @return true if no error occurred. False, otherwise.
     */
    public static boolean updateUnfairRep(int id)
    {
        String url = SERVER_ADDRESS+"/game/admin/MapUnRep.php?mapID="+id;
        AsyncTask<String, Void, String> mp = asyncTaskGen();
        mp.execute(url);
        return checkResponse(mp);
    }

    /**
     * Update and increase the number of Offensive report of Map table in the database.
     * @param id Reported player's ID
     * @return true if no error occurred. False, otherwise.
     */
    public static boolean  updateOffenseRep(int id)
    {
        String url = SERVER_ADDRESS+"/game/admin/MapOffRep.php?mapID="+id;
        AsyncTask<String, Void, String> mp = asyncTaskGen();
        mp.execute(url);
        return checkResponse(mp);
    }
}

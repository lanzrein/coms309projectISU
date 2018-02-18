package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static edu.iastate.cs.proj_309_vc_b_4.game.utils.VolleySingleton.SERVER_ADDRESS;


/**
 * Responsible for retrieving the players information that is being sorted based on different criteria in the php.
 * Created by JeremyC on 11/5/2017.
 */
public class LeaderServReq
{
    static String url = SERVER_ADDRESS;

    /**
     * Fetch data from the database
     * @param link url of the php file
     * @return list of retrieved data
     */
    public static ArrayList<Lcontent> FetchData (String link)
    {
        class procData extends AsyncTask<String, Void, String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                String s = strings[0];
                return getResponseFrom(s);
            }

        }

        procData fetch = new procData();
        fetch.execute(url+link);
        ArrayList<Lcontent> list = new ArrayList<>();
        try {
            String response = fetch.get();
            if (!response.contains("ERROR")) {
                String[] players = response.split(Pattern.quote("#"));
                Gson gson = new Gson();
                for (String p : players) {
                    PlayerLeaderboard p1 = gson.fromJson(p, PlayerLeaderboard.class);
                    list.add(p1.toContent());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;


    }


    @NonNull
    private static String getResponseFrom(String string)
    {
        String s = string;
        BufferedReader br = null;
        try{
            URL url = new URL(s);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String res;
            while((res = br.readLine())!= null){
                sb.append(res);

            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "ERROR";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}

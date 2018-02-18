package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class offers a few helper methods to facilitate creation of AsyncTasks.
 * Extend it when you need to create your own AsyncTask. If need an example refer to MapServerReqs, or FriendsServerReqs.
 * This offers an alternative to Volley that has a big advantage : easy to work with thread locking.
 * We can lock the main thread easily without having to make tricks and fry our CPU
 * Created by johan on 25.10.2017.
 */

public class RequestAsync {

    protected RequestAsync(){};

    /**
     * Small helper to get the response from a url.
     * @param string the url to call
     * @return the response from the url .
     */
    protected static String getResponseFrom(String string){
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
            return "ERR";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERR";
        }
    }


    /**
     * Return true if the response contains no error message.
     * @param asyncTask the async task
     * @return true iff no error in async task
     */
    protected static boolean checkResponse(AsyncTask<String,Void,String> asyncTask) {
        try{
            String response = asyncTask.get();
            return !response.contains("ERR");
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }


    /**
     * This creates a asynctask for you. It will return an AsyncTask that implements the method
     * doInBackgroundAlready
     * Careful : only works if you send a string request and expect a string as a response form server.
     * @return The async tast created.
     */
    protected static AsyncTask<String, Void, String> asyncTaskGen(){
        class MyAsyncTask extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                return getResponseFrom(strings[0]);
            }
        }
        return new MyAsyncTask();
    }
}

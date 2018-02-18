package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.content.Context;
import android.os.AsyncTask;

/**
 *
 * Offers all request handle from the settings activity..
 * Created by johan on 01.11.2017.
 */

public final class SettingsServerReqs extends RequestAsync{
    public static final String SERVER_ADDRESS = VolleySingleton.SERVER_ADDRESS+"/game/account/";


    /**
     * Change the message to newMessage
     * @param newMessage
     * @param username
     * @param password
     * @return true iff operation successful
     */
    public static boolean changeMessageTo(String newMessage, String username, String password){
        String url = SERVER_ADDRESS+"edit_message.php?newMessage="+newMessage+"&username="+username+"&password="+password;
        AsyncTask<String,Void,String> at = asyncTaskGen();
        at.execute(url);
        return checkResponse(at);
    }

    /**
     * Change the image to the one with the newImageId index
     * @param newImageId
     * @param username
     * @param password
     * @return true iff operation successful
     */
    public static boolean changeImageTo(int newImageId, String username, String password){
        String url = SERVER_ADDRESS+"edit_profilepicture.php?imageID="+newImageId+"&username="+username+"&password="+password;
        AsyncTask<String,Void,String> at = asyncTaskGen();
        at.execute(url);
        return checkResponse(at);
    }


    /**
     * Send email to the admin team. This sends an email to the team
     * @param message message of the email
     * @param username
     * @param playerID
     * @return true iff operation successful
     */
    public static boolean sendEmailToTeam(String message, String username, int playerID){

        String url = SERVER_ADDRESS+"send_feedback.php?playerID="+playerID+"&username="+username+"&feedback="+message;

        AsyncTask<String,Void,String> at = asyncTaskGen();
        at.execute(url);

        return checkResponse(at);
    }

    /**
     * delete the account of the current user.
     * @param username of the user
     * @param password of the user
     * @return true iff the account was deleted
     */
    public static boolean deleteAccount(String username, String password){
        String url = SERVER_ADDRESS+"delete_account.php?username="+username+"&password="+password;
        AsyncTask<String,Void,String> at = asyncTaskGen();
        at.execute(url);
        return checkResponse(at);
    }




}

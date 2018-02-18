package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import edu.iastate.cs.proj_309_vc_b_4.game.User.Message;

/**
 * Handles all the request to the server that have to do with messaging !
 * Created by johan on 03.11.2017.
 */

public class MessageServerReqs extends RequestAsync {
    public final static String SERVER_ADDRESS = VolleySingleton.SERVER_ADDRESS+"/game/friends/";

    //private
    private MessageServerReqs(){};

    /**
     * Send a message to the user with toID as playerID,
     * the message comes from the user iwth toID
     * the Lcontent is message
     * @param fromID
     * @param toID
     * @param message
     * @return true iff successful send.
     */
    public static boolean sendMessageTo(int fromID, int toID, String message){
        AsyncTask<String, Void, String> at = asyncTaskGen();
        String url = SERVER_ADDRESS+"send_message.php?fromID="+fromID+"&toID="+toID+"&message="+message;
        at.execute(url);
        return checkResponse(at);
    }

    /**
     * Delete the message with messageID
     * The other parameters are to insure it is the correct user makes the request.
     * @param username
     * @param password
     * @param toID
     * @param messageID
     * @return true iff successful delete
     */
    public static boolean deleteMessage(String username, String password, int toID, int messageID){
        AsyncTask<String, Void, String> at = asyncTaskGen();
        String url = SERVER_ADDRESS+"delete_message.php?username="+username+"&password="+password+"&toID="+toID+"&messageID="+messageID;
        at.execute(url);
        return checkResponse(at);
    }

    /**
     * Retrieve from the server all the messages available.
     * @param username
     * @param password
     * @return a list of all available messages to the user.
     */
    public static List<Message> getMessage(String username, String password){
        String url = SERVER_ADDRESS+"poll_messages.php?username="+username+"&password="+password;
        AsyncTask<String, Void, String> at = asyncTaskGen();
        at.execute(url);
        List<Message> messages = new ArrayList<>();
        try {
            String res = at.get();
            if(!res.contains("Empty")) {
                String[] xs = res.split(Pattern.quote("$"));
                Gson gson = new Gson();

                for (String x : xs) {
                    Message message = gson.fromJson(x, Message.class);
                    messages.add(message);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return messages;
    }

}

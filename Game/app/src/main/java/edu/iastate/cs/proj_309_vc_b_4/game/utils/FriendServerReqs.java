package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import edu.iastate.cs.proj_309_vc_b_4.game.ArgumentChecker;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;


/**
 * Created by johan on 19.10.2017.
 */

/**
 * This class makes all the request concerning friends and other users.
 */
public class FriendServerReqs extends RequestAsync {
    //we dont want no instance of this
    private FriendServerReqs(){};

    private final static String SERVER_ADDRESS = VolleySingleton.SERVER_ADDRESS+"/game/friends/";

    /**
     * The user adds a friend.
     * @param id id of the friend to be added.
     * @param username username of the user
     * @param password password of the user
     * @return true iff add successful
     */
    public static boolean addFriend(int id, String username, String password){
        //This works pretty much always the same. do the request.
        String url = SERVER_ADDRESS+"addFriend.php?username="+username+"&password="+password+"&friendID="+id;

        AsyncTask<String,Void,String> fa = asyncTaskGen();
        fa.execute(url);
        return checkResponse(fa);
    }

    /**
     * Remove a friend.
     * @param id id of friend to be removed
     * @param username username of user
     * @param password password of user
     * @return true iff remove successful
     */
    public static boolean removeFriends(int id, String username, String password){
        String url = SERVER_ADDRESS+"removeFriend.php?username="+username+"&password="+password+"&friendID="+id;


        AsyncTask<String,Void,String> fr = asyncTaskGen();
        fr.execute(url);
        return checkResponse(fr);
    }

    /**
     * Return a list of all the friends of the use.r
     * It is returned as a list of user.
     * If no friends or error - return null
     * @param username username of user.
     * @param password password of user
     * @return the list of friends ( or null on error )
     */
    public static List<User> getFriends(String username,String password){
        String url = SERVER_ADDRESS+"get_friends.php?username="+username+"&password="+password;
        AsyncTask<String,Void,String> friendgetter = asyncTaskGen();
        List<User> friends = new ArrayList<>();
        friendgetter.execute(url);
        try {

            Gson gson = new Gson();
            String response = friendgetter.get();
            if(!response.contains("ERR")||!response.isEmpty()){
                //we have a response that is a json string.
                String[] xs = response.split(Pattern.quote("$"));
                for( String js : xs){
                    User user = gson.fromJson(js,User.class);
                    if(user != null) {

                        friends.add(user);
                    }
                }
            }else{
                //no friends or error...
                return null;
            }
        } catch (InterruptedException e) {
            Log.d("FRIENDS", Log.getStackTraceString(e));
            return null;
        } catch (ExecutionException e) {
            Log.d("FRIENDS", Log.getStackTraceString(e));
            return null;
        } catch (JsonSyntaxException e){
            Log.d("FRIENDS", Log.getStackTraceString(e));
        }
        return friends;
    }

    /**
     * Check if player with given username and id exists
     * @param username username of user
     * @param id id of the user
     * @return true iff player exists
     */
    public static boolean existsPlayer(String username, int id){
        String url = SERVER_ADDRESS+"exists_player.php?username="+username+"&playerID="+id;
        AsyncTask<String,Void,String> task = asyncTaskGen();
        task.execute(url);
        return checkResponse(task);
    }


    /**
     * Return the user that has the id and username.
     * Null in case of problems.
     * @param username username of player
     * @param id id of the player we need
     * @return User or null in error.
     */
    public static User getSingleUser(String username, int id){
        String url;
        if(username.isEmpty()){
            url = SERVER_ADDRESS+"get_singleuser.php?playerID="+id;

        }else{
            url = SERVER_ADDRESS+"get_singleuser.php?username="+username+"&playerID="+id;

        }
        AsyncTask<String,Void,String> task = asyncTaskGen();
        task.execute(url);
        try {

            String res = task.get();
            if(!res.contains("ERROR")) {
                //convert it.
                Gson gson = new Gson();
                User u = gson.fromJson(res, User.class);
                return u;
            }


        } catch (InterruptedException e) {
            Log.d("FRIENDS", Log.getStackTraceString(e));
        } catch (ExecutionException e) {
            Log.d("FRIENDS", Log.getStackTraceString(e));
        }

        return null;

    }

    /**
     * Get a user given his id.
     * @param id
     * @return the user.
     */
    public static User getSingleUser(int id){
        return getSingleUser("",id);
    }



}

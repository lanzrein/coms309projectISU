package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.FriendServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.VolleySingleton;

import static edu.iastate.cs.proj_309_vc_b_4.game.utils.VolleySingleton.SERVER_ADDRESS;


/**
 *
 * This class describes a player in the login sense.
 * All the things here are his personal preferences.
 * Created by johan on 15.09.2017.

 */

public final class Player {
    private final int PlayerID;
    private final String username;
    private String message;

    /**
     * Creates a new player. the parameters are implicit.
     *
     * @param playerID
     * @param username
     * @param message
     */
    public Player(int playerID, String username,
                   String message){
        this.PlayerID = playerID;
        this.username = username;
        this.message = message;

    }



    public static Player getPlayer(String username, String pwd){


        return null;//TODO


    }



    public boolean updateWinLoss(/*DEFINE A GAME AND ITS OUTCOME*/){
        //TODO if the player wins ++ numberWins , if loss ++ numberLoss

        return false;
    }



    public boolean exists(Player player){
        return FriendServerReqs.existsPlayer(player.username,player.getId());
    }

    /**
     * Same idea as login.
     * Call this method to change he personnal message of our player.
     * @param appContext the current app context
     * @param string the new message.
     */
    public void editMessage(final Context appContext, String string){
        String url = SERVER_ADDRESS +"/game/message_edit.php?playerID="+PlayerID+"&newMessage="+string;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        if(!response.equals("ERR")){
                            Toast.makeText(appContext,"Message set as : "+ response,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(appContext,"Error ! Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appContext, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        });

        //now we send
        VolleySingleton.getInstance(appContext).addToRequestQueue(stringRequest);
    }






    @Override
    public String toString(){
        return username + ". ID : " + PlayerID;
    }


    public int getId() {
        return PlayerID;
    }

    public String getUsername(){
        return username;
    }


    public int getLevel() {
        int experience = 0;
        return (int)Math.floor(Math.log(experience));
    }

    public String getMessage() {
        return message;
    }

    /**
     * This allows to convert from a player to a user.
     * @return
     */
    public User converToUser(){
        return new User(username,message,10,10,0,PlayerID);
    }

}

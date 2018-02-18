package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.Opponent;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.GameMode;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.User.Lobby;
import edu.iastate.cs.proj_309_vc_b_4.game.User.SetupGame;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;

/**
 * handles the request made in the lobby menu.
 * Created by johan on 05.11.2017.
 */

public class LobbyServerReqs extends RequestAsync{
    private LobbyServerReqs(){};

    /**
     * Create a new lobby for the player. Given a game mode.
     * @param gm the game mode.
     * @param context current context
     * @return the lobby.
     */
    public static Lobby createLobby(GameMode gm, Context context){
        SharedPrefManager spm = SharedPrefManager.getInstance(context);
        Player p = spm.getLocalPlayer();
        String url = VolleySingleton.SERVER_ADDRESS + "/game/lobby/poll_lobby.php?username="+p.getUsername()+"&PlayerID="
                +p.getId()+"&Gamemode="+gm.ordinal();


        boolean created = false;
        while(!created) {
            AsyncTask<String, Void, String> at = asyncTaskGen();
            at.execute(url);
            try {
                String res = at.get();
                if (res.contains("ERR")) {
                    return null;
                } else if(!res.contains("Waiting")){
                    created = true;
                    Gson gson = new Gson();
                    Lobby lobby = gson.fromJson(res,Lobby.class);
                    lobby.setID(gm);
                    int oppID = lobby.getLeaderID();
                    if(oppID == p.getId()){
                        oppID = lobby.getOpponentID();
                    }

                    User u = FriendServerReqs.getSingleUser(oppID);
                    Opponent.setOpponent(u);

                    return lobby;
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return null;


    }


    /**
     * Returns a new setup of a game for both player.
     *
     * @param ctx the current context
     * @param mapID the map ID selected
     * @param raceID the race ID selected for the current user.
     * @param playerID the playerID of the current user.
     * @param gameID gameID of the game the user will play.
     * @return the SetupGame created.
     */
    public static SetupGame gameLobbyPoll(Context ctx, int mapID, int raceID, int playerID, int gameID){
        String url = VolleySingleton.SERVER_ADDRESS + "/game/lobby/game_lobby.php?PlayerID="+playerID+"&race="
                +raceID+"&MapID="+mapID+"&GameID="+gameID;
        boolean done = false;
        while(!done) {
            AsyncTask<String, Void, String> at = asyncTaskGen();

            at.execute(url);

            try {
                String res = at.get();
                if (res.contains("ERROR")) {
                    Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show();
                    done = true;

                } else if (res.contains("Wait")) {
                    /* needs to go to the other request... */
//                    Toast.makeText(ctx, "Waiting for other player...", Toast.LENGTH_SHORT).show();
//                    return requestForLeader(ctx,gameID, playerID);
                } else {
                    //we got what we need...
                    Gson gson = new Gson();
                    Toast.makeText(ctx, res,Toast.LENGTH_LONG).show();
                    done = true;
                    return gson.fromJson(res, SetupGame.class);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return null;



    }

    private static SetupGame requestForLeader(Context ctx, int gameID, int playerID) {
        String url = VolleySingleton.SERVER_ADDRESS + "/game/lobby/poll_game_lobby.php?PlayerID="+playerID+"&GameID="+gameID;

        boolean done = false;
        while(!done){
            AsyncTask<String, Void, String> at = asyncTaskGen();
            at.execute(url);
            try {
                String res = at.get();
                if(!res.contains("Waiting")){
                    done = true;
                    Toast.makeText(ctx, res,Toast.LENGTH_LONG).show();
                    Gson gson = new Gson();
                    return gson.fromJson(res, SetupGame.class);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        return null;



    }

}

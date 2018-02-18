import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 *
 * This class describes a player in the login sense.
 * All the things here are his personal preferences.
 * TODO Add a way to have an image.
 * Created by johan on 15.09.2017.
 *     //TODO we will need a second class that describes the player as a statistic.

 */

public final class Player {
    private final int playerID;
    private final String username;
    private float experience;
    private String message;
    private List<Integer> friendsID;//TODO find a way to retrieve friend list

    /**
     * Creates a new player. the parameters are implicit.
     *
     * @param playerID
     * @param username
     * @param message
     */
    public Player(int playerID, String username,
                  String message){
        this.playerID = playerID;
        this.username = username;
        this.message = message;

    }

    /**
     * This creates a new player. Should only be done when setting up an account.
     * @param username the username of the player
     * @return the new @Player created.
     */
    public Player createNew(String username){
        //TODO Check if the playerID is in db
        int playerID =-1;
        //TODO add security to password.
        return new Player(playerID, username,"");

    }



    public static Player createPlayer(int id, String username, float experience, int level, int numberWins,
                                      int numberLoss, int numberReports, String message) {



        return new Player(id,username,message);
    }


    public boolean updateWinLoss(/*DEFINE A GAME AND ITS OUTCOME*/){
        //TODO if the player wins ++ numberWins , if loss ++ numberLoss

        return false;
    }



    public boolean exists(Player player){
        //TODO pull ID from database.
        return true;
    }






    @Override
    public String toString(){
        return username + ". ID : " + playerID;
    }


    public int getId() {
        return playerID;
    }

    public String getUsername(){
        return username;
    }


    public int getLevel() {
        return (int)Math.floor(Math.log(experience));//TODO Check if team ok with algorithm
    }

    public String getMessage() {
        return message;
    }

}

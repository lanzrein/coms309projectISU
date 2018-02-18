package edu.iastate.cs.proj_309_vc_b_4.game.utils;

/**
 * This class describe a player leaderboard and
 * handles all stats that are required to be display.
 * Created by JeremyC on 11/5/2017.
 */
public class PlayerLeaderboard
{
    private final int PlayerID;
    private final int numberWins;
    private final int numberLoss;
    private final int fastestTime;
    private final int imageID;
    private final String username;
    private final String message;

    public PlayerLeaderboard(int PlayerID, String username, String message, int numberWins, int numberLoss, int imageID, int fastestTime){
        this.PlayerID = PlayerID;
        this.username = username;
        this.message = message;
        this.numberWins = numberWins;
        this.numberLoss = numberLoss;
        this.imageID = imageID;
        this.fastestTime = fastestTime;
    }

    /**
     * Place all of the data into the content class.
     * @return a list of data of type Lcontent.
     */
    public Lcontent toContent(){
        Lcontent ctn = new Lcontent(PlayerID,username,message,numberWins,numberLoss, imageID, fastestTime);
        return ctn;
    }
}

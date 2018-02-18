package edu.iastate.cs.proj_309_vc_b_4.game.User;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.GameMode;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;

/**
 * A lobby object that wil be used to share info between two players at the start of the game.
 * Created by johan on 05.11.2017.
 */

public class Lobby {



    private final int leaderID;
    private final String leaderUsername;
    private GameMode gamemode;
    private final int gameID;
    private final int opponentID;
    private final String opponentUsername;

    /**
     * Creates a new Lobby.
     * @param leaderID
     * @param leaderUsername
     * @param gamemode
     * @param gameID
     * @param opponentID
     * @param opponentUsername
     */
    public Lobby(int leaderID, String leaderUsername, GameMode gamemode, int gameID, int opponentID, String opponentUsername) {
        this.leaderID = leaderID;
        this.leaderUsername = leaderUsername;
        this.gamemode = gamemode;
        this.gameID = gameID;
        this.opponentID = opponentID;
        this.opponentUsername = opponentUsername;
    }

    /**
     * set the game mode id
     * @param gameMode the id of the gamemod
     */
    public void setID(GameMode gameMode) {
        this.gamemode = gameMode;
    }

    /**
     * Creates a new lobby for a SOLO game.
     * @param p the player who will play solo
     * @return the lobby created.
     */
    public static Lobby lobbySolo(Player p){
        return new Lobby(p.getId(), p.getUsername(), GameMode.SOLO,-1,-1,null);
    }

    /**
     * Returns if the player is a leader or not.
     * @param p the player
     * @return true iff the player is the leader of the lobby.
     */
    public boolean isLeader(Player p){
        return p.getId() == leaderID;
    }

    /**
     *
     * @return the game id
     */
    public int getGameID() {
        return gameID;
    }

    public int getOpponentID() {
        return opponentID;
    }

    public int getLeaderID() {
        return leaderID;
    }
}

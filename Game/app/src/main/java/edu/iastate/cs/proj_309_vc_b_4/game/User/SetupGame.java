package edu.iastate.cs.proj_309_vc_b_4.game.User;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.GameMode;

/**
 * a more precise lobby.
 * With extra information.
 * Created by johan on 05.11.2017.
 */

public class SetupGame extends Lobby {
    private final int race1;
    private final int race2;
    private final int mapID;

    /**
     * Return the setup of the game.
     * @param leaderID
     * @param race1 race of user 1  (leader)
     * @param race2 race of user 2
     * @param gameID the id of the game
     * @param mapID map ID.
     * @param opponentID the id of the opponent ( as the current user knows his own id)
     * @param gameMode the game mode selected
     */
    public SetupGame(int leaderID, int race1, int race2, int gameID, int mapID, int opponentID, GameMode gameMode){
            super(leaderID, "", gameMode, gameID, opponentID, "");
            this.race1 = race1;
            this.race2 = race2;
            this.mapID = mapID;
    }

    public int getMapID() {
        return mapID;
    }
}

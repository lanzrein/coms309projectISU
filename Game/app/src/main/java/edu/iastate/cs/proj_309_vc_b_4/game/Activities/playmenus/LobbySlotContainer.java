package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

/**
 * Created by Joe on 10/5/2017.
 *
 * This class is a container for the different team slots in the Game Lobby view
 */

public class LobbySlotContainer {
    private LobbySlot teamA;
    private LobbySlot teamB;
    private int teamASize, teamBSize;

    /**
     * Constructor for a LobbySlotContainer. This will initialize the teams to have the right number of slots depending on the game mode
     * @param gm Game mode
     */
    public LobbySlotContainer(GameMode gm){

        //build the lists
        teamA = new LobbySlot(true, null, null);
        teamB = new LobbySlot(true, null, null);
        teamASize++;
        teamBSize++;

        switch (gm) {
            case SOLO:
                //TODO fill with actual computer
                getSlotTeamB(0).fill("Computer");
                break;
            case ONEVONE:

                break;
            case ONEVTWO:
                //teamB is already length 1 and we want it to be length 2
                //so we only need to build on 1 more slot
                buildList(teamB, 1);
                teamBSize+= 1;
                break;
            case ONEVMANY:
                buildList(teamB, 3);
                teamBSize+= 3;
                break;
        }
        getSlotTeamA(0).fill("Player 1");
    }

    //NOTE this method does not update the size of the list it is building,
    //you must do that on your own
    private void buildList(LobbySlot head,int length) {
        LobbySlot next, prev;
        prev = head;
        for(int i = 0; i<length;i++){
            next = new LobbySlot(true, null, prev);
            prev.setNext(next);
            prev = next;
        }
    }

    /**
     * Given an index, returns the correct slot from Team A.
     * @param index index of the desired slot
     * @return Returns the slot at the given index. Null if the there is no slot.
     */
    public LobbySlot getSlotTeamA(int index){
        LobbySlot get = teamA;
        for(int i=0;i<index;i++) {
            if (get.getNext() != null) {
                get = get.getNext();
            }
            else
            {
                return null;
            }
        }
        return get;
    }

    /**
     * Given an index, returns the correct slot from Team B.
     * @param index index of the desired slot
     * @return Returns the slot at the given index. Null if the there is no slot.
     */
    public LobbySlot getSlotTeamB(int index){
        LobbySlot get = teamB;
        for(int i=0;i<index;i++) {
            if (get.getNext() != null) {
                get = get.getNext();
            }
            else
            {
                return null;
            }
        }
        return get;
    }

    /**
     * Returns the number of slots team A has
     * @return number of slots for team a
     */
    public int getTeamASize(){
        return teamASize;
    }

    /**
     * Returns the number of slots team A has
     * @return number of slot for team A
     */
    public int getTeamBSize(){
        return teamBSize;
    }
}

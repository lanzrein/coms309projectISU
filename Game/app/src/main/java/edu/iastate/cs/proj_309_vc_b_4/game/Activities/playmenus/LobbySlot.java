package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

/**
 * Created by Joe on 10/5/2017.
 * @class This holds a player slot in the lobby menu
 */
public class LobbySlot {

    //place holder for a player
    private String name;
    private final String EMPTY_NAME = "[EMPTY]";
    public LobbySlot next,prev;

    //true if the slot is empty
    private boolean empty;

    /**
     * Contructer for a LobbySlot
     * @param empty true if this slot is an empty slot
     * @param next pointer to the lobby slot below this slot
     * @param prev pointer to the lobby slot above this slot
     */
    public LobbySlot(boolean empty, LobbySlot next,LobbySlot prev) {
        this.empty = empty;
        this.next = next;
        this.prev = prev;
        name = EMPTY_NAME;

    }

    /**
     * Returns the name/player that this in this slot
     * @return name
     */
    public String getName(){
        return name;
    }


    //changes the name/player but doesn't set the slot to full

    /**
     * Changes the name of this slot. Note that this doesnt change the slot to full
     * @param name new name for this slot
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Attempts to fill this slot with the given player/name. If this slot is already full then this method will return null.
     * @param name Player/name to try and put in this slot
     * @return true if the slot was filled, false if the slot was already full
     */
    public boolean fill(String name){
        if(empty){
            this.name = name;
            empty = false;
            return true;
        }
        return false;
    }

    /**
     * Returns the status of this slot.
     * @return Return true if this slot is full
     */
    public boolean isEmpty(){
        return empty;
    }

    /**
     * Empties this slot
     */
    public void empty(){
        empty = true;
        name = EMPTY_NAME;
    }

    /**
     * Returns string representation of this class
     * @return String
     */
    public String toString() {
        return name;
    }

    /**
     * Gets a pointer to the next slot
     * @return pointer to thte next slot
     */
    public LobbySlot getNext(){
        return next;
    }

    /**
     * Gets a pointer to the previous slot
     * @return pointer to the previous slot
     */
    public LobbySlot getPrev(){
        return prev;
    }

    /**
     * Sets the next slot to the given slot
     * @param next slot that will be set to next
     */
    public void setNext(LobbySlot next){
        this.next = next;
    }

    /**
     * Sets the previous slot to the given slot
     * @param prev slot that will be set to previous
     */
    public void setPrev(LobbySlot prev) {
        this.prev = prev;
    }



}

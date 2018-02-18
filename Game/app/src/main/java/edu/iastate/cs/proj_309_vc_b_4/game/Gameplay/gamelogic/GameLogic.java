package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus.SendingWave;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units.Dwarf_Units;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units.Goblin_Units;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Units.Human_Units;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.GameStore;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.TurretBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.UnitBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Projectile;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Turret;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.TurretServer;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.GamestateServerReqs;

/**
 * Created by Joe on 10/30/2017.
 *
 * This class represents the internal game logic seperate of any UI
 * Will contain the map, players, units, turrets , etc
 */
public class GameLogic {


    private Map map;
    private GameStore store;
    private ArrayList<Turret> turrets;
    private ArrayList<Unit> units;
    private HashMap<UnitBuyable,Integer> boughtUnits;
    private ArrayList<Projectile> projectiles;
    private UnitWave wave;
    private boolean playing = false;
    private boolean lost = false;

    private Turret bought;

    //start out with some money to spend on a tower
    private long score=20;
    private int cash=2000;
    private int baseHP=10;

    private int unitsKilled;


    /**
     * Creates a new instance of GameLogic
     * @param map the map that this game will be played on
     * @param store the store for this game
     */
    public GameLogic(Map map,GameStore store){
        this.map = map;
        this.store = store;
        turrets = new ArrayList<>();
        units = new ArrayList<>();
        boughtUnits = new HashMap<>();
        projectiles = new ArrayList<>();

        unitsKilled = 0;

        //start out with a few basic units
        boughtUnits.put((UnitBuyable)store.getUnitBuyables().get(0),5);

    }

    @SuppressLint("NewApi")
    private void createWave(int waveSize, int unitDensity){
        //creates a wave based on the units arraylist
        for(int i =0;i<waveSize;i++){
            Position spawn = map.getSpawns().get(i%map.getSpawns().size()).getPosition();
            Unit u = units.get(i);
            u.setGame(this);
            u.setPath(map.getPath(spawn));
            u.setPosition(spawn);
        }
        wave = new UnitWave(units,unitDensity);
    }

    /**
     * This method will create a new wave based on the given wave list and begin the wave on this game
     * @param waveList An arraylist of units that this wave will consist of
     */
    public void startWave(ArrayList<Unit> waveList){
        if(!playing) {
            //new wave, so all old projectiles and units can be cleared
            units = waveList;
            createWave(waveList.size(), 30);
            playing = true;
            SendingWave.clearEnemyWave();
        }
    }


    /**
     * Moves the game forward one tick by updating all the units, turrets, and projectiles
     */
    public void tick(){
        if (playing) {
            wave.update();
            for (Turret t : turrets) {

                t.update();
            }
            for (Unit t : wave.getSpawnedUnits()) {
                
                if (t.isAlive() && t.getPosition() != null) {

                    t.update();
                    if(t.getPosition().roughlyEquals(map.getBase().getPosition())){
                        baseHP--;
                        t.kill();
                        if (baseHP <= 0) {
                            lost=true;
                        }
                    }
                }
            }
            for (Projectile t : projectiles) {

                if (t.isAlive())
                    t.update();
            }

            //check if the wave is over
            playing = !wave.roundOver();
        } else {
            projectiles.clear();
        }
    }

    /**
     * Returns all Units that the current wave consists of.
     * @return ArrayList of Units
     */
    public ArrayList<Unit> getUnits(){
        return units;
    }

    /**
     * Returns a list of all units that have been spawned during this wave so far
     * @return ArrayList of units
     */
    public ArrayList<Unit> getSpawnedUnits(){
        if(wave!=null)
            return wave.getSpawnedUnits();
        else
            return null;
    }

    /**
     * attempts to place a turret at the given location
     * returns true if the turret could be placed there, returns false if
     * the position was invalid, if the position is taken, or if there isn't a turret that was bought
     * @return true if a turret was placed
     */
    public boolean placeTurret(Position position) {
        if(bought !=null) {
            if (position.getX() >= 0 && position.getX() < map.WIDTH &&
                    position.getY() >= 0 && position.getY() < map.HEIGHT) {
                if(map.getTerrainAt((int)position.getX(),(int)position.getY())== TerrainType.BASE ||
                        map.getTerrainAt((int)position.getX(),(int)position.getY())== TerrainType.SPAWN ||
                        map.getTerrainAt((int)position.getX(),(int)position.getY())== TerrainType.PATH)
                    return false;

                boolean valid = true;
                for (Turret t : turrets) {
                    if (Position.distance(t.getPosition(), position) <= .75) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    Turret t = new Turret(this, bought.getRange(), bought.getFireRate(), bought.getType(), bought.getPower(), position);
                    turrets.add(t);
                    bought = null;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a list of turrets in the game
     * @return ArrayList of turrets in the game
     */
    public ArrayList<Turret> getTurrets(){
        return turrets;
    }

    /**
     * Returns the list of projectiles in this game
     * @return ArrayList of projectiles
     */
    public ArrayList<Projectile> getProjectiles(){
        return projectiles;
    }

    /**
     * Adds a projectile to the list of projectiles that will be updated on every call to tick()
     * @param p the projectile to add
     */
    public void addProjectile(Projectile p){
        projectiles.add(p);
    }

    /**
     * Returns the map that is being played on
     * @return an instance of the map
     */
    public Map getMap(){
        return map;
    }

    /**
     * Returns the remaining hp that the base has in this game
     * @return the remaining hp that this base has
     */
    public int getBaseHP(){
        return baseHP;
    }

    /**
     * Returns true if this game has been lost
     * @return true if this game has been lost, false if it hasn't
     */
    public boolean hasLost(){
        return lost;
    }

    /**
     * Returns the number of units killed in this game
     * @return number of units killed
     */
    public int getUnitsKilled(){
        return unitsKilled;
    }

    /**
     * called when a unit is killed, This will add the given amount to the score and cash and increase the kill count by 1
     * @param score the amount to be added
     */
    public void addToScore(int score){
        this.score += score;
        cash += score;
        unitsKilled++;
    }

    /**
     * Returns the current amount of cash in this game
     * @return current amount of cash
     */
    public int getCash(){
        return cash;
    }

    /**
     * Returns the score of this game
     * @return score of this game
     */
    public long getScore(){
        return score;
    }

    /**
     * returns a hash map that contains the number of each units the player has bought.
     * @return hashmap containg the units that the player bought as keys and the number of times that unit has been bought as values
     */
    public HashMap<UnitBuyable,Integer> getBoughtUnits(){
        return boughtUnits;
    }

    /**
     * This method attempts to buy a turret. If the turret can be bought, it will be placed at whichever valid position the player clicks next.
     * @param t the turretBuyable that the user attempted to buy
     * @return true if the turret was bought, false if the turret couldn't be bought
     */
    public boolean buyTurret(TurretBuyable t){
        if(t.getCost()<=cash) {
            bought = t.getItem();
            cash = cash - t.getCost();
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * This method attempts to buy a unit. If the unit can be bought, it is added to the hashmap of bought units.
     * @param u the UnitBuyable that the player attempted to buy
     * @return true if the unit was bought, false if the unit wasn't bougth.
     */
    public boolean buyUnit(UnitBuyable u){
        if(u.getCost()<=cash){
            if (boughtUnits.containsKey(u)){
                int boughtCount = boughtUnits.get(u);
                boughtCount++;
                boughtUnits.put(u, boughtCount);
            } else{
                boughtUnits.put(u, 1);
            }
            cash = cash - u.getCost();
            return true;
        }
        else {
            return false;
        }
    }

    private String encodeForServer(){
        StringBuilder unitsID = new StringBuilder();


        for(Unit u : SendingWave.getOwnWave()){
            unitsID.append(u.getID());
            unitsID.append(",");
        }



        StringBuilder turrets = new StringBuilder();
        for(Turret t : this.turrets){
            turrets.append(t.toJson());
            turrets.append("$");
        }
        return unitsID+"$"+turrets.toString();
    }

    private void decodeFromServer(String encoded){
        String[] split = encoded.split(Pattern.quote("$"));
        if(split.length < 2) {
            return;
        }

        String unitsID = split[0];
        String[] unitsIDInt = unitsID.split(",");

        for(String id : unitsIDInt){
            if(!id.isEmpty()) {
                int idint = Integer.parseInt(id.trim());
                units.add(getUnitByID(idint));
            }
        }

        SendingWave.setEnemyWave(units);




        //now decode the turrets.
        Gson gson = new Gson();
        turrets = new ArrayList<>();
        for(int i = 1; i < split.length;i++){
            String t = split[i];
            TurretServer ts = gson.fromJson(t, TurretServer.class);
            turrets.add(ts.toTurret(this));
        }
    }

    /**
     * update the game given the string.
     * @param encodedGamestate the game state encoded in a string.
     */
    private void updateGame(String encodedGamestate) {
        if(encodedGamestate.equals("$")){
            return;
        }
        decodeFromServer(encodedGamestate);

        //Clear the flag saying we got the information needed.
    }

    /**
     * Poll the server for the state of the game.
     * @param playerID the player id
     * @param adversaryID the opponents id
     */
    public void poll(int playerID, int adversaryID) {
        String res = GamestateServerReqs.poll_server(playerID,adversaryID);
        Log.d("ENEMY TUR:",res+":::"+
                turrets.toString());
        updateGame(res);


    }

    /**
     * push the server an update of the gamestate.
     * @param playerID playerid
     * @param adversaryID adversary id
     */
    public void push(int playerID, int adversaryID) {
        String encoded = encodeForServer();
        if(encoded.isEmpty()){return;}
        GamestateServerReqs.push_gamestate(encoded,playerID,adversaryID);
    }


    /**
     * Returns the unit that corresponds to the given id
     * @param ID the give id
     * @return a unit instance that corresponds to the given id
     */
    public Unit getUnitByID(int ID){

        if(ID == 0){
            return Dwarf_Units.make_Dwarf_Flying_Unit(null, null);
        }
        else if (ID == 1){
            return Dwarf_Units.make_Dwarf_Ground_Unit(null, null);
        }
        else if (ID == 2){
            return Dwarf_Units.make_Dwarf_Burrowing_Unit(null, null);
        }
        else if (ID == 3){
            return Goblin_Units.make_Goblin_Burrowing_Unit(null, null);
        }
        else if (ID == 4){
            return Goblin_Units.make_Goblin_Ground_Unit(null, null);
        }
        else if (ID == 5){
            return Goblin_Units.make_Goblin_Burrowing_Unit(null, null);
        }
        else if (ID == 6){
            return Human_Units.make_Human_Flying_Unit(null, null);
        }
        else if (ID == 7){
            return Human_Units.make_Human_Ground_Unit(null, null);
        }
        else if(ID == 8) {
            return Human_Units.make_Human_Burrowing_Unit(null, null);
        }
        else{
            return Goblin_Units.make_Goblin_Burrowing_Unit(null, null);
        }
    }


}

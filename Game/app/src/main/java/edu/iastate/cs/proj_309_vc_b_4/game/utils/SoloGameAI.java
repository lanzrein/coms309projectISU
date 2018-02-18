package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Cell;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.TurretBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.UnitWave;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Turret;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;

/**
 * A basic "AI" for the solo game.
 * It always follows the same pattern and the levels of difficulty
 * make just the unit waves bigger and bigger.
 * Created by johan on 01.12.2017.
 */

public class SoloGameAI {

    public static SoloGameAI currAI;
    private static final int UNIT_CONSTANT = 15;
    private static final int DENSITY_CONSTANT = 30;
    private final int levelDifficulty;
    private boolean pathsNotComputed = true;
    private ArrayList<Position> paths = new ArrayList<>();
    private final int race;//the race.

    /**
     * Create a new ai with the given level of difficulty.
     * The race will be chosen randomly
     * @param levelDifficulty the level of diffuclty.
     */
    public SoloGameAI(int levelDifficulty){
        this(levelDifficulty,(int)( Math.random() * 3));
    }

    /**
     * create a new AI with the level of difficulty and the race.
     * @param levelDifficulty level of difficulty.
     * @param race the race.
     */
    public SoloGameAI(int levelDifficulty, int race){
        this.levelDifficulty = levelDifficulty;
        this.race = race;
        currAI = this;
    }

    /**
     * Spawns a unit wave depending on what round  it is.
     * the highest the wave, the bigger the wave .
     * @param round the current round.
     * @param gameLogic the current game logic
     * @return
     */
    @SuppressLint("NewApi")
    public ArrayList<Unit> spawnWave(int round, GameLogic gameLogic){
    int waveSize = (int) ((round+1)*Math.pow(2,levelDifficulty)*UNIT_CONSTANT);
        int unitDensity = (int) (Math.pow(2,levelDifficulty)*DENSITY_CONSTANT);
        Map map = gameLogic.getMap();
        ArrayList<Unit> units = new ArrayList<>();
        for(int i =0;i<waveSize;i++){
            Position spawn = map.getSpawns().get(i%gameLogic.getMap().getSpawns().size()).getPosition();
            switch(race) {
                case 0 : //human
                    break;
                case 1 :
                    //dwarf
                    break;
                case 2:
                    //gob
                    break;
                default:
                    break;
            }
            units.add(new Unit(gameLogic,map.getPath(spawn),i,50,30, DamageTypes.AIR,10));
        }
        Log.d("WAVE:",units.toString());
        return  units;
    }

    /**
     * Adds a number of turrets on the map.
     * They are placed randomly close to the path.
     * @param round the current round.
     * @param gameLogic the game logic where to add the turret.
     */
    @SuppressLint("NewApi")
    public void addTurrets(int round, GameLogic gameLogic){
        int turretCount = round*levelDifficulty+1;

        Map map = gameLogic.getMap();
        if(pathsNotComputed) {
            List<Cell> spawns = map.getSpawns();
            ArrayList<Position> paths = new ArrayList<>();
            for (Cell c : spawns) {
                paths.addAll(map.getPath(c.getPosition()));
            }

            this.paths.addAll(paths);
            pathsNotComputed = false;
        }
        //we add the turret on a random place close to the paths.
        for(int i = 0 ; i < turretCount; i ++){
            int place = (int)(Math.random()*paths.size());
            Position pos = paths.get(place);
            Position newPos = computeNeighbor(pos, map);
            if(newPos!=null) {
                DamageTypes type = DamageTypes.values()[(int) (Math.random() * 3)];
                Turret t = new Turret(gameLogic, 15 * levelDifficulty, 10 * levelDifficulty, type, 5 * levelDifficulty, newPos);
                gameLogic.buyTurret(new TurretBuyable("turret", 0, t));
                gameLogic.placeTurret(newPos);
            }
        }


    }

    private Position computeNeighbor(Position pos, Map map) {
        return computeNeighbor(pos, map, 0);
    }
    private Position computeNeighbor(Position pos,Map map,int recursionCounter){
        if(recursionCounter>20){
            return null;
        }

        int dx = Math.random() > 0.5 ? -1 : 1;
        int dy = Math.random() > 0.5 ? -1 : 1;

        Position tryPosition = pos.neighbourgh(dx,dy);

        if(map.getData()[(int)tryPosition.getY()][(int)tryPosition.getX()].equals(TerrainType.GRASS)){
            return tryPosition;
        }else{
            return computeNeighbor(pos,map,recursionCounter+1);
        }
    }

    /**
     *
     * @return the race.
     */
    public int getRace() {
        return race;
    }
}

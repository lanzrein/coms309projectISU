package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects;

import com.google.gson.Gson;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;

/**
 * This class is used for server communication.
 * Created by johan on 30.11.2017.
 */

public class TurretServer {

    protected Position position;
    protected DamageTypes type;
    protected int range;
    protected int power;
    protected int fireRate;

    /**
     * Constructor
     * @param p position
     * @param dt damage type
     * @param range range
     * @param power power
     * @param fireRate firerate
     */
    public TurretServer(Position p, DamageTypes dt, int range, int power, int fireRate){
        this.position= p;
        this.type = dt;
        this.range = range;
        this.power = power;
        this.fireRate = fireRate;
    }

    /**
     * empty contructor.
     */
    public TurretServer(){
        position = new Position(0,0);
    }

    /**
     * Convert the turret into a json strin
     * @return JSON string
     */
    public String toJson() {
        Gson gson = new Gson();
        TurretServer ta = cast(this);
        return gson.toJson(ta);
    }

    private TurretServer cast(TurretServer turretServer) {
        return new TurretServer(turretServer.position, turretServer.type, turretServer.range, turretServer.power, turretServer.fireRate);
    }

    /**
     * Transform a turret in server sense
     * into a real turret.
     * @param gameLogic the game logic of the turret.
     * @return the turret
     */
    public Turret toTurret(GameLogic gameLogic) {
        return new Turret(gameLogic,range,fireRate,type,power,position);
    }
}

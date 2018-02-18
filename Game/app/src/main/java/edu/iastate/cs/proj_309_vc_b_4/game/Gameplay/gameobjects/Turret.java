package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;

import android.graphics.Canvas;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;

/**
 * Created by Alex on 10/16/2017.
 *
 * Base class for turrets
 */

public class Turret extends TurretServer implements GameObject {

    //reference to the game object
    private GameLogic game;

    private Position position;
    private DamageTypes type;
    private int range;
    private int power;
    private boolean seeks_Burrowing;
    private boolean seeks_Flying;

    private int fireRate;
    private int lastFired;


    public Turret(GameLogic game, int range, int fireRate, DamageTypes type, int power, Position position) {
        super(position, type, range, power, fireRate);
        this.type = type;
        this.position = position;
        this.range = range;
        this.fireRate = fireRate;
        this.game = game;
        this.power = power;
    }

    /**
     * Final constructor iteration
     * @param game
     * @param range tower's range
     * @param fireRate how fast the tower can shoot
     * @param type type of damage
     * @param power how much damage the tower does per shot
     * @param position position of the tower on the screen
     * @param seeks_Burrowing set true if turret can attack burrowing units
     * @param seeks_Flying set true if turret can attack flying units
     */

    public Turret(GameLogic game, int range, int fireRate, DamageTypes type, int power, Position position, boolean seeks_Burrowing, boolean seeks_Flying) {
        this(game, range, fireRate, type, power, position);
        this.seeks_Burrowing = seeks_Burrowing;
        this.seeks_Flying = seeks_Flying;
    }

//    /**
//     * @param canvas draws turret on canvas
//     */
//    @Override
//    public void draw(Canvas canvas, int scalex, int scaley) {
//    }


    /**
     * tells the turret to attack its target
     * if it does not have a target to aquire one
     */
    @Override
    public void update() {
        logic();
    }


    /**
     *Turret logic; shoots if the tower can shoot. Calls aquire_target from gamelogic's unit list
     */
    private void logic() {

        //if turret can fire, find a target and shoot
        if (lastFired > fireRate) {
            Unit target = aquire_target(game.getSpawnedUnits());
            if (target != null) {
                //TODO grab projectile attributes from somewhere, for now they are hard coded
                game.addProjectile(new Projectile(position.hardCopy(), target, type, 50, 5000, power));
                //only reset the fire counter if a projectile was created
                lastFired = 0;
            }
        } else {
            lastFired++;
        }
    }

    /**
     * This method finds a target for the turret to shoot
     *
     * @param units a list of all units
     * @return the unit that is targeted
     */
    private Unit aquire_target(ArrayList<Unit> units) {
        //always target the first unit that is in the lead and is in range
        for (Unit u : units) {
            if (u.isFlying() && seeks_Flying || u.isBurrowing() && seeks_Burrowing || (u.isBurrowing()==false && u.isFlying()==false)) {
                if (u.isAlive()) {
                    if (u.getPosition()!=null && inRange(u)) {

                        return u;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param u
     * @return if its target is within the turret's range
     */
    private boolean inRange(Unit u) {
        return Position.distance(position, u.getPosition()) <= range;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Turret hardCopy() {
        if(position==null)
            return new Turret(game, range, fireRate, type, power, null);
        else
            return new Turret(game, range, fireRate, type, power, position.hardCopy());
    }


    /**
     *
     * @return type of damage the turret deals
     */
    public DamageTypes getType() {
        return type;
    }


    /**
     *
     * @return turret's range
     */
    public int getRange() {
        return range;
    }


    /**
     *
     * @return turret's power
     */
    public int getPower() {
        return power;
    }

    /**
     *
     * @return turret's rate of fire
     */
    public int getFireRate() {
        return fireRate;
    }


}

package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;
import android.graphics.Point;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.DamageTypes;

/**
 * Created by Alex on 10/16/2017.
 * object for projectiles which are shot out of turrets hit a unit then disappear
 */


public class Projectile implements GameObject  {

    private Position position;
    private int speed,life,damage;
    private Unit target;
    private DamageTypes type;

    /**
     * Constructor for a projectile
     * @param position The origin position of the projectile
     * @param type The type of damage this projectile will do
     * @param speed The speed of this projectiles
     * @param life The amount of ticks/time this projectile has before it despawns
     * @param damage
     */
    public Projectile(Position position,Unit target, DamageTypes type, int speed, int life, int damage){
        this.position = position;
        this.speed = speed;
        this.life = life;
        this.type = type;
        this.damage = damage;
        this.target = target;
    }


    /**
     *
     * This method updates the projectiles position.
     * Projectiles act like homing missles and will follow their target until
     * they either reach the target and hit it or run out of time and despawn
     */
    @Override
    public void update(){
        if (life>0) {
            detectHit();
            float destx, desty, x, y;
            destx = target.getPosition().getX();
            desty = target.getPosition().getY();
            x = position.getX();
            y = position.getY();

            //prevents the projectile from orbiting around a target
            float displacement = .05f;

            x = (float)(x + Math.cos(Math.atan2(desty - y, destx - x))*speed* 0.1 * displacement);
            y = (float)(y + Math.sin(Math.atan2(desty - y, destx - x))*speed * 0.1 * displacement);
            position.set(x,y);

            life--;
        }
        else
        {
            target = null;
        }
    }

    /**
     * Detects if this projectile has reached it's target.
     * If it has, it does damage to the target and kills the projectile
     */
    private void detectHit(){
        if (Position.distance(position,target.getPosition()) < 0.5) {
            target.calculate_Damage(damage,type);
            life = 0;
        }
    }

    /**
     * Returns the current position of this projectile
     * @return current Position
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Returns the speed of this projectile
     * @return speed of this projectile
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the remaingin lifespan of this projectile
     * @return remaining lifespawn
     */
    public int getLifeSpan() {
        return life;
    }

    /**
     * Returns true if this projectile is still alive
     * @return true if alive, false if dead
     */
    public boolean isAlive(){
        return life>0;
    }


}

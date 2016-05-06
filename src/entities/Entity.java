package entities;

import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Termi on 10/09/2015.
 */
public class Entity {

    protected EntitiesManager em;
    protected Vector2f pos;
    protected Vector2f vel;

    public Entity(EntitiesManager em) {
        this(em, new Vector2f(0,0), new Vector2f(0,0));
    }

    public Entity(EntitiesManager em, Vector2f pos, Vector2f vel) {
        this.em = em;
        this.pos = pos.copy();
        this.vel = vel.copy();
    }

    public void init(GameContainer gc) {}

    public void update(GameContainer gc) {}

    public void render(GameContainer gc, Graphics g) {}

    /**
     * Updates the position of the entity by the vel vector
     */
    protected void updatePos(GameContainer gc) {
        pos.add(vel);
        while (pos.x < 0)
            pos.x += gc.getWidth();
        while (pos.y < 0)
            pos.y += gc.getHeight();
        if (pos.x > gc.getWidth())
            pos.x %= gc.getWidth();
        if (pos.y > gc.getHeight())
            pos.y %= gc.getHeight();
    }

    /**
     * Accelerate by given acceleration
     * @param acceleration Acceleration to apply
     */
    protected void accelerate(float acceleration, int direction) {
        Vector2f newForce = new Vector2f(acceleration, 0);
        newForce.add(direction);
        vel.add(newForce);
    }

    /**
     * Decelerate by given deceleration
     * @param deceleration Deceleration to apply
     */
    protected void decelerate(float deceleration) {
        float speed = vel.length();
        float newSpeed = speed - deceleration;
        if (newSpeed > deceleration)
            vel.normalise().scale(newSpeed);
        else
            vel.set(0, 0);
    }

    protected void destroy() {
        em.destroyEntity(this);
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public Vector2f getVel() {
        return vel;
    }

    public void setVel(Vector2f vel) {
        this.vel = vel;
    }
}

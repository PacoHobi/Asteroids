package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Termi on 10/09/2015.
 */
public class ProjectileEntity extends  Entity {

    private Entity origin;
    private long createdAt;
    private int lifeSpan;
    private int radius;

    public ProjectileEntity(EntitiesManager em, Vector2f pos, int direction, Entity origin) {
        this(em, pos, direction, 7, 2000, origin);
    }

    public ProjectileEntity(EntitiesManager em, Vector2f pos, int direction, float speed, int lifeSpan, Entity origin) {
        super(em);
        this.pos = pos;
        this.lifeSpan = lifeSpan;
        this.origin = origin;
        vel = new Vector2f(speed, 0);
        vel.add(direction);
        radius = 2;
    }

    public void init(GameContainer gc) {
        createdAt = gc.getTime();
    }

    public void update(GameContainer gc) {
        // check if lifespan has ended
        if (gc.getTime() - createdAt > lifeSpan) {
            destroy();
            return;
        }

        // update position
        updatePos(gc);
    }

    public void render(GameContainer gc, Graphics g) {
        // Draw as circle
        g.fillOval(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);

        // Draw as line
//        int l = 3;
//        Vector2f end = pos.copy().add(vel.copy().normalise().scale(l));
//        g.drawLine(pos.x, pos.y, end.x, end.y);
    }

    public int getRadius() {
        return radius;
    }

    public Entity getOrigin() {
        return origin;
    }
}

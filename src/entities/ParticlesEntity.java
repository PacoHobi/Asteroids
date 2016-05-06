package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by Termi on 10/09/2015.
 */
public class ParticlesEntity extends Entity {

    private int numParticles;
    private int speed;
    private int length;
    private int lifeSpan;
    private long destroyAt;
    private ArrayList<Vector2f> points;

    public ParticlesEntity(EntitiesManager em, Vector2f pos) {
        super(em);
        this.pos = pos.copy();
        numParticles = 20;
        speed = 1;
        length = 3;
        lifeSpan = 400;
    }

    @ Override
    public void init(GameContainer gc) {
        destroyAt = gc.getTime() + lifeSpan;

        points = new ArrayList<>();
        Vector2f v = new Vector2f(5, 0);
        float ang = 360f / numParticles;
        for (int i = 0; i < numParticles; i++) {
            points.add(v.copy().add(ang*i).add(Math.random() * ang - ang/2));
        }
    }

    @ Override
    public void update(GameContainer gc) {
        if (destroyAt < gc.getTime()) {
            destroy();
            return;
        }

        for (Vector2f p : points) {
            float newLength = (float)(p.length() + 1.5 * Math.random() * speed);
            p.normalise().scale(newLength);
        }
    }

    @ Override
    public void render(GameContainer gc, Graphics g) {
        for (Vector2f p : points) {
            Vector2f a = p.copy().add(pos);
            Vector2f b = a.copy().add(p.copy().normalise().scale(length));
            g.drawLine(a.x, a.y, b.x, b.y);
        }
    }
}

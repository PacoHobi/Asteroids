package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import utils.Geometry;

import java.util.ArrayList;

/**
 * Created by Termi on 11/09/2015.
 */
public class LargeSaucerEntity extends SaucerEntity {


    public LargeSaucerEntity(EntitiesManager em) {
        super(em);

        ArrayList<Vector2f> body = new ArrayList<>();
        body.add(new Vector2f(-25, -1));
        body.add(new Vector2f(-10, -7));
        body.add(new Vector2f(10, -7));
        body.add(new Vector2f(25, -1));
        body.add(new Vector2f(-25, -1));
        body.add(new Vector2f(-9, 10));
        body.add(new Vector2f(9, 10));
        body.add(new Vector2f(25, -1));
        points.add(body);

        ArrayList<Vector2f> cabin = new ArrayList<>();
        cabin.add(new Vector2f(-10, -7));
        cabin.add(new Vector2f(-5, -16));
        cabin.add(new Vector2f(5, -16));
        cabin.add(new Vector2f(10, -7));
        points.add(cabin);

        collisionBox.add(new Vector2f(-27, -16));
        collisionBox.add(new Vector2f(27, -16));
        collisionBox.add(new Vector2f(27, 10));
        collisionBox.add(new Vector2f(-27, 10));
    }

    @ Override
    public void update(GameContainer gc) {
        long time = gc.getTime();

        super.update(gc);

        // shoot
        if (time > lastShot + fireRate) {
            lastShot = time;

            int projDirection = (int)(Math.random() * 360);

            // calculate spawn point;
            Vector2f projVel = new Vector2f(1,0).add(projDirection);
            Vector2f projSpawn = null;

            for (int i = 0; i < collisionBox.size(); i++) {
                Vector2f a = collisionBox.get(i).copy().add(pos);
                Vector2f b = collisionBox.get((i+1) % collisionBox.size()).copy().add(pos);
                projSpawn = Geometry.intersection(pos, projVel, a, b);
                if (projSpawn != null)
                    break;
            }

            em.addEntity(new ProjectileEntity(em, projSpawn, projDirection, 4, 1000, this));

        }
    }
}

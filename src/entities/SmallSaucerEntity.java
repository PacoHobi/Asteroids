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
public class SmallSaucerEntity extends SaucerEntity {

    public SmallSaucerEntity(EntitiesManager em) {
        super(em);

        ArrayList<Vector2f> body = new ArrayList<>();
        body.add(new Vector2f(-12, 0));
        body.add(new Vector2f(-5, -3));
        body.add(new Vector2f(5, -3));
        body.add(new Vector2f(12, 0));
        body.add(new Vector2f(-12, 0));
        body.add(new Vector2f(-4, 5));
        body.add(new Vector2f(4, 5));
        body.add(new Vector2f(12, 0));
        points.add(body);

        ArrayList<Vector2f> cabin = new ArrayList<>();
        cabin.add(new Vector2f(-5, -3));
        cabin.add(new Vector2f(-2, -8));
        cabin.add(new Vector2f(2, -8));
        cabin.add(new Vector2f(5, -3));
        points.add(cabin);

        collisionBox.add(new Vector2f(-12, -8));
        collisionBox.add(new Vector2f(12, -8));
        collisionBox.add(new Vector2f(12, 5));
        collisionBox.add(new Vector2f(-12, 5));
    }

    @ Override
    public void update(GameContainer gc) {
        long time = gc.getTime();

        super.update(gc);

        // shoot
        if (time > lastShot + fireRate) {
            lastShot = time;

            int projDirection = (int)Geometry.Vector2f(pos, em.getShip().getPos()).getTheta() + (int)(Math.random() * 20 - 10);

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

package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by Termi on 10/09/2015.
 */
public class AsteroidEntity extends Entity {

    private int size;
    private int speed;
    private int radius;
    private ArrayList<Vector2f> points;

    public AsteroidEntity(EntitiesManager em, Vector2f pos, int direction, int size) {
        super(em);
        this.pos = pos.copy();
        this.size = size;

        switch (size) {
            case 1:
                speed = 3;
                radius = 15;
                break;
            case 2:
                speed = 2;
                radius = 25;
                break;
            default:
                speed = 1;
                radius = 35;
        }
        vel = new Vector2f(speed, 0).add(direction);
    }

    @ Override
    public void init(GameContainer gc) {
        generateRandomAsteroid();
    }

    @ Override
    public void update(GameContainer gc) {
        // Update position
        updatePos(gc);
    }

    @ Override
    public void render(GameContainer gc, Graphics g) {
        for (int i = 0; i < points.size(); i++) {
            Vector2f a = points.get(i).copy().add(pos);
            Vector2f b = points.get((i + 1) % points.size()).copy().add(pos);
            g.drawLine(a.x, a.y, b.x, b.y);
        }
    }

    private void generateRandomAsteroid() {
        points = new ArrayList<>();
        int edges = 12;

        Vector2f v = new Vector2f(radius, 0);
        float ang = 360 / edges;
        for (int i = 0; i < edges; i++) {
            Vector2f u = v.copy().add(i * ang).scale((float) (Math.random() / 2 + .5));
            points.add(u);
        }
    }

    public void divideAsteroid() {
        destroy();
        if (size > 1) {
            em.addEntity(new AsteroidEntity(
                    em, pos,
                    (int)(Math.random() * 150 - 75),
                    size -1
            ));
            em.addEntity(new AsteroidEntity(
                    em, pos,
                    180 + (int)(Math.random() * 150 - 75),
                    size -1
            ));
        }
    }

    public void destroy() {
        em.addEntity(new ParticlesEntity(em, pos));
        super.destroy();
    }

    public int getRadius() {
        return radius;
    }

    public int getSize() {
        return size;
    }
}

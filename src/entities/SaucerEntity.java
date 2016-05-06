package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import utils.Geometry;

import java.util.ArrayList;

/**
 * Created by Termi on 11/09/2015.
 */
public class SaucerEntity extends Entity {

    protected int speed;
    protected int decisionRate;
    protected long lastDecision;
    protected int fireRate;
    protected long lastShot;
    protected ArrayList<ArrayList<Vector2f>> points;
    protected ArrayList<Vector2f> collisionBox;


    public SaucerEntity(EntitiesManager em) {
        super(em);

        decisionRate = 3000;
        fireRate = 1500;
        speed = 2;

        points = new ArrayList<>();
        collisionBox = new ArrayList<>();
    }

    @ Override
    public void init(GameContainer gc) {
        long time = gc.getTime();

        lastDecision = time;
        lastShot = time;

        switch ((int)(Math.random() * 4 + 1)) {
            case 1:
                pos.set(0, 30);
                vel.set(speed, 0);
                break;
            case 2:
                pos.set(gc.getWidth(), 30);
                vel.set(-speed, 0);
                break;
            case 3:
                pos.set(0, gc.getHeight() - 25);
                vel.set(speed, 0);
                break;
            case 4:
                pos.set(gc.getWidth(), gc.getHeight() - 25);
                vel.set(-speed, 0);
                break;
        }
    }

    @ Override
    public void update(GameContainer gc) {
        long time = gc.getTime();

        // update direction
        if (time > lastDecision + decisionRate) {
            lastDecision = time;
            // if the saucer is flying inside the top or lower border we change direction,
            // if not we change direction with a 50% probability
            if (Math.random() < 0.5 || (vel.y == 0 && (pos.y < 30 || pos.y > gc.getHeight() - 25))) {
                if (vel.y == 0)
                    vel.add(Math.random() < .5 ? 35 : -35); // randomly turn up or down by 45º
                else
                    vel.set(Math.signum(vel.x) * speed, 0);
            }
        }

        // Update pos
        updatePos(gc);
    }

    @ Override
    public void render(GameContainer gc, Graphics g) {
        for (ArrayList<Vector2f> part : points)
            draw(g, part);
    }

    public void draw(Graphics g, ArrayList<Vector2f> points) {
        for (int i = 0; i < points.size(); i++) {
            Vector2f a = points.get(i).copy().add(pos);
            Vector2f b = points.get((i+1) % points.size()).copy().add(pos);
            g.drawLine(a.x, a.y, b.x, b.y);
        }
    }

    public ArrayList<Vector2f> getCollisionBox() {
        ArrayList<Vector2f> colBox = new ArrayList<>();
        for (Vector2f point : collisionBox)
            colBox.add(point.copy());
        return colBox;
    }

    public void destroy() {
        em.addEntity(new ParticlesEntity(em, pos));
        super.destroy();
    }
}

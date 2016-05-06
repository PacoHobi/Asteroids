package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by Termi on 10/09/2015.
 */
public class ShipEntity extends Entity {

    private ArrayList<Vector2f> points;
    private ArrayList<Vector2f> thrustPoints;
    private int direction;
    private int turnSpeed;
    private float acceleration;
    private float deceleration;
    private boolean inmune;
    private int maxMissiles;

    // respawn parameters
    private boolean respawning;
    private long respawnTime;
    private long inmuneUntil;

    public ShipEntity(EntitiesManager em) {
        super(em);
        direction = 0;
        turnSpeed = 5;
        acceleration = .1f;
        deceleration = .07f;
        inmune = false;
        maxMissiles = 4;

        points = new ArrayList<>();
        points.add(new Vector2f(15, 0));
        points.add(new Vector2f(-10, 10));
        points.add(new Vector2f(-10, -10));

        thrustPoints = new ArrayList<>();
        thrustPoints.add(new Vector2f(-10, 0));
        thrustPoints.add(new Vector2f(-10, 5));
        thrustPoints.add(new Vector2f(-10, -5));
    }

    @Override
    public void init(GameContainer gc) {
        pos.set(gc.getWidth() / 2, gc.getHeight() / 2);
        vel.set(0, 0);
    }

    @Override
    public void update(GameContainer gc) {
        Input input = gc.getInput();
        long time = gc.getTime();

        if (respawnTime > time)
            return;
        else
            respawning = false;

        if (inmuneUntil < time)
            inmune = false;

        // Handle input
        if (input.isKeyDown(Input.KEY_LEFT))
            directionAdd(-turnSpeed);
        if (input.isKeyDown(Input.KEY_RIGHT))
            directionAdd(turnSpeed);
        if (input.isKeyDown(Input.KEY_UP)) {
            accelerate();
            Vector2f p = thrustPoints.get(0);
            p.x = Math.max(p.x - 2, -25);
            p.y = (float)Math.floor(Math.random() * 3) - 1;
        } else {
            decelerate();
            Vector2f p = thrustPoints.get(0);
            p.x = Math.min(p.x + 2, -10);
        }
        if (input.isKeyPressed(Input.KEY_SPACE)) {
            int availableMissiles = maxMissiles - em.entitiesCount(ProjectileEntity.class);
            if (availableMissiles > 0) {
                Vector2f projSpawn = points.get(0).copy().add(direction).add(pos);
                em.addEntity(new ProjectileEntity(em, projSpawn, direction, this));
            }
        }
        if (input.isKeyPressed(Input.KEY_E))
            em.addEntity(new AsteroidEntity(
                    em, new Vector2f(0, 0),
                    (int)(Math.random() * 360),
                    3
            ));
        if (input.isKeyPressed(Input.KEY_R))
            em.addEntity(new LargeSaucerEntity(em));
        if (input.isKeyPressed(Input.KEY_T))
            em.addEntity(new SmallSaucerEntity(em));

        // Update ship position
        updatePos(gc);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        if (respawning)
            return;

        if (inmune)
            g.setColor(Color.gray);
        drawTriangle(g, thrustPoints);
        drawTriangle(g, points);
        g.setColor(Color.white);
    }

    public void drawTriangle(Graphics g, ArrayList<Vector2f> points) {
        ArrayList<Vector2f> rotatedPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Vector2f a =  points.get(i).copy();
            a.add(direction);
            a.add(pos);
            rotatedPoints.add(a);
        }
        for (int i = 0; i < rotatedPoints.size(); i++) {
            Vector2f a = rotatedPoints.get(i);
            Vector2f b = rotatedPoints.get((i+1) % rotatedPoints.size());
            g.drawLine(a.x, a.y, b.x, b.y);
        }
    }

    /**
     * Changes the ship's direction by the given angle
     * @param angle Angle to change the direction by
     */
    private void directionAdd(int angle) {
        direction += angle;
        while (direction < 0)
            direction += 360;
        if (direction > 360)
            direction %= 360;
    }

    public void respawn(GameContainer gc) {
        em.addEntity(new ParticlesEntity(em , pos));
        long time = gc.getTime();
        respawnTime = time + 2000;
        inmuneUntil = respawnTime + 2500;
        respawning = true;
        inmune = true;
        init(gc);
    }

    /**
     * Accelerate by the default acceleration
     */
    private void accelerate() {
        super.accelerate(acceleration, direction);
    }


    /**
     * Decelerate by default deceleration
     */
    private void decelerate() {
        super.decelerate(deceleration);
    }

    public ArrayList<Vector2f> getPoints() {
        ArrayList<Vector2f> points = new ArrayList<>();
        for (Vector2f p : this.points)
            points.add(p.copy());
        return points;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isInmune() {
        return inmune;
    }
}

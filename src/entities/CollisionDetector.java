package entities;

import main.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import utils.Geometry;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Termi on 10/09/2015.
 */
public class CollisionDetector {

    private Game game;
    private GameContainer gc;
    private EntitiesManager em;

    public CollisionDetector(Game game, GameContainer gc, EntitiesManager em) {
        this.game = game;
        this.gc = gc;
        this.em = em;
    }

    public void update() {
        ShipEntity ship = em.getShip();
        ArrayList<ProjectileEntity> projectiles = em.getEntities(ProjectileEntity.class);
        ArrayList<AsteroidEntity> asteroids = em.getEntities(AsteroidEntity.class);
        ArrayList<SaucerEntity> saucers = em.getEntities(SaucerEntity.class);

        // handle ship collisions
        if (ship != null && !ship.isInmune()) {
            // handle ship-asteroid collision
            for (AsteroidEntity asteroid : asteroids)
                if (collision(ship, asteroid)) {
                    ship.respawn(gc);
                    game.lifes--;
                }

            // handle ship-projectiles collisions
            for (ProjectileEntity projectile : projectiles)
                if (!projectile.getOrigin().equals(ship) && collision(ship, projectile)) {
                    projectile.destroy();
                    ship.respawn(gc);
                    game.lifes--;
                }

            // handle ship-saucer collision
            for (SaucerEntity saucer : saucers)
                if (collision(ship, saucer)) {
                    saucer.destroy();
                    ship.respawn(gc);
                    game.lifes--;
                }
        }

        // handle saucer-asteroid collision
        for (AsteroidEntity asteroid : asteroids) {
            for (SaucerEntity saucer : saucers) {
                if (collision(saucer, asteroid)) {
                    saucer.destroy();
                    asteroid.divideAsteroid();
                }
            }
        }

        // handle projectile-asteroid collision
        for (ProjectileEntity projectile : projectiles) {
            for (AsteroidEntity asteroid : asteroids) {
                if (collision(projectile, asteroid)) {
                    if (projectile.getOrigin().equals(em.getShip()))
                    switch (asteroid.getSize()) {
                        case 1: game.score += 100; break;
                        case 2: game.score += 50; break;
                        default: game.score += 20;
                    }
                    projectile.destroy();
                    asteroid.divideAsteroid();
                }
            }
        }

        // handle projectile-saucer collision
        for (ProjectileEntity projectile : projectiles) {
            for (SaucerEntity saucer : saucers) {
                if (collision(projectile, saucer)) {
                    // add score if destroyed by player
                    if (projectile.getOrigin().equals(em.getShip()))
                        if (saucer instanceof LargeSaucerEntity)
                            game.score += 200;
                        else if (saucer instanceof SmallSaucerEntity)
                            game.score += 1000;
                    projectile.destroy();
                    saucer.destroy();
                }
            }
        }
    }

    public boolean collision(ProjectileEntity projectile, AsteroidEntity asteroid) {
        return projectile.getPos().distance(asteroid.getPos()) < asteroid.getRadius();
    }

    public boolean collision(ShipEntity ship, AsteroidEntity asteroid) {
        ArrayList<Vector2f> shipPoints = ship.getPoints();

        for (int i = 0; i < shipPoints.size(); i++)
            shipPoints.get(i).add(ship.getDirection()).add(ship.getPos());

        for (int i = 0; i < shipPoints.size(); i++) {
            Vector2f a = shipPoints.get(i);
            Vector2f b = shipPoints.get((i+1) % shipPoints.size());

            if (Geometry.intersects(asteroid.getPos(), asteroid.getRadius(), a, b))
                return true;
        }
        return false;
    }

    public boolean collision(ShipEntity ship, ProjectileEntity projectile) {
        ArrayList<Vector2f> shipPoints = ship.getPoints();

        for (int i = 0; i < shipPoints.size(); i++)
            shipPoints.get(i).add(ship.getDirection()).add(ship.getPos());

        Vector2f a = shipPoints.get(0);
        Vector2f b = shipPoints.get(1);
        Vector2f c = shipPoints.get(2);

        return Geometry.intersects(projectile.getPos(), projectile.getRadius(), a, b, c);
    }

    public boolean collision(ShipEntity ship, SaucerEntity saucer) {
        ArrayList<Vector2f> shipPoints = ship.getPoints();
        for (Vector2f p : shipPoints)
            p.add(ship.getDirection()).add(ship.getPos());

        ArrayList<Vector2f> saucerColBox = saucer.getCollisionBox();
        for (Vector2f p : saucerColBox)
            p.add(saucer.pos);

        for (int i = 0; i < shipPoints.size(); i++) {
            Vector2f a = shipPoints.get(i);
            Vector2f b = shipPoints.get((i + 1) % shipPoints.size());

            for (int j = 0; j < saucerColBox.size(); j++) {
                Vector2f c =  saucerColBox.get(j);
                Vector2f d =  saucerColBox.get((j +1) % saucerColBox.size());

                if (Geometry.intersects(a, b, c, d))
                    return true;
            }
        }
        return false;
    }

    public boolean collision(ProjectileEntity projectile, SaucerEntity saucer) {
        // projectile can't hit his origin entity
        if (projectile.getOrigin().equals(saucer))
            return false;

        ArrayList<Vector2f> saucerColBox = saucer.getCollisionBox();
        for (Vector2f p : saucerColBox)
            p.add(saucer.pos);
        return Geometry.intersects(
                projectile.getPos(),
                projectile.getRadius(),
                saucerColBox.get(0),
                saucerColBox.get(1),
                saucerColBox.get(2),
                saucerColBox.get(3)
        );
    }

    public boolean collision(SaucerEntity saucer, AsteroidEntity asteroid) {
        ArrayList<Vector2f> saucerColBox = saucer.getCollisionBox();
        for (Vector2f p : saucerColBox)
            p.add(saucer.pos);
        return Geometry.intersects(
                asteroid.getPos(),
                asteroid.getRadius(),
                saucerColBox.get(0),
                saucerColBox.get(1),
                saucerColBox.get(2),
                saucerColBox.get(3)
        );
    }
}

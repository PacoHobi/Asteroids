package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import utils.Geometry;

import java.util.ArrayList;

/**
 * Created by Termi on 11/09/2015.
 */
public class BoardManager {

    private int board;
    private int minAsteroids;
    private int maxAsteroids;
    private int minSmallSaucers;
    private int maxSmallSaucers;
    private int minLargeSaucers;
    private int maxLargeSaucers;

    private GameContainer gc;
    private EntitiesManager em;


    public BoardManager(int board, GameContainer gc, EntitiesManager em) {
        this.gc = gc;
        this.em = em;

        if (board < 0)
            board = 0;
        this.board = board;

        minAsteroids = 4;
        maxAsteroids = 11;
        minSmallSaucers = 0;
        maxSmallSaucers = 3;
        minLargeSaucers = 0;
        maxLargeSaucers = 5;
    }

    public void update() {
        ArrayList<AsteroidEntity> asteroids = em.getEntities(AsteroidEntity.class);
        ArrayList<LargeSaucerEntity> largeSaucers = em.getEntities(LargeSaucerEntity.class);
        ArrayList<SmallSaucerEntity> smallSaucers = em.getEntities(SmallSaucerEntity.class);

        // check for next board
        if (asteroids.size() == 0 && largeSaucers.size() == 0 && smallSaucers.size() == 0) {
            board++;

            // Calculate number of asteroids
            int asteroidsToSpawn = (int)Geometry.lerp(minAsteroids, maxAsteroids, Math.random() * (board - 1) / 6);
            asteroidsToSpawn = Math.min(asteroidsToSpawn, maxAsteroids);

            // Calculate number of large saucers
            int largeSaucersToSpawn = (int)Geometry.lerp(minSmallSaucers, maxLargeSaucers, Math.random() * (board - 1) / 4);
            largeSaucersToSpawn = Math.min(largeSaucersToSpawn, maxLargeSaucers);

            // Calculate number of small saucers
            int smallSaucersToSpawn = (int)Geometry.lerp(minSmallSaucers, maxSmallSaucers, Math.random() * (board - 1) / 4);
            smallSaucersToSpawn = Math.min(smallSaucersToSpawn, maxSmallSaucers);

            // Spawn asteroids
            for (int i = 0; i < asteroidsToSpawn; i++)
                em.addEntity(
                        new AsteroidEntity(em, randomSpawn(), (int)(Math.random() * 360), 3)
                );

            // Spawn large saucers
            for (int i = 0; i < largeSaucersToSpawn; i++)
                em.addEntity(
                        new LargeSaucerEntity(em)
                );

            // Spawn small saucers
            for (int i = 0; i < smallSaucersToSpawn; i++)
                em.addEntity(
                        new SmallSaucerEntity(em)
                );

            System.out.printf("Board %d (asteroids = %d)\n", board, asteroidsToSpawn);
        }
    }

    private Vector2f randomSpawn() {
        float x = 0;
        float y = 0;
        switch ((int)(Math.random() * 2)) {
            case 0:
                x = (float)(Math.random() * gc.getWidth());
                break;
            case 1:
                y = (float)(Math.random() * gc.getHeight());
                break;
        }
        return new Vector2f(x, y);
    }

    public int getBoard() {
        return board;
    }
}

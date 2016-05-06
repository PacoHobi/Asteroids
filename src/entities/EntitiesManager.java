package entities;

import main.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Termi on 10/09/2015.
 */
public class EntitiesManager {

    private Game game;
    private GameContainer gc;
    private Graphics g;
    private List<Entity> entities;

    // important entities
    private ShipEntity ship;

    public EntitiesManager(Game game, GameContainer gc, Graphics g) {
        this.game = game;
        this.gc = gc;
        this.g = g;
        entities = new ArrayList<>();
    }

    public void init() {
        for (int i = 0; i < entities.size(); i++)
            entities.get(i).init(gc);
    }

    public void update() {
        for (int i = 0; i < entities.size(); i++)
            entities.get(i).update(gc);
    }

    public void render() {
        for (int i = 0; i < entities.size(); i++)
            entities.get(i).render(gc, g);
    }

    public void addEntity(Entity e) {
        if (e instanceof AsteroidEntity && getEntities(AsteroidEntity.class).size() >= game.getMaxAsteroids())
            return;

        entities.add(e);
        e.init(gc);

        if (e instanceof ShipEntity) {
            ship = (ShipEntity) e;
        }
    }

    public void destroyEntity(Entity e) {
        entities.remove(e);

        if (ship != null && entities.indexOf(ship) == -1)
            ship = null;
    }

    public int entitiesCount() {
        return entities.size();
    }

    /**
     * Returns the number of entities of the given Entity subclass
     * @param type Entity subclass of the entities to count
     * @return The number of entities of the given Entity subclass
     */
    public int entitiesCount(Class<? extends Entity> type) {
        int counter = 0;
        for (Entity e : entities)
            if (type.isInstance(e))
                counter++;
        return counter;
    }

    public ShipEntity getShip() {
        return ship;
    }

    public Iterator getEntities() {
        return entities.iterator();
    }

    /**
     * Returns all the entities of the given Entity subclass
     * @param type A subclass of Entity
     * @param <T> Subclass of Entity
     * @return All entities of the given Entity subclass
     */
    public <T extends Entity> ArrayList<T> getEntities(Class<T> type) {
        ArrayList<T> ent = new ArrayList<>();
        for (Entity e : entities)
            if (type.isInstance(e))
                ent.add((T)e);
        return ent;
    }
}

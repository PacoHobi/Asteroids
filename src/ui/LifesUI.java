package ui;

import main.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by Termi on 10/09/2015.
 */
public class LifesUI extends UIElement {

    private int lifes;
    private ArrayList<Vector2f> points;

    public LifesUI(UIManager uim, Game game) {
        super(uim, game);
    }

    @ Override
    public void init(GameContainer gc) {
        pos.set(20, 50);

        points = new ArrayList<>();
        points = new ArrayList<>();
        points.add(new Vector2f(5, 0));
        points.add(new Vector2f(0, 12));
        points.add(new Vector2f(10, 12));
    }

    @ Override
    public void update(GameContainer gc) {
        long time = gc.getTime();
        Input input = gc.getInput();

        if (!hidden && gc.getTime() - lastRefresh > refreshTime) {
            lastRefresh = gc.getTime();

            lifes = game.lifes;
        }
    }

    @ Override
    public void render(GameContainer gc, Graphics g) {
        for (int i = 0; i < lifes; i++) {
            drawTriangle(g, pos.copy().add(new Vector2f(20,0).scale(i)));
        }
    }

    private void drawTriangle(Graphics g, Vector2f pos) {
        for (int i = 0; i < points.size(); i++) {
            Vector2f a = points.get(i).copy().add(pos);
            Vector2f b = points.get((i+1) % points.size()).copy().add(pos);
            g.drawLine(a.x, a.y, b.x, b.y);
        }
    }
}

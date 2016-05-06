package ui;


import main.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by Termi on 10/09/2015.
 */
public class UIManager {

    private Game game;
    private GameContainer gc;
    private Graphics g;
    private ArrayList<UIElement> elements;

    public UIManager(Game game, GameContainer gc, Graphics g) {
        this.game = game;
        this.gc = gc;
        this.g = g;
        elements = new ArrayList<>();
    }

    public void init() {
        for (int i = 0; i < elements.size(); i++)
            elements.get(i).init(gc);
    }

    public void update() {
        for (int i = 0; i < elements.size(); i++)
            elements.get(i).update(gc);
    }

    public void render() {
        for (int i = 0; i < elements.size(); i++)
            elements.get(i).render(gc, g);
    }

    public void addElement(UIElement e) {
        elements.add(e);
        e.init(gc);
    }

    public void destroyEntity(UIElement e) {
        elements.remove(e);
    }
}

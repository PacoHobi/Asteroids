package ui;

import main.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Termi on 10/09/2015.
 */
public class UIElement {

    protected UIManager uim;
    protected Game game;
    protected int refreshTime;
    protected long lastRefresh;
    protected Vector2f pos;
    protected boolean hidden;

    public UIElement(UIManager uim, Game game) {
        this.uim = uim;
        this.game = game;
        refreshTime = 100;
        pos = new Vector2f(0, 0);
        hidden = false;
    }

    public void init(GameContainer gc) {}

    public void update(GameContainer gc) {}

    public void render(GameContainer gc, Graphics g) {

    }

    protected void showHide() {
        hidden = !hidden;
    }
}

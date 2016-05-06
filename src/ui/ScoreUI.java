package ui;

import main.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Termi on 10/09/2015.
 */
public class ScoreUI extends UIElement {

    private int score;

    public ScoreUI(UIManager uim, Game game) {
        super(uim, game);
    }

    @ Override
    public void init(GameContainer gc) {
        pos.set(20, 20);
    }

    @ Override
    public void update(GameContainer gc) {
        long time = gc.getTime();

        if (!hidden && gc.getTime() - lastRefresh > refreshTime) {
            lastRefresh = gc.getTime();
            score = game.score;
        }
    }

    @ Override
    public void render(GameContainer gc, Graphics g) {
        if (!hidden)
            g.drawString("" + score, pos.x, pos.y);
    }
}

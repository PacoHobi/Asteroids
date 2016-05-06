package ui;

import main.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Termi on 10/09/2015.
 */
public class StatsUI extends UIElement {

    private String text;
    private int entitiesCount;
    private float shipSpeed;
    private boolean shipInmune;
    private int board;

    public StatsUI(UIManager uim, Game game) {
        super(uim, game);
    }

    @ Override
    public void init(GameContainer gc) {
        pos.set(gc.getWidth() - 120, 10);
        text = "";
    }

    @ Override
    public void update(GameContainer gc) {
        long time = gc.getTime();
        Input input = gc.getInput();

        // handle input
        if (input.isKeyPressed(Input.KEY_P))
            showHide();

        // refresh
        if (!hidden && gc.getTime() - lastRefresh > refreshTime) {
            lastRefresh = gc.getTime();

            entitiesCount = game.em.entitiesCount();
            shipInmune = game.em.getShip().isInmune();
            board = game.bm.getBoard();


            // ship speed
            if (game.em.getShip() != null)
                shipSpeed = game.em.getShip().getVel().length();
            else
                shipSpeed = 0;

            text = String.format(
                    "Board: %d\n" +
                    "Speed: %.2f\n" +
                    "Inmune: %b\n" +
                    "Entities: %d\n",
                    board,
                    shipSpeed,
                    shipInmune,
                    entitiesCount
            );
        }
    }

    @ Override
    public void render(GameContainer gc, Graphics g) {
        if (!hidden)
             g.drawString(text, pos.x, pos.y);
    }
}

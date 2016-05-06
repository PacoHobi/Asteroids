package ui;

import main.Game;
import org.newdawn.slick.*;

/**
 * Created by Termi on 10/09/2015.
 */
public class GameOverUI extends UIElement {

    private String score;

    public GameOverUI(UIManager uim, Game game) {
        super(uim, game);
    }

    @ Override
    public void init(GameContainer gc) {
        score = "Score: " + game.score;
    }

    @ Override
    public void update(GameContainer gc) {
        Input input = gc.getInput();

        if (input.isKeyPressed(Input.KEY_SPACE))
            try {
                game.init(gc);
                uim.destroyEntity(this);
            } catch (SlickException e) {
                e.printStackTrace();
            }
    }

    @ Override
    public void render(GameContainer gc, Graphics g) {
        int width = gc.getWidth();
        int height = gc.getHeight();
        if (!hidden) {
            g.setColor(Color.black);
            g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
            g.setColor(Color.white);

            String s = "GAME OVER";
            g.drawString(s, width/2 - g.getFont().getWidth(s)/2, height/2 - 20);
            g.drawString(score, width/2 - g.getFont().getWidth(score)/2, height/2);
            s = "Press space to restart";
            g.drawString(s, width/2 - g.getFont().getWidth(s)/2, height/2 + 20);
        }
    }
}

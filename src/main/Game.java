package main;

import entities.*;
import org.newdawn.slick.*;
import ui.*;

/**
 * Created by Termi on 10/09/2015.
 */
public class Game extends BasicGame {

    public EntitiesManager em;
    public UIManager uim;
    public CollisionDetector cd;
    public BoardManager bm;

    private int maxAsteroids;
    private int extraLifeEach;
    private int lastExtraLifeAt;
    public int lifes;
    public int score;
    public boolean gameOver;

    public Game(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        maxAsteroids = 26;
        extraLifeEach = 10000;
        lastExtraLifeAt = 0;
        lifes = 3;
        score = 0;
        gameOver = false;

        // Entities manager
        em = new EntitiesManager(this, gc, gc.getGraphics());
        em.addEntity(new ShipEntity(em));
        em.init();

        // UI manager
        uim = new UIManager(this, gc, gc.getGraphics());
        uim.addElement(new LifesUI(uim, this));
        uim.addElement(new ScoreUI(uim, this));
//        uim.addElement(new StatsUI(uim, this));
        uim.init();

        // Collision detector
        cd = new CollisionDetector(this, gc, em);

        // Board manager
        bm = new BoardManager(0, gc, em);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (score / extraLifeEach > lastExtraLifeAt) {
            lastExtraLifeAt = score / extraLifeEach;
            lifes++;
        }

        uim.update();

        if (lifes <= 0) {
            if (!gameOver) {
                gameOver = true;
                uim.addElement(new GameOverUI(uim, this));
            }
            return;
        }

        em.update();
        cd.update();
        bm.update();
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        em.render();
        uim.render();
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game("main.Game"));
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(60);
        app.setShowFPS(false);
        app.start();
    }

    public int getMaxAsteroids() {
        return maxAsteroids;
    }
}

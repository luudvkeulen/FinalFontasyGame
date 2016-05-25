package com.ffxvi.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ffxvi.game.models.Player;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.screens.MenuScreen;
import com.ffxvi.game.screens.PreGameScreen;

/**
 * The main controller of the game.
 */
public class MainClass extends Game implements ApplicationListener {

    /**
     * Holds the instance of MainClass.
     */
    private static final MainClass MAINCLASS = new MainClass();

    /**
     * An int containing the width of the screen.
     */
    public static int WIDTH;
    
    /**
     * An int containing the height of the screen.
     */
    public static int HEIGHT;
    
    /**
     * The camera.
     */
    public static OrthographicCamera camera;
    
    /**
     * The main player of the client.
     */
    public static Player mainPlayer;
    
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private PreGameScreen preGameScreen;

    public void setMenuScreen() {
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    public void setGameScreen() {
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }

    public void setPreGameScreen() {
        preGameScreen = new PreGameScreen(this);
        setScreen(preGameScreen);
    }
    
    private MainClass()
    {
        
    }
    
    public static MainClass getInstance()
    {
        return MAINCLASS;
    }

    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        setMenuScreen();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

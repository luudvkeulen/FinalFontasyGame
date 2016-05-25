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

    /**
     * The screen of the menu.
     */
    private MenuScreen menuScreen;

    /**
     * The screen of the game.
     */
    private GameScreen gameScreen;

    /**
     * The screen which is shown before the game is shown.
     */
    private PreGameScreen preGameScreen;

    /**
     * Sets the menu screen.
     */
    public void setMenuScreen() {
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    /**
     * Sets the game screen.
     */
    public void setGameScreen() {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

    /**
     * Sets the pre game screen.
     */
    public void setPreGameScreen() {
        preGameScreen = new PreGameScreen(this);
        setScreen(preGameScreen);
    }

    /**
     * Private constructor for singleton.
     */
    private MainClass() {

    }

    /**
     * Gets the instance of this class.
     *
     * @return The instance of this class.
     */
    public static MainClass getInstance() {
        return MAINCLASS;
    }

    /**
     * Creates the GUI for a new game.
     */
    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        setMenuScreen();
    }

    /**
     * Is run when the screen should be rendered. This method contains all
     * MainClass related drawing code.
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    /**
     * Resizes this screen to the given width and height.
     *
     * @param width the new width.
     * @param height the new height.
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * Pauses the game.
     */
    @Override
    public void pause() {
        super.pause();
    }

    /**
     * Resumes the game.
     */
    @Override
    public void resume() {
        super.resume();
    }

    /**
     * Disposes the game.
     */
    @Override
    public void dispose() {
        super.dispose();
    }
}

/*
 * (C) Copyright 2016 - S33A
 * Final Fontasy XVI, Version 1.0.
 * 
 * Contributors:
 *   Pim Janissen
 *   Luud van Keulen
 *   Robin de Kort
 *   Koen Schilders
 *   Guido Thomasse
 *   Joel Verbeek
 */
package com.ffxvi.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.ffxvi.game.screens.MenuScreen;

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
	public int WIDTH;

	/**
	 * An int containing the height of the screen.
	 */
	public int HEIGHT;

	/**
	 * The camera.
	 */
	public OrthographicCamera camera;

	/**
	 * The screen of the menu.
	 */
	private MenuScreen menuScreen;

	/**
	 * The selected ip from serverbrowser
	 */
	public String selectedIp;

	/**
	 * background for all menus
	 */
	public static Texture background;
	/**
	 * Private constructor for singleton.
	 */
	//private MainClass() {}
	
	/**
	 * Sets the menu screen.
	 */
	public void setMenuScreen() {
		menuScreen = new MenuScreen();
		setScreen(menuScreen);
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
		background = new Texture(Gdx.files.internal("background.png"));
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
}

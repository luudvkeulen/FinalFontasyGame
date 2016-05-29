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
package com.ffxvi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.ffxvi.game.MainClass;

/**
 * The screen for the settings.
 */
public class OptionsScreen implements Screen {

    /**
     * The game controller.
     */
    private final MainClass game;

    /**
     * Initializes a new OptionsScreen.
     */
    public OptionsScreen() {
        this.game = MainClass.getInstance();
    }

    /**
     * Is executed each time this screen is redrawn. ALl logic regarding drawing
     * is executed here.
     *
     * @param delta the time between the last and current use of this method
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getScreen().dispose();
            game.setScreen(new MenuScreen());
            return;
        }

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void show() {
        
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }
}

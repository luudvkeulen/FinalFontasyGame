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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
	 * The stage for the screen.
	 */
	private final Stage stage;
	
	/**
	 * The layout.
	 */
	private final GlyphLayout layout;
	
	/**
	 * A label for rendering the header text.
	 */
	private final Label headerLabel;

    /**
     * Initializes a new OptionsScreen.
     */
    public OptionsScreen() {
        this.game = MainClass.getInstance();
		this.stage = new Stage();
		this.layout = new GlyphLayout();
		Gdx.input.setInputProcessor(this.stage);
		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		BitmapFont bfont = new BitmapFont();
		skin.add("default", bfont);
		
		// Create header text
		this.headerLabel = new Label("OPTIONS", skin);
		this.headerLabel.setFontScale(2);
		this.headerLabel.setPosition((this.stage.getWidth() / 2) - (this.layout.width / 2) - this.headerLabel.getWidth(), this.stage.getHeight() - 50);

		this.stage.addActor(this.headerLabel);
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
            this.game.getScreen().dispose();
            this.game.setScreen(new MenuScreen());
            return;
        }

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		this.stage.draw();
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

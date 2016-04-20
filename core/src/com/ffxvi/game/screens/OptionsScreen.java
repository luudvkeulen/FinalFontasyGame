package com.ffxvi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.ffxvi.game.MainClass;

public class OptionsScreen implements Screen {	
	private final MainClass game;
	
	public OptionsScreen (final MainClass game) {
		this.game = game;	
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float f) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			game.setScreen(new MenuScreen(game));
			return;
		}
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int w, int h) {
		
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

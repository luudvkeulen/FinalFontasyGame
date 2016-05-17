/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.entities.PlayerCharacter;
import java.util.Random;

public class ServerBrowser implements Screen {
	
	private Skin skin;
	private SpriteBatch batch;
	private Stage stage;
	
	private MainClass game;
	
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 60;
	private static final int BUTTON_OFFSET = 30;
	
	private Sprite sprite;
	
	// TEMP SERVER LIST
	private List servers;
	
	
	
	
	private ScrollPane scrollPane;
	private Table table;
	
	
	public ServerBrowser(final MainClass game) {
		this.game = game;
	}

	@Override
	public void show() {
		
		this.stage = new Stage();
		
		Gdx.input.setInputProcessor(this.stage);
		
		
		
		this.skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		
		
		// TEMP SERVER LIST
		this.servers = new List(this.skin);
		this.servers.setItems(new String[] {});
		
		
		this.batch = new SpriteBatch();
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));
		
		// Store the default libgdx font under the name "default".
		BitmapFont bfont=new BitmapFont();
		//bfont.scale(1);
		skin.add("default",bfont);
		
		
		this.scrollPane = new ScrollPane(servers, skin);
		
		
		table = new Table(this.skin);
		this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.scrollPane.setSmoothScrolling(false);
		this.table.add(this.scrollPane).size(800, 600);
		table.debug();
		
		
		this.stage.addActor(this.table);
		
		this.refreshServers();
	}
	
	private void refreshServers() {
		String[] randomIps = new String[20];
		
		Random rnd = new Random();
		
		for (int i = 0; i < randomIps.length; i++) {
			randomIps[i] = "192.168." + (rnd.nextInt(89) + 10) + "." + (rnd.nextInt(890) + 10) + ":" + (rnd.nextInt(100) + 3000);
		}
		
		this.servers.setItems(randomIps);
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			this.game.setScreen(new MenuScreen(game));
			return;
		}
		
		// Draw background color
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
	
}

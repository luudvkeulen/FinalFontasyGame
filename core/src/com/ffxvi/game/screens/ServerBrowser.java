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
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.entities.PlayerCharacter;
import com.ffxvi.game.serverlist.ServerRetriever;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	ServerRetriever serverRetriever;
	
	
	
	
	private ScrollPane scrollPane;
	private Table table;
	
	
	public ServerBrowser(final MainClass game) {
		this.game = game;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		this.skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		this.skin.add("white", new Texture(pixmap));

		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		BitmapFont bfont = new BitmapFont();
		//bfont.scale(1);
		skin.add("default",bfont);

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.WHITE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

		textButtonStyle.font = skin.getFont("default");

		skin.add("default", textButtonStyle);
		
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		final TextButton playButton = new TextButton("PLAY",textButtonStyle);
		playButton.setPosition((stage.getWidth()/2) - (playButton.getWidth()/2), (playButton.getHeight()/2) - 10);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreen().dispose();
				game.setScreen(new PreGameScreen(game));
			}
		});
		stage.addActor(playButton);
		
		/* Draw server overview */
		try {
			this.serverRetriever = new ServerRetriever();
		} catch (IOException ex) {
			Logger.getLogger(ServerBrowser.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		this.servers = new List(skin);
		servers.getSelection().setMultiple(true);
		servers.getSelection().setRequired(false);
		this.servers.setItems(new String[] {});
		
		this.scrollPane = new ScrollPane(servers, this.skin);
		
		table = new Table(this.skin);
		this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.scrollPane.setSmoothScrolling(false);
		this.table.add(this.scrollPane).size(800, 600);
		table.debug();
		
		this.stage.addActor(this.table);
		
		try {
			this.refreshServers();
		} catch (RemoteException ex) {
			Logger.getLogger(ServerBrowser.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void refreshServers() throws RemoteException {
		String[] serverAddresses = serverRetriever.getAddresses().toArray(new String[0]);
		
		this.servers.setItems(serverAddresses);
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

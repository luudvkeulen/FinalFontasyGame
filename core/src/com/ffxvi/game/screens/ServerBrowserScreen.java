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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.serverlist.ServerRetriever;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import queryServer.IServer;

/**
 * A screen used for selecting a server to play on.
 */
public class ServerBrowserScreen implements Screen {

	/**
	 * The width of buttons.
	 */
	private static final int BUTTON_WIDTH = 200;

	/**
	 * The height of buttons.
	 */
	private static final int BUTTON_HEIGHT = 60;

	/**
	 * The offset of buttons.
	 */
	private static final int BUTTON_OFFSET = 30;

	/**
	 * The stage.
	 */
	private Stage stage;

	/**
	 * The game controller.
	 */
	private final MainClass game;

	// TEMP SERVER LIST
	/**
	 * A list of servers which the user can connect to.
	 */
	private List<String> servers;

	/**
	 * A scrollpane GUI control.
	 */
	private ScrollPane scrollPane;

	/**
	 * A table used for displaying the server list.
	 */
	private Table table;

	/**
	 * The ServerRetriever for getting the server list.
	 */
	private ServerRetriever serverRetriever;

	/**
	 * The layout.
	 */
	private GlyphLayout layout;

	/**
	 * A label for rendering the header text.
	 */
	private Label headerLabel;
	
	/**
	 * A playButton to enter a game.
	 */
	private TextButton playButton;

	/**
	 * Initializes a new ServerBrowserScreen.
	 */
	public ServerBrowserScreen() {
		this.game = MainClass.getInstance();
	}

	/**
	 * Shows the screen.
	 */
	@Override
	public void show() {
		this.stage = new Stage();
		this.layout = new GlyphLayout();
		Gdx.input.setInputProcessor(this.stage);

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		Skin buttonSkin = new Skin();

		Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		buttonSkin.add("white", new Texture(pixmap));

		BitmapFont bfont = new BitmapFont();
		skin.add("default", bfont);
		buttonSkin.add("default", bfont);

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = buttonSkin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = buttonSkin.newDrawable("white", Color.WHITE);
		textButtonStyle.over = buttonSkin.newDrawable("white", Color.LIGHT_GRAY);

		textButtonStyle.font = buttonSkin.getFont("default");

		buttonSkin.add("default", textButtonStyle);

		// Create header text
		this.headerLabel = new Label("SERVER BROWSER", skin);
		this.headerLabel.setFontScale(2);
		this.headerLabel.setPosition((this.stage.getWidth() / 2) - (this.layout.width / 2) - this.headerLabel.getWidth(), this.stage.getHeight() - 50);

		this.stage.addActor(this.headerLabel);

		final Sound click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
		
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		playButton = new TextButton("PLAY", textButtonStyle);
		playButton.setPosition((this.stage.getWidth() / 2) - (playButton.getWidth() / 2), (playButton.getHeight() / 2) - 20);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				click.play();
				game.selectedIp = servers.getSelected();
				game.getScreen().dispose();
				game.setScreen(new PreGameScreen());
			}
		});
		playButton.setTouchable(Touchable.disabled);
		this.stage.addActor(playButton);

		/* Connect to the RMI Server */
		try {
			this.serverRetriever = new ServerRetriever();
		} catch (IOException ex) {
			Logger.getLogger(ServerBrowserScreen.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.servers = new List(skin);
		this.servers.getSelection().setMultiple(true);
		this.servers.getSelection().setRequired(false);
		this.servers.setItems(new String[]{});
		this.servers.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
				if (servers.getSelected() == null) {
					playButton.setTouchable(Touchable.disabled);
				} else {
					playButton.setTouchable(Touchable.enabled);
				}
			}
		});

		this.scrollPane = new ScrollPane(servers, skin);

		this.table = new Table(skin);
		this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.scrollPane.setSmoothScrolling(false);
		this.table.add(this.scrollPane).size(800, 600);
		//this.table.debug(); //Deze laat lijnen rondom de serverlist zien voor te debuggen

		this.stage.addActor(this.table);

		try {
			this.refreshServers();
		} catch (RemoteException ex) {
			Logger.getLogger(ServerBrowserScreen.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Refreshes the server list.
	 *
	 * @throws RemoteException When an (un)expected remote exception occurs.
	 */
	private void refreshServers() throws RemoteException {
		//String[] serverAddresses = serverRetriever.getAddresses().toArray(new String[0]);
		java.util.List<IServer> serverlist = serverRetriever.getIServers();
		Array serverNames = new Array();
		for (IServer s : serverlist) {
			serverNames.add(s.getPlayers() + "/16 | " + s.getName() + " - " + s.getFullAddress());
		}
		this.servers.setItems(serverNames);
	}

	/**
	 * Is executed when the screen is (re)drawn. Contains all logic regarding
	 * the drawing in the ServerBrowserScreen.
	 *
	 * @param delta The time which has passed since the last update.
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

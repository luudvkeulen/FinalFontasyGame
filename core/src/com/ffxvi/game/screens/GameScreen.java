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
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Timer;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.client.Client;
import com.ffxvi.game.entities.Direction;
import com.ffxvi.game.entities.Player;
import com.ffxvi.game.entities.PlayerCharacter;
import com.ffxvi.game.entities.Projectile;
import com.ffxvi.game.entities.SimplePlayer;
import com.ffxvi.game.entities.SimpleProjectile;
import com.ffxvi.game.logics.ChatManager;
import com.ffxvi.game.logics.InputManager;
import com.ffxvi.game.models.Map;
import com.ffxvi.game.models.MapType;
import com.ffxvi.game.support.Shake;
import com.ffxvi.game.support.SkinManager;
import com.ffxvi.game.support.Vector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The screen for the game.
 *
 */
public class GameScreen implements Screen, Observer {

	/**
	 * The main class for the game.
	 */
	private final MainClass game;

	/**
	 * The manager for all player textures (skins)
	 */
	private static final SkinManager skinManager = new SkinManager();

	/**
	 * The main player of the game.
	 */
	private Player mainPlayer;

	//Multiplayer
	/**
	 * The players which are in the room.
	 */
	private List<SimplePlayer> multiplayers;

	/**
	 * The received player's data that is currently being rendered.
	 */
	private Player multiplayer;

	/**
	 * The code for this client.
	 */
	public final Client client;

	//Map related
	/**
	 * The tile map renderer.
	 */
	private OrthogonalTiledMapRenderer renderer;

	/**
	 * The map of this screen.
	 */
	private static Map map;

	/**
	 * A list of all the maps in the game.
	 */
	private final List<Map> maps;

	/**
	 * A list of all the possible map types.
	 */
	private final List<MapType> mapTypes;

	//Chat related
	/**
	 * The labels in which the chat messages are shown.
	 */
	private final List<Label> oldchatlabels;

	/**
	 * The controller class for chat related issues.
	 */
	public final ChatManager chatManager;

	/**
	 * The shape renderer.
	 */
	private final ShapeRenderer shape;

	/**
	 * A SpriteBatch which contains all the sprites which are used.
	 */
	private final SpriteBatch batch;

	/**
	 * An ArrayList containing all projectiles which are in the room.
	 */
	protected static List<Projectile> projectiles;

	/**
	 * The stage.
	 */
	private final Stage stage;

	/**
	 * The skin.
	 */
	private final Skin skin;

	//HUD Related
	/**
	 * Label for the player name on top of the player.
	 */
	private final Label playerLabelName;

	/**
	 * Label for the player name on the HUD.
	 */
	private final Label playerLabelNameHUD;

	/**
	 * Label for the health counter on top of the player.
	 */
	private final Label playerHealthLabel;

	/**
	 * Label for the health counter on the HUD.
	 */
	private final Label playerHealthLabelHUD;

	/**
	 * Label for the score on the HUD.
	 */
	private final Label scoreLabel;

	/**
	 * TextField for chat messages.
	 */
	private final TextField textfield;

	/**
	 * font for drawing player names
	 */
	private final BitmapFont fontwhite;

	/**
	 * font for drawing health
	 */
	private final BitmapFont fontred;

	/**
	 * A controller class which handles all the keyboard/mouse/controller input
	 * for the game.
	 */
	private InputManager inputManager;

	/**
	 * Dialog to show messages.
	 */
	private Dialog messageDialog;
	
	/**
	 * Boolean indicating whether to render the scoreboard.
	 */
	private boolean renderScoreboard;	
	
	/**
	 * Shake used for shaking the camera when the player is hit.
	 */
	private Shake shake;

	/**
	 * Initializes a new GameScreen.
	 */
	public GameScreen() {
		this.game = MainClass.getInstance();
		this.stage = new Stage();
		this.shake = new Shake();
		
		this.chatManager = new ChatManager(this);

		this.fontwhite = new BitmapFont();
		this.fontred = new BitmapFont();
		this.fontred.setColor(Color.RED);

		if (!game.selectedIp.equals("")) {
			String fulltext = game.selectedIp.replaceAll("\\s+","");
 			String fullip = fulltext.substring(fulltext.indexOf("-") + 1);
 			System.out.println(fullip);
  			this.client = new Client(fullip.substring(0, fullip.indexOf(":")), Integer.parseInt(fullip.substring(fullip.indexOf(":") + 1)), 1337, this);
  			System.out.println(fullip.substring(0, fullip.indexOf(":")) + Integer.parseInt(fullip.substring(fullip.indexOf(":") + 1)));
		} else {
			this.client = null;
			System.out.println("Error no ip selected");
		}

		//Setup map stuff
		this.maps = new ArrayList();
		this.mapTypes = new ArrayList();
		this.loadMaps();

		Gdx.input.setInputProcessor(stage);

		this.skin = new Skin(Gdx.files.internal("uiskin.json"));

		// Store the default libgdx font under the name "default".
		BitmapFont bfont = new BitmapFont();
		this.skin.add("default", bfont);

		this.shape = new ShapeRenderer();
		this.batch = new SpriteBatch();

		GameScreen.projectiles = new ArrayList();
		this.multiplayers = new ArrayList();
		this.multiplayer = new Player(this);

		this.textfield = new TextField("", skin);
		this.textfield.setPosition(10, Gdx.graphics.getHeight() - 200);
		this.textfield.setWidth(300);
		this.stage.addActor(this.textfield);

		// Setup dialog
		this.messageDialog = new Dialog("This is a message", skin);
		this.messageDialog.setVisible(false);
		this.messageDialog.setPosition((Gdx.graphics.getWidth() / 2) - (this.messageDialog.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (this.messageDialog.getHeight() / 2));

		//Setup labels
		////Setup label variables
		this.playerLabelName = new Label("", this.skin);
		this.playerLabelNameHUD = new Label("", this.skin);
		this.playerHealthLabel = new Label("100", this.skin);
		this.playerHealthLabelHUD = new Label("100", this.skin);
		this.scoreLabel = new Label("5000", this.skin);
		////Setup label fontscales
		this.playerHealthLabelHUD.setFontScale(2);
		this.playerLabelNameHUD.setFontScale(2);
		this.scoreLabel.setFontScale(2);
		////Setup label colors
		this.playerHealthLabel.setColor(Color.RED);
		this.playerHealthLabelHUD.setColor(Color.RED);
		this.scoreLabel.setColor(Color.YELLOW);
		////Setup label height
		this.playerLabelNameHUD.setHeight(20);
		this.scoreLabel.setHeight(20);
		this.playerHealthLabelHUD.setHeight(20);
		////Add labels to stage
		this.stage.addActor(this.playerHealthLabel);
		this.stage.addActor(this.playerHealthLabelHUD);
		this.stage.addActor(this.playerLabelName);
		this.stage.addActor(this.playerLabelNameHUD);
		this.stage.addActor(this.scoreLabel);

		this.oldchatlabels = new ArrayList();
	}

	public static SkinManager getSkinManager() {
		return skinManager;
	}
	
	/**
	 * Toggles the boolean to render the scoreboard.
	 */
	public void toggleShowScoreboard() {
		this.renderScoreboard = !this.renderScoreboard;
	}

	/**
	 * Gets the current map.
	 *
	 * @return The current map.
	 */
	public static Map getCurrentMap() {
		return map;
	}

	/**
	 * Loads all the maps for this game.
	 */
	private void loadMaps() {
		this.mapTypes.add(new MapType(1, "level1"));
		this.mapTypes.add(new MapType(2, "level2"));
		this.mapTypes.add(new MapType(3, "level3"));
		this.mapTypes.add(new MapType(4, "level4"));

		for (MapType mapType : this.mapTypes) {
			this.maps.add(new Map(mapType.getName() + ".tmx", mapType.getId()));
		}
	}

	/**
	 * Adds a player to this screen.
	 *
	 * @param playerName The name of the player. When an empty String (excluding
	 * spaces), an IllegalArgumentException is thrown.
	 * @param character The character of this player.
	 */
	public void addPlayer(String playerName, PlayerCharacter character) {

		if (playerName == null || playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("Player name can't be null nor an empty string (excluding spaces).");
		}

		if (character == null) {
			throw new IllegalArgumentException("Character can not be null.");
		}

		map = getRandomMap();

		this.mainPlayer = new Player(character, playerName, new Vector(64f, 64f), this, map.getId());

		this.mainPlayer.setPosition(64, 64);
		this.client.sendPlayer(new SimplePlayer(this.mainPlayer));

		this.playerLabelName.setText(playerName);
		this.playerLabelNameHUD.setText(playerName);

		this.inputManager = new InputManager(this.mainPlayer);
		this.inputManager.addObserver(this);

		this.sendChatMessage("[SERVER]", this.mainPlayer.getName() + " HAS CONNECTED");
	}

	public Map getRandomMap() {
		int idx  = new Random().nextInt(this.maps.size());
		while (idx == 1) {
			idx  = new Random().nextInt(this.maps.size());
		}
		return maps.get(idx);
	}

	public void Respawn(String killer) {
		Collection<SimplePlayer> localMultiplayers = this.getMultiplayers();
		for (SimplePlayer splayer : localMultiplayers) {
			if (splayer.getName().equals(killer)) {
				splayer.increaseScore();
				this.client.sendPlayer(splayer);
				break;
			}
		}

		updatePlayerHealthLabels(mainPlayer.getHitPoints());

		int mapid = getRandomMap().getId();
		mainPlayer.setRoomId(mapid);

		setLevel(mapid, Direction.UPLEFT);
		this.mainPlayer.setPosition(64, 64);

		this.client.sendPlayer(new SimplePlayer(mainPlayer));
	}

	/**
	 * Adds a list of other players to this game.
	 *
	 * @param multiplayers A list of the other players. An empty list is
	 * allowed.
	 */
	public void addMultiPlayers(Collection<SimplePlayer> multiplayers) {

		if (multiplayers == null) {
			throw new IllegalArgumentException("The multiplayers can not be null.");
		}

		if (this.multiplayers == null) {
			this.multiplayers = new ArrayList();
		}

		this.multiplayers.clear();
		this.multiplayers = (List<SimplePlayer>) multiplayers;
	}

	/**
	 * Gets a list with the currently connected players.
	 *
	 * @return The currently connected players.
	 */
	public Player getMainPlayer() {
		return this.mainPlayer;
	}

	/**
	 * Get all the players in the game.
	 *
	 * @return all the players in the game.
	 */
	public Collection<SimplePlayer> getMultiplayers() {
		return this.multiplayers;
	}

	public void setDialogMessage(String message) {
		this.messageDialog.text(message);
		this.messageDialog.setVisible(true);
	}

	/**
	 * Shows the map on the screen.
	 */
	@Override
	public void show() {
		this.renderer = new OrthogonalTiledMapRenderer(this.map.getMap(), 1f);
		this.renderer.setView(this.game.camera);

		this.stage.setKeyboardFocus(null);
		
		game.backgroundmusic.play(0.2f);
	}

	/**
	 * Sets the map to the map with the given id.
	 *
	 * @param mapId The id of the map. When smaller than 1, throws an
	 * IllegalArgumentException.
	 * @param direction the direction in which the mainPlayer is entering.
	 * @throws IllegalArgumentException when the mapId is lower than 1 or when
	 * the direction is a null value
	 */
	public void setLevel(int mapId, Direction direction) throws IllegalArgumentException {
		if (mapId <= 0) {
			throw new IllegalArgumentException("The map id must be at least 1.");
		}

		if (direction == null) {
			throw new IllegalArgumentException("direction can not be null.");
		}

		Map oldMap = this.map;
		for (Map m : this.maps) {
			if (m.getId() == mapId) {
				this.map = m;
			}
		}

		if (oldMap == this.map) {
			return;
		}
		
		boolean founddoor = false;
		for (RectangleMapObject rmo : this.map.getDoors().getByType(RectangleMapObject.class)) {
			if (Integer.parseInt(rmo.getName()) == oldMap.getId()) {
				founddoor = true;
				switch (direction) {
					case UP:
						this.mainPlayer.setPosition(rmo.getRectangle().x, rmo.getRectangle().y + 64);
						break;
					case DOWN:
						this.mainPlayer.setPosition(rmo.getRectangle().x, rmo.getRectangle().y - 64);
						break;
					default:
					case LEFT:
						this.mainPlayer.setPosition(rmo.getRectangle().x - 64, rmo.getRectangle().y);
						break;
					case RIGHT:
						this.mainPlayer.setPosition(rmo.getRectangle().x + 64, rmo.getRectangle().y);
						break;
				}
				break;
			}
		}
		
		if(!founddoor) {
			this.mainPlayer.setPosition(this.map.getDoors().getByType(RectangleMapObject.class).first().getRectangle().x + 64, this.map.getDoors().getByType(RectangleMapObject.class).first().getRectangle().y);
		}

		this.renderer = new OrthogonalTiledMapRenderer(this.map.getMap(), 1f);
		this.renderer.setView(this.game.camera);
		this.mainPlayer.setRoomId(this.map.getId());
	}

	/**
	 * Adds a projectile to the screen.
	 *
	 * @param projectile The projectile to add.
	 * @param receivedFromServer True if the projectile was sent by the server,
	 * false if the projectile was created by this client
	 */
	public void addProjectile(Projectile projectile, boolean receivedFromServer) {

		if (projectile == null) {
			throw new IllegalArgumentException("Projectile can not be null.");
		}

		projectiles.add(projectile);

		// Check if this projectile has not been received from the server,
		// to prevent an infinite loop
		if (!receivedFromServer) {
			// Send projectile to other players
			client.sendProjectile(new SimpleProjectile(projectile));
		}
	}

	/**
	 * Removes a projectile from the screen.
	 *
	 * @param projectile The projectile to remove.
	 */
	public void removeProjectile(Projectile projectile) {

		if (projectile == null) {
			throw new IllegalArgumentException("Projectile can not be null.");
		}

		projectiles.remove(projectile);
	}

	/**
	 * Updates the player health labels to match the player's health.
	 *
	 * @param health The player's health.
	 */
	public void updatePlayerHealthLabels(int health) {
		this.shake.shake(0.5f);
		this.playerHealthLabel.setText(Integer.toString(health));
		this.playerHealthLabelHUD.setText(Integer.toString(health));
	}

	/**
	 * Is executed each time the screen should be redrawn. Contains all drawing
	 * logic for the game screen.
	 *
	 * @param delta The time relative to the last delta time.
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		if (this.mainPlayer != null) {
//            this.client.sendPlayer(new SimplePlayer(this.mainPlayer));
			this.game.camera.position.set(this.mainPlayer.getX(), this.mainPlayer.getY(), 0);
			this.game.camera.update();
			
			shake.update(delta, this.game.camera, new Vector2(this.mainPlayer.getX(), this.mainPlayer.getY()));

			this.renderer.setView(this.game.camera);
			this.renderer.render();

			// Set the playerLabel position to the position of the player
			Vector3 playerPos = new Vector3(this.mainPlayer.getX(), this.mainPlayer.getY(), 0);
			this.game.camera.project(playerPos);

			float playerLabelWidth = this.playerLabelName.getWidth();
			this.playerLabelName.setAlignment((int) playerLabelWidth / 2);
			this.playerLabelName.setPosition(playerPos.x + 32, playerPos.y + this.mainPlayer.getCurrentAnimation().getKeyFrame(0).getRegionHeight() + 12);

			float playerHealthLabelWidth = this.playerHealthLabel.getWidth();
			this.playerHealthLabel.setAlignment((int) (playerHealthLabelWidth / 2));
			this.playerHealthLabel.setPosition(playerPos.x + 16, playerPos.y + this.mainPlayer.getCurrentAnimation().getKeyFrame(0).getRegionHeight() - 18);

			this.playerHealthLabelHUD.setPosition(0, this.playerHealthLabelHUD.getHeight());
			this.playerHealthLabelHUD.setText(String.valueOf(mainPlayer.getHitPoints()));

			this.playerLabelNameHUD.setAlignment((int) this.playerLabelNameHUD.getWidth() / 2);
			this.playerLabelNameHUD.setPosition(Gdx.graphics.getWidth() / 2, this.playerLabelNameHUD.getHeight());

			this.scoreLabel.setText(String.valueOf(mainPlayer.getScore()));
			this.scoreLabel.setPosition(Gdx.graphics.getWidth() - (this.scoreLabel.getWidth() * 2), this.scoreLabel.getHeight());

			//Render other players
			if (this.multiplayers == null) {
				this.multiplayers = new ArrayList();
			}
			if (this.multiplayer == null) {
				this.multiplayer = new Player(this);
			}
			List<SimplePlayer> localMultiplayers = new ArrayList(this.multiplayers);
			batch.begin();
			batch.setProjectionMatrix(game.camera.combined);
			try {
				for (SimplePlayer splayer : localMultiplayers) {
					if (splayer.getRoomId() == mainPlayer.getRoomId()) {
						multiplayer.setData(splayer);
						multiplayer.render(batch);
						this.fontwhite.draw(batch, splayer.getName(), splayer.getX(), splayer.getY() + 76);
						this.fontred.draw(batch, Integer.toString(splayer.getHitPoints()), splayer.getX() + 12, splayer.getY() + 64);
					}
				}
			} catch (ConcurrentModificationException cme) {
				Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, cme);
			}
			this.mainPlayer.render(this.batch);
			this.inputManager.checkInput();
			this.batch.end();

			//Remove old chat labels
			for (Label l : this.oldchatlabels) {
				l.remove();
			}
			this.oldchatlabels.clear();

			//Draw new labels
			int counter = 0;
			int spacing = 15;
			for (Label label : this.chatManager.getMessages()) {
				label.setPosition(10, Gdx.graphics.getHeight() - label.getHeight() - (counter * spacing));
				this.oldchatlabels.add(label);
				this.stage.addActor(label);

				counter++;
			}

			this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			this.stage.draw();

			// Render projectiles
			ArrayList projectilesToBeRemoved = new ArrayList();

			// Loop through all projectiles to render projectiles and check
			// for removed projectiles
			for (int i = 0; i < GameScreen.projectiles.size(); i++) {
				try {
					Projectile p = GameScreen.projectiles.get(i);

					if (p != null) {
						if (!p.shouldRemove()) {
							// Update and render projectile only if the room IDs match
							// This should always be the case when projectiles are send
							// through multiplayer
							if (p.getRoomID() == this.mainPlayer.getRoomId()) {
								p.update();
								p.render(this.shape, this.game.camera);
							}
						} else {
							projectilesToBeRemoved.add(p);
						}
					}
				} catch (Exception ex) {
					Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			// Remove all expired projectiles
			projectiles.removeAll(projectilesToBeRemoved);

			//Update the player
			this.mainPlayer.update();
		}
				
		// Render scoreboard overlay
		if (this.renderScoreboard) {
			// Tweak these variables
			int padding = 100;
			int labelOffsetX = 20;
			int labelOffsetY = 50;
			int labelHeight = 40;
			int headerHeight = 50;
			Color labelColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
			Color backGroundColor = new Color(0.3f, 0.3f, 0.3f, 0.8f);
			
			// Create a new stage for rendering labels,
			// and a new shaperenderer for the background
			Stage labelStage = new Stage();
			ShapeRenderer backgroundShapeRenderer = new ShapeRenderer();
			
			// render background
			Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			backgroundShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			backgroundShapeRenderer.setColor(backGroundColor);
			backgroundShapeRenderer.rect(padding, padding, Gdx.graphics.getWidth() - (padding*2), Gdx.graphics.getHeight() - (padding*2));
			backgroundShapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
			
			// Render header
			Label headerLabel = new Label("HighScores", this.skin);
			headerLabel.setFontScale(2);
			headerLabel.setColor(labelColor);
			headerLabel.setPosition(padding + labelOffsetX, Gdx.graphics.getHeight() - padding - labelOffsetY);
			labelStage.addActor(headerLabel);
			
			// Render mainplayer score
			Label mainPlayerScoreLabel = new Label(this.mainPlayer.getName() + " - " + this.mainPlayer.getScore(), this.skin);
			mainPlayerScoreLabel.setColor(labelColor);
			mainPlayerScoreLabel.setPosition(padding + labelOffsetX, Gdx.graphics.getHeight() - padding - labelOffsetY - headerHeight);
			labelStage.addActor(mainPlayerScoreLabel);
			
			// Get multiplayers
			for (int i = 0; i < this.multiplayers.size(); i++) {
				SimplePlayer sp = this.multiplayers.get(i);
				
				Label multiPlayerScoreLabel = new Label(sp.getName() + " - " + sp.getScore(), this.skin);
				multiPlayerScoreLabel.setColor(labelColor);
				multiPlayerScoreLabel.setPosition(padding + labelOffsetX, Gdx.graphics.getHeight() - padding - labelOffsetY - headerHeight - ((i+1)*labelHeight));
				labelStage.addActor(multiPlayerScoreLabel);
			}
			
			labelStage.draw();
			labelStage.clear();
			labelStage.dispose();
		}
	}

	/**
	 * Is executed each time the chatmanager is updated.
	 *
	 * @param o the observable which this observer is watching that was updated.
	 * @param arg the possibly given argument.
	 */
	@Override
	public void update(Observable o, Object arg) {
		String chatMessage = this.textfield.getText();

		this.sendChatMessage(this.mainPlayer.getName(), chatMessage);
	}

	/**
	 * Sends a chat message to every connected player.
	 *
	 * @param sender The sender of the message.
	 * @param message The message to be send.
	 */
	public void sendChatMessage(String sender, String message) {
		try {
			if (!sender.isEmpty() && !message.isEmpty()) {
				this.stage.setKeyboardFocus(this.scoreLabel);
				inputManager.isChatting = false;
				this.chatManager.sendMessage(sender, message);
				this.textfield.setText("");
			} else {
				this.stage.setKeyboardFocus(this.textfield);
				inputManager.isChatting = true;
			}
		} catch (Exception ex) {
			Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
		}
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
		game.backgroundmusic.stop();
	}

	@Override
	public void dispose() {
		client.stop();
	}
}

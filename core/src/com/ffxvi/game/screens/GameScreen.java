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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.client.Client;
import com.ffxvi.game.entities.Direction;
import com.ffxvi.game.entities.Player;
import com.ffxvi.game.entities.PlayerCharacter;
import com.ffxvi.game.entities.Projectile;
import com.ffxvi.game.entities.SimplePlayer;
import com.ffxvi.game.logics.ChatManager;
import com.ffxvi.game.logics.InputManager;
import com.ffxvi.game.models.Map;
import com.ffxvi.game.models.MapType;
import com.ffxvi.game.support.Vector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * The screen for the game.
 *
 */
public class GameScreen implements Screen, Observer {

    /**
     * Holds the current instance of GameScreen.
     */
    private static final GameScreen GAMESCREEN = new GameScreen();

    /**
     * The main class for the game.
     */
    private final MainClass game;

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
     * The code for this client.
     */
    private final Client client;

    //Map related
    /**
     * The tile map renderer.
     */
    private OrthogonalTiledMapRenderer renderer;

    /**
     * The map of this screen.
     */
    private Map map;

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
    private final ChatManager chatManager;

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
    private final Label playerLabel1;

    /**
     * Label for the player name on the HUD.
     */
    private final Label playerLabel2;

    /**
     * Label for the health counter on top of the player.
     */
    private final Label healthLabel1;

    /**
     * Label for the health counter on the HUD.
     */
    private final Label healthLabel2;

    /**
     * Label for the score on the HUD.
     */
    private final Label scoreLabel;

    /**
     * TextField for chat messages.
     */
    private final TextField textfield;

    /**
     * A controller class which handles all the keyboard/mouse/controller input
     * for the game.
     */
    private InputManager inputManager;

    /**
     * Initializes a new GameScreen.
     */
    private GameScreen() {
        this.game = MainClass.getInstance();
        this.stage = new Stage();
        this.chatManager = new ChatManager();

        this.client = new Client("192.168.1.1", 1338, 1337);

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

        this.textfield = new TextField("", skin);
        this.textfield.setPosition(10, Gdx.graphics.getHeight() - 200);
        this.textfield.setWidth(300);
        this.stage.addActor(this.textfield);

        //Setup labels
        ////Setup label variables
        this.playerLabel1 = new Label("", this.skin);
        this.playerLabel2 = new Label("", this.skin);
        this.healthLabel1 = new Label("100", this.skin);
        this.healthLabel2 = new Label("100", this.skin);
        this.scoreLabel = new Label("5000", this.skin);
        ////Setup label fontscales
        this.healthLabel2.setFontScale(2);
        this.playerLabel2.setFontScale(2);
        this.scoreLabel.setFontScale(2);
        ////Setup label colors
        this.healthLabel1.setColor(Color.RED);
        this.healthLabel2.setColor(Color.RED);
        this.scoreLabel.setColor(Color.YELLOW);
        ////Setup label height
        this.playerLabel2.setHeight(20);
        this.scoreLabel.setHeight(20);
        this.healthLabel2.setHeight(20);
        ////Add labels to stage
        this.stage.addActor(this.healthLabel1);
        this.stage.addActor(this.healthLabel2);
        this.stage.addActor(this.playerLabel1);
        this.stage.addActor(this.playerLabel2);
        this.stage.addActor(this.scoreLabel);

        this.oldchatlabels = new ArrayList();
    }

    /**
     * Gets the current map.
     *
     * @return The current map.
     */
    public Map getCurrentMap() {
        return this.map;
    }

    /**
     * Gets the current instance of this class.
     *
     * @return The current instance of this class.
     */
    public static GameScreen getInstance() {
        return GAMESCREEN;
    }

    /**
     * Loads all the maps for this game.
     */
    private void loadMaps() {
        this.mapTypes.add(new MapType(1, "level1"));
        this.mapTypes.add(new MapType(2, "level2"));
        this.mapTypes.add(new MapType(3, "level3"));

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

        int idx = new Random().nextInt(this.maps.size());
        GameScreen.getInstance().map = this.maps.get(idx);

        this.mainPlayer = new Player(character, playerName, new Vector(64f, 64f), this.map.getId());
        this.mainPlayer.setPosition(64, 64);
        this.client.sendPlayer(new SimplePlayer(this.mainPlayer));

        this.playerLabel1.setText(playerName);
        this.playerLabel2.setText(playerName);

        this.inputManager = new InputManager(this.mainPlayer);
        this.inputManager.addObserver(this);
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

        this.multiplayers = (List<SimplePlayer>) multiplayers;
    }

    /**
     * Shows the map on the screen.
     */
    @Override
    public void show() {
        this.renderer = new OrthogonalTiledMapRenderer(this.map.getMap(), 1f);
        this.renderer.setView(this.game.camera);

        this.stage.setKeyboardFocus(null);
    }

    /**
     * Sets the map to the map with the given id.
     *
     * @param mapId The id of the map. When smaller than 1, throws an
     * IllegalArgumentException.
     * @param direction the direction in which the mainPlayer is entering.
     */
    public void setLevel(int mapId, Direction direction) {
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

        for (RectangleMapObject rmo : this.map.getDoors().getByType(RectangleMapObject.class)) {
            if (Integer.parseInt(rmo.getName()) == oldMap.getId()) {
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

        this.renderer = new OrthogonalTiledMapRenderer(this.map.getMap(), 1f);
        this.renderer.setView(this.game.camera);
        this.mainPlayer.setRoomId(this.map.getId());
    }

    /**
     * Adds a projectile to the screen.
     *
     * @param projectile The projectile to add.
     */
    public static void addProjectile(Projectile projectile) {

        if (projectile == null) {
            throw new IllegalArgumentException("Projectile can not be null.");
        }

        projectiles.add(projectile);
    }

    /**
     * Removes a projectile from the screen.
     *
     * @param projectile The projectile to remove.
     */
    public static void removeProjectile(Projectile projectile) {

        if (projectile == null) {
            throw new IllegalArgumentException("Projectile can not be null.");
        }
        projectiles.remove(projectile);
    }

    /**
     * Is executed each time the screen should be redrawn. Contains all drawing
     * logic for the game screen.
     *
     * @param delta The time relative to the last delta time.
     */
    @Override
    public void render(float delta) {
        if (this.mainPlayer != null) {
            this.client.sendPlayer(new SimplePlayer(this.mainPlayer));
            this.game.camera.position.set(this.mainPlayer.getX(), this.mainPlayer.getY(), 0);
            this.game.camera.update();

            this.renderer.setView(this.game.camera);
            this.renderer.render();

            // Set the playerLabel position to the position of the player
            Vector3 playerPos = new Vector3(this.mainPlayer.getX(), this.mainPlayer.getY(), 0);
            this.game.camera.project(playerPos);

            float playerLabelWidth = this.playerLabel1.getWidth();
            this.playerLabel1.setAlignment((int) playerLabelWidth / 2);
            this.playerLabel1.setPosition(playerPos.x + 32, playerPos.y + this.mainPlayer.getCurrentAnimation().getKeyFrame(0).getRegionHeight() + 12);

            float healthLabel1width = this.healthLabel1.getWidth();
            this.healthLabel1.setAlignment((int) (healthLabel1width / 2));
            this.healthLabel1.setPosition(playerPos.x + 16, playerPos.y + this.mainPlayer.getCurrentAnimation().getKeyFrame(0).getRegionHeight() - 18);

            this.healthLabel2.setPosition(0, this.healthLabel2.getHeight());

            this.playerLabel2.setAlignment((int) this.playerLabel2.getWidth() / 2);
            this.playerLabel2.setPosition(Gdx.graphics.getWidth() / 2, this.playerLabel2.getHeight());

            this.scoreLabel.setPosition(Gdx.graphics.getWidth() - (this.scoreLabel.getWidth() * 2), this.scoreLabel.getHeight());

            //Render other players
            for (SimplePlayer splayer : this.multiplayers) {
                ShapeRenderer srenderer = new ShapeRenderer();
                srenderer.setProjectionMatrix(this.game.camera.combined);
                srenderer.setAutoShapeType(true);
                srenderer.begin();
                //srenderer.circle((splayer.getX()/mainPlayer.getX()) + Gdx.graphics.getWidth()/2, (splayer.getY()/mainPlayer.getY()) + Gdx.graphics.getHeight()/2, 10);
                //Vector3 v3 = camera.unproject(new Vector3(splayer.getX(), splayer.getY(), 0));
                //srenderer.circle(v3.x, v3.y, 10);
                srenderer.circle(splayer.getX(), splayer.getY(), 10);
                srenderer.end();
            }

            this.batch.begin();
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

            for (Projectile p : GameScreen.projectiles) {
                if (p != null) {
                    if (!p.shouldRemove()) {
                        p.update();
                        p.render(this.shape, this.game.camera);
                    } else {
                        p = null;
                    }
                }
            }

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

        if (!chatMessage.isEmpty()) {
            this.stage.setKeyboardFocus(this.scoreLabel);

            this.chatManager.addMessage(this.mainPlayer.getName(), chatMessage);
            this.textfield.setText("");
        } else {
            this.stage.setKeyboardFocus(this.textfield);
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
        
    }

    @Override
    public void dispose() {
        
    }
}

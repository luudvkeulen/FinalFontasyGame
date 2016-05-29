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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ffxvi.game.MainClass;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * The skin of this class.
     */
    private Skin skin;

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
    private List servers;

    /**
     * A scrollpane GUI control.
     */
    private ScrollPane scrollPane;

    /**
     * A table used for displaying the server list.
     */
    private Table table;

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
        Gdx.input.setInputProcessor(this.stage);

        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        this.skin.add("white", new Texture(pixmap));

        this.skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        BitmapFont bfont = new BitmapFont();
        //bfont.scale(1);
        this.skin.add("default", bfont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = this.skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = this.skin.newDrawable("white", Color.WHITE);
        textButtonStyle.over = this.skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = this.skin.getFont("default");

        this.skin.add("default", textButtonStyle);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton playButton = new TextButton("PLAY", textButtonStyle);
        playButton.setPosition((this.stage.getWidth() / 2) - (playButton.getWidth() / 2), (playButton.getHeight() / 2) - 10);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
                game.setScreen(new PreGameScreen());
            }
        });
        this.stage.addActor(playButton);

        /* Draw server overview */
 /*try {
			this.serverRetriever = new ServerRetriever();
		} catch (IOException ex) {
			Logger.getLogger(ServerBrowserScreen.class.getName()).log(Level.SEVERE, null, ex);
		}*/
        this.servers = new List(skin);
        servers.getSelection().setMultiple(true);
        servers.getSelection().setRequired(false);
        this.servers.setItems((Object) new String[]{});

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

        //this.servers.setItems(serverAddresses);
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
            this.game.setScreen(new MenuScreen());
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

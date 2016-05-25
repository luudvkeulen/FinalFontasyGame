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
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ffxvi.game.MainClass;

/**
 * The screen for the main menu.
 */
public class MenuScreen implements Screen {

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
     * The skin.
     */
    private final Skin skin;

    /**
     * The stage.
     */
    private final Stage stage;

    /**
     * The spritebatch in which the sprites are contained.
     */
    private final SpriteBatch batch;

    /**
     * The main class.
     */
    private final MainClass game;

    /**
     * The sprite.
     */
    private final Sprite sprite;

    /**
     * Initializes the menu screen.
     */
    public MenuScreen() {
        this.game = MainClass.getInstance();
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);

        this.skin = new Skin();

        Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        this.skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        BitmapFont bfont = new BitmapFont();
        //bfont.scale(1);
        this.skin.add("default", bfont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.WHITE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        this.skin.add("default", textButtonStyle);

        // Create the logo
        Texture texture = new Texture(Gdx.files.internal("Logo.png"));
        this.sprite = new Sprite(texture);
        this.sprite.scale(-0.3f);
        this.sprite.setPosition((stage.getWidth() / 2) - (texture.getWidth() / 2),
                this.stage.getHeight() - texture.getHeight() + 10);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton playButton = new TextButton("PLAY", textButtonStyle);
        playButton.setPosition((stage.getWidth() / 2) - (playButton.getWidth() / 2), (stage.getHeight() / 2) - (playButton.getHeight() / 2));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().dispose();
//				game.setScreen(new PreGameScreen(game));
                game.setScreen(new ServerBrowserScreen());
            }
        });
        stage.addActor(playButton);

        // Create 'Options' button
        final TextButton optionsButton = new TextButton("OPTIONS", textButtonStyle);
        optionsButton.setPosition((stage.getWidth() / 2) - (playButton.getWidth() / 2), playButton.getY() - optionsButton.getHeight() - BUTTON_OFFSET);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen());
            }
        });
        this.stage.addActor(optionsButton);

        // Create 'Quit' button
        final TextButton quitButton = new TextButton("QUIT", textButtonStyle);
        quitButton.setPosition((this.stage.getWidth() / 2) - (optionsButton.getWidth() / 2), optionsButton.getY() - quitButton.getHeight() - BUTTON_OFFSET);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        this.stage.addActor(quitButton);
    }

    @Override
    public void show() {

    }

    /**
     * Is executed when the menu screen is redrawn. All drawing logic for menu
     * screen is handled here.
     *
     * @param f
     */
    @Override
    public void render(float f) {
        // Draw background color
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Render background image
        this.batch.begin();
        this.sprite.draw(this.batch);
        this.batch.end();

        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.stage.draw();
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

    /**
     * Hides this screen.
     */
    @Override
    public void hide() {
        this.stage.clear();
    }

    @Override
    public void dispose() {

    }

}

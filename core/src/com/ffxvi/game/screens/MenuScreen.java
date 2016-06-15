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
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ffxvi.game.MainClass;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The screen for the main menu.
 */
public class MenuScreen implements Screen {

	/**
	 * The game version.
	 */
	private static final String version = "alpha 4.2.0";

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
	
	private final Sprite backgroundsprite;

	/**
	 * A label to draw the version
	 */
	private final Label versionLabel;

	/**
	 * Initializes the menu screen.
	 */
	public MenuScreen() {
		this.game = MainClass.getInstance();
		this.batch = new SpriteBatch();
		this.stage = new Stage();
		Gdx.input.setInputProcessor(this.stage);

		Skin buttonSkin = new Skin();

		Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		buttonSkin.add("white", new Texture(pixmap));

		this.skin = new Skin(Gdx.files.internal("uiskin.json"));

		//Background
		this.backgroundsprite = new Sprite(game.background);
		
		// Store the default libgdx font under the name "default".
		BitmapFont bfont = new BitmapFont();
		this.skin.add("default", bfont);
		buttonSkin.add("default", bfont);

		float rgbcolor = 0.05f;
		Color blacktransparent = new Color(Color.rgba8888(rgbcolor, rgbcolor, rgbcolor, 0.8f));
		Color blacktransparenthover = new Color(Color.rgba8888(rgbcolor, rgbcolor, rgbcolor, 0.95f));
		
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = buttonSkin.newDrawable("white", blacktransparent);
		textButtonStyle.down = buttonSkin.newDrawable("white", blacktransparent);
		textButtonStyle.over = buttonSkin.newDrawable("white", blacktransparenthover);

		textButtonStyle.font = buttonSkin.getFont("default");

		buttonSkin.add("default", textButtonStyle);

		// Create the logo
		Texture texture = new Texture(Gdx.files.internal("Logo2.png"));
		this.sprite = new Sprite(texture);
		this.sprite.scale(-0.3f);
		this.sprite.setPosition((stage.getWidth() / 2) - (texture.getWidth() / 2),
				this.stage.getHeight() - texture.getHeight() + 10);

		//Create audio for clicking sound
		final Sound click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
		
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		final TextButton playButton = new TextButton("PLAY", textButtonStyle);
		playButton.setWidth(300);
		playButton.setHeight(100);
		playButton.setPosition((stage.getWidth() / 2) - (playButton.getWidth() / 2), (stage.getHeight() / 2) - (playButton.getHeight() / 2));
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				click.play();
				game.getScreen().dispose();
				game.setScreen(new ServerBrowserScreen());
			}
		});
		stage.addActor(playButton);

		// Create 'Options' button
		final TextButton optionsButton = new TextButton("OPTIONS", textButtonStyle);
		optionsButton.setWidth(300);
		optionsButton.setHeight(100);
		optionsButton.setPosition((stage.getWidth() / 2) - (playButton.getWidth() / 2), playButton.getY() - optionsButton.getHeight() - BUTTON_OFFSET);
		optionsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				click.play();
				game.getScreen().dispose();
				game.setScreen(new OptionsScreen());
			}
		});
		this.stage.addActor(optionsButton);

		// Create 'Quit' button
		final TextButton quitButton = new TextButton("QUIT", textButtonStyle);
		quitButton.setWidth(300);
		quitButton.setHeight(100);
		quitButton.setPosition((this.stage.getWidth() / 2) - (optionsButton.getWidth() / 2), optionsButton.getY() - quitButton.getHeight() - BUTTON_OFFSET);
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				click.play();
				try {
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					Logger.getLogger(MenuScreen.class.getName()).log(Level.SEVERE, null, ex);
				}
				Gdx.app.exit();
			}
		});
		this.stage.addActor(quitButton);

		// Render version label
		this.versionLabel = new Label(this.version, this.skin);
		this.versionLabel.setPosition(Gdx.graphics.getWidth() - this.versionLabel.getWidth(), 10);
		this.versionLabel.setFontScale(0.8f);

		this.stage.addActor(this.versionLabel);
	}

	/**
	 * Is executed when the menu screen is redrawn. All drawing logic for menu
	 * screen is handled here.
	 *
	 * @param delta the time between the last and current use of this method
	 */
	@Override
	public void render(float delta) {

		// Render background image
		this.batch.begin();
		this.backgroundsprite.draw(this.batch);
		this.sprite.draw(this.batch);
		this.batch.end();

		this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		this.stage.draw();
	}

	/**
	 * Hides this screen.
	 */
	@Override
	public void hide() {
		this.stage.clear();
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
	public void dispose() {
	}
}

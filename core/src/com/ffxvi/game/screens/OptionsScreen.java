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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ffxvi.game.MainClass;
import java.util.Arrays;
import java.util.LinkedHashMap;

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
	 * Spritebatch for background
	 */
	private final SpriteBatch batch;

	/**
	 * sprite for background
	 */
	private final Sprite backgroundsprite;

	/**
	 * A label for rendering the header text.
	 */
	private Label headerLabel;
	
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
	 * Initializes a new OptionsScreen.
	 */
	public OptionsScreen() {
		this.game = MainClass.getInstance();
		this.stage = new Stage();
		this.layout = new GlyphLayout();
		this.batch = new SpriteBatch();
		this.backgroundsprite = new Sprite(game.background);
		reloadScreen();
	}

	public final void reloadScreen() {
		Gdx.input.setInputProcessor(this.stage);

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		Skin buttonSkin = new Skin();

		Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		buttonSkin.add("white", new Texture(pixmap));

		BitmapFont bfont = new BitmapFont();
		skin.add("default", bfont);
		buttonSkin.add("default", bfont);

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = buttonSkin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = buttonSkin.newDrawable("white", Color.WHITE);
		textButtonStyle.over = buttonSkin.newDrawable("white", Color.LIGHT_GRAY);

		textButtonStyle.font = buttonSkin.getFont("default");

		buttonSkin.add("default", textButtonStyle);

		// Create table
		Table table = new Table(skin);
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.stage.addActor(table);

		// Create header text
		this.headerLabel = new Label("OPTIONS", skin);
		this.headerLabel.setFontScale(2);
		this.headerLabel.setPosition((Gdx.graphics.getWidth() / 2) - (this.layout.width / 2) - this.headerLabel.getWidth(), Gdx.graphics.getHeight() - 50);
		this.stage.addActor(this.headerLabel);

		// Create "change resolution" label
		final Label lbResolution = new Label("Change resolution", skin);

		// Create a resolution hashmap, containing the supported resolutions
		final LinkedHashMap<String, Vector2> hmResolutions = new LinkedHashMap<String, Vector2>();
		hmResolutions.put("1152 x 768 (3:2)", new Vector2(1152, 768));
		hmResolutions.put("1280 x 854 (3:2)", new Vector2(1280, 854));
		hmResolutions.put("1440 x 960 (3:2)", new Vector2(1440, 960));
		hmResolutions.put("2880 x 1920 (3:2)", new Vector2(2880, 1920));
		hmResolutions.put("1024 x 768 (4:3)", new Vector2(1024, 768));
		hmResolutions.put("1152 x 864 (4:3)", new Vector2(1152, 864));
		hmResolutions.put("1280 x 960 (4:3)", new Vector2(1280, 960));
		hmResolutions.put("1400 x 1050 (4:3)", new Vector2(1400, 1050));
		hmResolutions.put("1600 x 1200 (4:3)", new Vector2(1600, 1200));
		hmResolutions.put("1280 x 768 (5:3)", new Vector2(1280, 768));
		hmResolutions.put("1280 x 1024 (5:4)", new Vector2(1280, 1024));
		hmResolutions.put("1280 x 720 (16:9)", new Vector2(1280, 720));
		hmResolutions.put("1366 x 768 (16:9)", new Vector2(1366, 768));
		hmResolutions.put("1600 x 900 (16:9)", new Vector2(1600, 900));
		hmResolutions.put("1920 x 1080 (16:9)", new Vector2(1920, 1080));
		hmResolutions.put("1280 x 800 (16:10)", new Vector2(1280, 800));
		hmResolutions.put("1440 x 900 (16:10)", new Vector2(1440, 900));
		hmResolutions.put("1680 x 1050 (16:10)", new Vector2(1680, 1050));
		hmResolutions.put("1920 x 1200 (16:10)", new Vector2(1920, 1200));
		hmResolutions.put("2560 x 1600 (16:10)", new Vector2(2560, 1600));
		hmResolutions.put("2048 x 1080 (17:9)", new Vector2(2048, 1080));

		// Create a selectbox to select a resolution
		final SelectBox<Object> sbResolutions = new SelectBox<Object>(skin);
		sbResolutions.setItems(hmResolutions.keySet().toArray());
		sbResolutions.setSize(200, 30);

		// Create a checkbox to toggle fullscreen
		final CheckBox cbFullscreen = new CheckBox("Toggle fullscreen", skin);
		cbFullscreen.setPosition((this.stage.getWidth() / 2) - (cbFullscreen.getWidth() / 2), (this.stage.getHeight() / 2) - 100);
		cbFullscreen.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sbResolutions.setDisabled(cbFullscreen.isChecked());
			}
		});

		// Create a button to reset to default
		final TextButton btnDefault = new TextButton("Reset to default", buttonSkin);
		btnDefault.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		btnDefault.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Reset to fullscreen
				cbFullscreen.setChecked(true);
			}
		});

		// Create a button to apply the changes
		final TextButton btnApplyChanges = new TextButton("Apply changes", buttonSkin);
		btnApplyChanges.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		btnApplyChanges.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Apply resolution
				// Get index
				int index = sbResolutions.getSelectedIndex();

				// Get width and height
				int newWidth = (int) ((Vector2) hmResolutions.values().toArray()[index]).x;
				int newHeight = (int) ((Vector2) hmResolutions.values().toArray()[index]).y;

				// Set window size
				if (cbFullscreen.isChecked()) {
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				} else {
					Gdx.graphics.setWindowedMode(newWidth, newHeight);
				}

				reloadScreen();
			}
		});

		// Create a button to discard the changes
		final TextButton btnDiscardChanges = new TextButton("Discard changes", buttonSkin);
		btnDiscardChanges.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		btnDiscardChanges.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreen().dispose();
				game.setScreen(new MenuScreen());
			}
		});

		// Align the elements in a table
		table.add(lbResolution).colspan(3).pad(10).padBottom(0).row();
		table.add(sbResolutions).colspan(3).pad(10).padTop(0).row();
		table.add(cbFullscreen).colspan(3).pad(10).row();
		table.add(btnDefault).colspan(3).pad(10).row();
		table.add(btnApplyChanges).colspan(1).pad(10);
		table.add(btnDiscardChanges).colspan(1).pad(10).right();

		// Set the right settings on the actors
		cbFullscreen.setChecked(Gdx.graphics.isFullscreen());

		Vector2 windowSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Vector2 foundVector = null;

		for (Vector2 vector : hmResolutions.values()) {
			if (vector.x == windowSize.x
					&& vector.y == windowSize.y) {
				foundVector = vector;
			}
		}

		if (foundVector != null) {
			int foundIndex = Arrays.asList(hmResolutions.values().toArray(new Vector2[0])).indexOf(foundVector);
			sbResolutions.setSelectedIndex(foundIndex);
		}
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

		this.batch.begin();
		backgroundsprite.draw(batch);
		this.batch.end();

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

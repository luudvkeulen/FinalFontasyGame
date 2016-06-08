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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.entities.PlayerCharacter;

/**
 * The screen which is shown before the game.
 */
public class PreGameScreen implements Screen {
	
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
	 * The game controller.
	 */
	private final MainClass game;

	/**
	 * The stage for the screen.
	 */
	private final Stage stage;

	/**
	 * A textfield for the username.
	 */
	private final TextField txtUsername;

	/**
	 * A label for the username.
	 */
	private final Label usernameLabel;
	
	/**
	 * A label for rendering the header text.
	 */
	private final Label headerLabel;

	/**
	 * The layout.
	 */
	private final GlyphLayout layout;

	/**
	 * Initializes a new PreGamScreen.
	 */
	public PreGameScreen() {
		this.game = MainClass.getInstance();
		this.stage = new Stage();
		this.layout = new GlyphLayout();
		Gdx.input.setInputProcessor(this.stage);

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		Skin buttonSkin = new Skin();

        Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        buttonSkin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		BitmapFont bfont = new BitmapFont();
		//bfont.scale(1);
		skin.add("default", bfont);
		buttonSkin.add("default", bfont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = buttonSkin.newDrawable("white", Color.WHITE);
        textButtonStyle.over = buttonSkin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = buttonSkin.getFont("default");

        buttonSkin.add("default", textButtonStyle);
		
		// Create header text
		this.headerLabel = new Label("CHARACTER SELECT", skin);
		this.headerLabel.setFontScale(2);
		this.headerLabel.setPosition((this.stage.getWidth() / 2) - (this.layout.width / 2) - this.headerLabel.getWidth(), this.stage.getHeight() - 50);

		this.stage.addActor(this.headerLabel);

		// Create textfield
		this.txtUsername = new TextField("Papyrus", skin);
		this.txtUsername.setSize(200, 40);
		this.txtUsername.setPosition((this.stage.getWidth() / 2) - (this.txtUsername.getWidth() / 2), (this.stage.getHeight() / 2) + 100);

		// Add the textfield to the stage
		this.stage.addActor(this.txtUsername);
		
		// Create username text
		this.usernameLabel = new Label("Voer een naam in:", skin);
		this.layout.setText(skin.getFont("default"), this.usernameLabel.getText());
		this.usernameLabel.setPosition((this.stage.getWidth() / 2) - (this.layout.width / 2), (this.stage.getHeight() / 2) + 100 + this.txtUsername.getHeight());

		// Add the label to the stage
		this.stage.addActor(this.usernameLabel);
		
		// Create imageButtons
		TextButton enterAsSkeletonDaggerButton = new TextButton("Skeleton Dagger", textButtonStyle);
		enterAsSkeletonDaggerButton.setSize(200, 200);
		enterAsSkeletonDaggerButton.setPosition((this.stage.getWidth() / 2) - (enterAsSkeletonDaggerButton.getWidth() * 2) - (BUTTON_OFFSET * 1.5f), (this.stage.getHeight() / 2) - enterAsSkeletonDaggerButton.getHeight());
		enterAsSkeletonDaggerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterGame(PlayerCharacter.SKELETON_DAGGER);
			}
		});
		this.stage.addActor(enterAsSkeletonDaggerButton);
		
		TextButton enterAsSkeletonHoodedBowButton = new TextButton("Skeleton Hooded Bow", textButtonStyle);
		enterAsSkeletonHoodedBowButton.setSize(200, 200);
		enterAsSkeletonHoodedBowButton.setPosition((this.stage.getWidth() / 2) - enterAsSkeletonHoodedBowButton.getWidth() - (BUTTON_OFFSET/2), (this.stage.getHeight() / 2) - enterAsSkeletonDaggerButton.getHeight());
		enterAsSkeletonHoodedBowButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterGame(PlayerCharacter.SKELETON_HOODED_BOW);
			}
		});
		enterAsSkeletonHoodedBowButton.setTouchable(Touchable.disabled);
		enterAsSkeletonHoodedBowButton.setColor(Color.GRAY);
		this.stage.addActor(enterAsSkeletonHoodedBowButton);
		
		TextButton enterAsSkeletonHoodedBowButton2 = new TextButton("Skeleton Hooded Bow 2", textButtonStyle);
		enterAsSkeletonHoodedBowButton2.setSize(200, 200);
		enterAsSkeletonHoodedBowButton2.setPosition((this.stage.getWidth() / 2) + (BUTTON_OFFSET/2), (this.stage.getHeight() / 2) - enterAsSkeletonDaggerButton.getHeight());
		enterAsSkeletonHoodedBowButton2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterGame(PlayerCharacter.SKELETON_HOODED_BOW);
			}
		});
		enterAsSkeletonHoodedBowButton2.setTouchable(Touchable.disabled);
		enterAsSkeletonHoodedBowButton2.setColor(Color.GRAY);
		this.stage.addActor(enterAsSkeletonHoodedBowButton2);
		
		TextButton enterAsSkeletonHoodedDaggerButton = new TextButton("Skeleton Dagger", textButtonStyle);
		enterAsSkeletonHoodedDaggerButton.setSize(200, 200);
		enterAsSkeletonHoodedDaggerButton.setPosition((this.stage.getWidth() / 2) + enterAsSkeletonHoodedDaggerButton.getWidth() + (BUTTON_OFFSET*1.5f), (this.stage.getHeight() / 2) - enterAsSkeletonDaggerButton.getHeight());
		enterAsSkeletonHoodedDaggerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterGame(PlayerCharacter.SKELETON_HOODED_DAGGER);
			}
		});
		this.stage.addActor(enterAsSkeletonHoodedDaggerButton);
	}

	/**
	 * Makes the user enter the game.
	 *
	 * @param character The player character which was chosen by the user.
	 */
	public void enterGame(PlayerCharacter character) {
		if (character == null) {
			throw new IllegalArgumentException("Character can not be null.");
		}

		this.game.getScreen().dispose();
		GameScreen gameScreen = new GameScreen();
		gameScreen.addPlayer(this.txtUsername.getText(), character);
		this.game.setScreen(gameScreen);
	}

	/**
	 * Is executed each time the screen is drawn. Contains all drawing logic for
	 * this screen.
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

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

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

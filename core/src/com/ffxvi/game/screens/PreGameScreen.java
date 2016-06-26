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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.ffxvi.game.models.PlayerCharacter;

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
	 * background related
	 */
	private final Sprite backgroundsprite;
	private final SpriteBatch backSpriteBatch;

	/**
	 * Initializes a new PreGamScreen.
	 */
	public PreGameScreen() {
		this.game = MainClass.getInstance();
		this.stage = new Stage();
		this.layout = new GlyphLayout();
		Gdx.input.setInputProcessor(this.stage);

		this.backgroundsprite = new Sprite(game.background);
		this.backSpriteBatch = new SpriteBatch();
		
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

		float rgbcolor = 0.05f;
		Color blacktransparent = new Color(Color.rgba8888(rgbcolor, rgbcolor, rgbcolor, 0.8f));
		Color blacktransparenthover = new Color(Color.rgba8888(rgbcolor, rgbcolor, rgbcolor, 0.95f));
		Color reddisabled = new Color(Color.rgba8888(0.2f, rgbcolor, rgbcolor, 0.6f));
		
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = buttonSkin.newDrawable("white", blacktransparent);
		textButtonStyle.down = buttonSkin.newDrawable("white", blacktransparent);
		textButtonStyle.over = buttonSkin.newDrawable("white", blacktransparenthover);
		textButtonStyle.disabled = buttonSkin.newDrawable("white", reddisabled);

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
		this.txtUsername.setColor(blacktransparent);

		// Add the textfield to the stage
		this.stage.addActor(this.txtUsername);

		// Create username text
		this.usernameLabel = new Label("Voer een naam in:", skin);
		this.layout.setText(skin.getFont("default"), this.usernameLabel.getText());
		this.usernameLabel.setPosition((this.stage.getWidth() / 2) - (this.layout.width / 2), (this.stage.getHeight() / 2) + 100 + this.txtUsername.getHeight());

		// Add the label to the stage
		this.stage.addActor(this.usernameLabel);

		final Sound click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
		
		// Create imageButtons
		TextButton enterAsSkeletonNormalButton = new TextButton("Skeleton Normal", textButtonStyle);
		enterAsSkeletonNormalButton.setSize(200, 200);
		enterAsSkeletonNormalButton.setPosition((this.stage.getWidth() / 2) - (enterAsSkeletonNormalButton.getWidth() * 2) - (BUTTON_OFFSET * 1.5f), (this.stage.getHeight() / 2) - enterAsSkeletonNormalButton.getHeight());
		enterAsSkeletonNormalButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				click.play();
				enterGame(PlayerCharacter.SKELETON_NORMAL);
			}
		});
		this.stage.addActor(enterAsSkeletonNormalButton);

		TextButton enterAsSkeletonHoodedButton = new TextButton("Skeleton Hooded", textButtonStyle);
		enterAsSkeletonHoodedButton.setSize(200, 200);
		enterAsSkeletonHoodedButton.setPosition((this.stage.getWidth() / 2) - enterAsSkeletonHoodedButton.getWidth() - (BUTTON_OFFSET / 2), (this.stage.getHeight() / 2) - enterAsSkeletonNormalButton.getHeight());
		enterAsSkeletonHoodedButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				click.play();
				enterGame(PlayerCharacter.SKELETON_HOODED);
			}
		});
		this.stage.addActor(enterAsSkeletonHoodedButton);

		TextButton enterAsHumanSoldierButton = new TextButton("Human Soldier", textButtonStyle);
		enterAsHumanSoldierButton.setSize(200, 200);
		enterAsHumanSoldierButton.setPosition((this.stage.getWidth() / 2) + (BUTTON_OFFSET / 2), (this.stage.getHeight() / 2) - enterAsSkeletonNormalButton.getHeight());
		enterAsHumanSoldierButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				click.play();
				enterGame(PlayerCharacter.HUMAN_SOLDIER);
			}
		});
		this.stage.addActor(enterAsHumanSoldierButton);

		TextButton enterAsHumanPirateButton = new TextButton("Human Pirate", textButtonStyle);
		enterAsHumanPirateButton.setSize(200, 200);
		enterAsHumanPirateButton.setPosition((this.stage.getWidth() / 2) + enterAsHumanPirateButton.getWidth() + (BUTTON_OFFSET * 1.5f), (this.stage.getHeight() / 2) - enterAsSkeletonNormalButton.getHeight());
		enterAsHumanPirateButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				click.play();
				enterGame(PlayerCharacter.HUMAN_PIRATE);
			}
		});
		this.stage.addActor(enterAsHumanPirateButton);
		
		TextButton enterAsSpectatorButton = new TextButton("Spectate", textButtonStyle);
		enterAsSpectatorButton.setSize(200, 60);
		enterAsSpectatorButton.setPosition((this.stage.getWidth() / 2) - (enterAsSpectatorButton.getWidth() / 2), (this.stage.getHeight() / 2) - enterAsSkeletonNormalButton.getHeight() - enterAsSpectatorButton.getHeight() - BUTTON_OFFSET);
		enterAsSpectatorButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterAsSpectator();
			}
		});
		this.stage.addActor(enterAsSpectatorButton);
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
		
		if(this.txtUsername.getText().equals("")) return;

		this.game.getScreen().dispose();
		GameScreen gameScreen = new GameScreen(false);
		gameScreen.addPlayer(this.txtUsername.getText(), character);
		this.game.setScreen(gameScreen);
	}
	
	/**
	 * Makes the user enter the game as a spectator.
	 */
	public void enterAsSpectator() {
		this.game.getScreen().dispose();
		GameScreen gameScreen = new GameScreen(true);
		gameScreen.addPlayer(this.txtUsername.getText(), PlayerCharacter.SKELETON_NORMAL);
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

		backSpriteBatch.begin();
		this.backgroundsprite.draw(backSpriteBatch);
		backSpriteBatch.end();

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

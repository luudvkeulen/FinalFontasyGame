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

public class MenuScreen implements Screen {
	private Skin skin;
	private Stage stage;
	private SpriteBatch batch;
	
	private final MainClass game;
	
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 60;
	private static final int BUTTON_OFFSET = 30;
	
	private Sprite sprite;
	
	public MenuScreen (final MainClass game) {
		this.game = game;
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		BitmapFont bfont=new BitmapFont();
		//bfont.scale(1);
		skin.add("default",bfont);

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.WHITE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

		textButtonStyle.font = skin.getFont("default");

		skin.add("default", textButtonStyle);
		
		// Create the logo
		Texture texture = new Texture(Gdx.files.internal("Logo.png"));
		sprite = new Sprite(texture);
		sprite.scale(-0.3f);
		sprite.setPosition((stage.getWidth()/2)-(texture.getWidth()/2),
						    stage.getHeight()-texture.getHeight() + 10);

		

		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		final TextButton playButton = new TextButton("PLAY",textButtonStyle);
		playButton.setPosition((stage.getWidth()/2) - (playButton.getWidth()/2), (stage.getHeight()/2) - (playButton.getHeight()/2));
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.getScreen().dispose();
//				game.setScreen(new PreGameScreen(game));
				game.setScreen(new ServerBrowser(game));
			}
		});
		stage.addActor(playButton);
		
		// Create 'Options' button
		final TextButton optionsButton = new TextButton("OPTIONS", textButtonStyle);
		optionsButton.setPosition((stage.getWidth()/2) - (playButton.getWidth()/2), playButton.getY() - optionsButton.getHeight() - BUTTON_OFFSET);
		optionsButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new OptionsScreen(game));
			}
		});
		stage.addActor(optionsButton);
		
		// Create 'Quit' button
		final TextButton quitButton = new TextButton("QUIT", textButtonStyle);
		quitButton.setPosition((stage.getWidth()/2) - (optionsButton.getWidth()/2), optionsButton.getY() - quitButton.getHeight() - BUTTON_OFFSET);
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		stage.addActor(quitButton);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float f) {
		// Draw background color
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		// Render background image
		batch.begin();
		sprite.draw(batch);
		batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		//Table.drawDebug(stage);
	}

	@Override
	public void resize(int w, int h) {
		//game.resize(i, i);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		stage.clear();
	}

	@Override
	public void dispose() {
		
	}

}

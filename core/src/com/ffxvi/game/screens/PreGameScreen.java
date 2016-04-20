package com.ffxvi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.PlayerCharacter;
import java.awt.Font;

public class PreGameScreen implements Screen {

	private final MainClass game;
	private final Stage stage;
	private TextField txtUsername;
	private Label label;
	private final GlyphLayout layout;
	
	public PreGameScreen (final MainClass game) {
		this.game = game;
		this.stage = new Stage();
		this.layout = new GlyphLayout();
		Gdx.input.setInputProcessor(stage);
		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Store the default libgdx font under the name "default".
		BitmapFont bfont=new BitmapFont();
		//bfont.scale(1);
		skin.add("default",bfont);
		
		// Create textfield
		txtUsername = new TextField("Pepe", skin);
		txtUsername.setSize(200, 40);
		txtUsername.setPosition((stage.getWidth()/2)-(txtUsername.getWidth()/2), (stage.getHeight()/2) + 25);
		
		// Add the textfield to the stage
		stage.addActor(txtUsername);
		
		// Create text
		label = new Label("Voer een naam in:", skin);
		layout.setText(skin.getFont("default"), label.getText());
		label.setPosition((stage.getWidth()/2)-(layout.width/2), (stage.getHeight()/2) + 25 + txtUsername.getHeight());
		// Add the label to the stage
		stage.addActor(label);
		
		// Create new button
		TextButton enterAsSkeletonDaggerButton = new TextButton("Skeleton Dagger", skin);
		enterAsSkeletonDaggerButton.setSize(200, 50);
		enterAsSkeletonDaggerButton.setPosition((stage.getWidth()/2)-(enterAsSkeletonDaggerButton.getWidth()/2), (stage.getHeight()/2) - 50);
		enterAsSkeletonDaggerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterGame(PlayerCharacter.SKELETON_DAGGER);
			}
		});
		
		// Add the button to the stage
		stage.addActor(enterAsSkeletonDaggerButton);
		
		// Create new button
		TextButton enterAsSkeletonHoodedBowButton = new TextButton("Skeleton Hooded Bow", skin);
		enterAsSkeletonHoodedBowButton.setSize(200, 50);
		enterAsSkeletonHoodedBowButton.setPosition((stage.getWidth()/2)-(enterAsSkeletonHoodedBowButton.getWidth()/2), (stage.getHeight()/2) - 50 - enterAsSkeletonHoodedBowButton.getHeight());
		enterAsSkeletonHoodedBowButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterGame(PlayerCharacter.SKELETON_HOODED_BOW);
			}
		});
		enterAsSkeletonHoodedBowButton.setTouchable(Touchable.disabled);
		enterAsSkeletonHoodedBowButton.setColor(Color.GRAY);
		
		// Add the button to the stage
		stage.addActor(enterAsSkeletonHoodedBowButton);
		
		// Create new button
		TextButton enterAsSkeletonHoodedDaggerButton = new TextButton("Skeleton Hooded Dagger", skin);
		enterAsSkeletonHoodedDaggerButton.setSize(200, 50);
		enterAsSkeletonHoodedDaggerButton.setPosition((stage.getWidth()/2)-(enterAsSkeletonHoodedDaggerButton.getWidth()/2), (stage.getHeight()/2) - 50 - (enterAsSkeletonHoodedDaggerButton.getHeight()*2));
		enterAsSkeletonHoodedDaggerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				enterGame(PlayerCharacter.SKELETON_HOODED_DAGGER);
			}
		});
		
		// Add the button to the stage
		stage.addActor(enterAsSkeletonHoodedDaggerButton);
	}
	
	public void enterGame(PlayerCharacter character) {
		game.getScreen().dispose();
		GameScreen gameScreen = new GameScreen(game);
		gameScreen.AddPlayer(txtUsername.getText(), character);
		game.setScreen(gameScreen);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		// Draw background color
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
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

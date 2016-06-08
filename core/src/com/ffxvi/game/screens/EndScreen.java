package com.ffxvi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.models.Ending;

public class EndScreen implements Screen{

	/**
	 * The label where the defeat or victory message will be shown
	 */
	private final Label message;
	
	/**
	 * Info how to get back to menu screen
	 */
	private final Label returnMessage;
	
	/**
	 * The Glyph Layouts to get text size
	 */
	private final GlyphLayout messageLayout;
	private final GlyphLayout returnMessageLayout;
	
	/**
	 * The skin containing the fonts etc
	 */
	private final Skin skin;
	
	/**
	 * The stage where the drawing will happen
	 */
	private final Stage stage;
	
	public EndScreen(Ending ending) {
		//Setup variables
		this.stage = new Stage();
		Gdx.input.setInputProcessor(this.stage);
		this.skin = new Skin(Gdx.files.internal("uiskin.json"));
		this.messageLayout = new GlyphLayout();
		this.returnMessageLayout = new GlyphLayout();
		
		//Add font
		BitmapFont bfont = new BitmapFont();
		this.skin.add("default", bfont);
		
		//Setup return message
		this.returnMessage = new Label("Press the escape key to return to the main menu...", this.skin);
		this.returnMessageLayout.setText(skin.getFont("default"), this.returnMessage.getText());
		
		//Setup message depending on the outcome
		switch(ending) {
			case VICTORY : 
				this.message = new Label("VICTORY!", this.skin);
				this.message.setColor(Color.GREEN);
				break;
			case DEFEAT : 
				this.message = new Label("DEFEAT", this.skin);
				this.message.setColor(Color.RED);
				break;
			default :
				this.message = new Label("Error", this.skin);
				break;
		}		
		
		//Setup message size and position
		int scale = 3;
		this.message.setFontScale(scale);
		this.messageLayout.setText(skin.getFont("default"), this.message.getText());
		this.message.setPosition((Gdx.graphics.getWidth() / 2) - (this.messageLayout.width * scale / 2), Gdx.graphics.getHeight() / 2);
		this.returnMessage.setPosition((Gdx.graphics.getWidth() / 2) - (this.returnMessageLayout.width / 2), Gdx.graphics.getHeight() / 2 - this.messageLayout.height * scale);
		
		stage.addActor(message);
		stage.addActor(returnMessage);
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float f) {
		this.stage.draw();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			MainClass.getInstance().setScreen(new MenuScreen());
		}
	}

	@Override
	public void resize(int i, int i1) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
}

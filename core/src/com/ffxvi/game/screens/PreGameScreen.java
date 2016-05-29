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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
    private final Label label;

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

        // Store the default libgdx font under the name "default".
        BitmapFont bfont = new BitmapFont();
        //bfont.scale(1);
        skin.add("default", bfont);

        // Create textfield
        this.txtUsername = new TextField("Papyrus", skin);
        this.txtUsername.setSize(200, 40);
        this.txtUsername.setPosition((this.stage.getWidth() / 2) - (this.txtUsername.getWidth() / 2), (this.stage.getHeight() / 2) + 25);

        // Add the textfield to the stage
        this.stage.addActor(this.txtUsername);

        // Create text
        this.label = new Label("Voer een naam in:", skin);
        this.layout.setText(skin.getFont("default"), this.label.getText());
        this.label.setPosition((this.stage.getWidth() / 2) - (this.layout.width / 2), (this.stage.getHeight() / 2) + 25 + this.txtUsername.getHeight());
        
        // Add the label to the stage
        this.stage.addActor(this.label);

        // Create new button
        TextButton enterAsSkeletonDaggerButton = new TextButton("Skeleton Dagger", skin);
        enterAsSkeletonDaggerButton.setSize(200, 50);
        enterAsSkeletonDaggerButton.setPosition((this.stage.getWidth() / 2) - (enterAsSkeletonDaggerButton.getWidth() / 2), (this.stage.getHeight() / 2) - 50);
        enterAsSkeletonDaggerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                enterGame(PlayerCharacter.SKELETON_DAGGER);
            }
        });

        // Add the button to the stage
        this.stage.addActor(enterAsSkeletonDaggerButton);

        // Create new button
        TextButton enterAsSkeletonHoodedBowButton = new TextButton("Skeleton Hooded Bow", skin);
        enterAsSkeletonHoodedBowButton.setSize(200, 50);
        enterAsSkeletonHoodedBowButton.setPosition((this.stage.getWidth() / 2) - (enterAsSkeletonHoodedBowButton.getWidth() / 2), (this.stage.getHeight() / 2) - 50 - enterAsSkeletonHoodedBowButton.getHeight());
        enterAsSkeletonHoodedBowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                enterGame(PlayerCharacter.SKELETON_HOODED_BOW);
            }
        });
        enterAsSkeletonHoodedBowButton.setTouchable(Touchable.disabled);
        enterAsSkeletonHoodedBowButton.setColor(Color.GRAY);

        // Add the button to the stage
        this.stage.addActor(enterAsSkeletonHoodedBowButton);

        // Create new button
        TextButton enterAsSkeletonHoodedDaggerButton = new TextButton("Skeleton Hooded Dagger", skin);
        enterAsSkeletonHoodedDaggerButton.setSize(200, 50);
        enterAsSkeletonHoodedDaggerButton.setPosition((this.stage.getWidth() / 2) - (enterAsSkeletonHoodedDaggerButton.getWidth() / 2), (this.stage.getHeight() / 2) - 50 - (enterAsSkeletonHoodedDaggerButton.getHeight() * 2));
        enterAsSkeletonHoodedDaggerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                enterGame(PlayerCharacter.SKELETON_HOODED_DAGGER);
            }
        });

        // Add the button to the stage
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
     * @param delta
     */
    @Override
    public void render(float delta) {
        // Draw background color
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

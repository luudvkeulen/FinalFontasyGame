/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.ffxvi.game.entities.PlayerAnimation.*;
import com.ffxvi.game.models.Direction;
import com.ffxvi.game.models.Player;
import com.ffxvi.game.models.PlayerCharacter;
import com.ffxvi.game.models.SimplePlayer;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.SkinManager;
import com.ffxvi.game.support.Utils;
import com.ffxvi.game.support.Vector;
import org.w3c.dom.css.Counter;

/**
 *
 * @author gebruiker-pc
 */
public class LibPlayer extends Player {
	
	/**
	 * The textures (skin) that this player is using
	 */
	private SkinManager.PlayerSkin playerSkin;

	/**
	 * The animation that this player is currently in
	 */
	private Animation currentAnimation;

	private GameScreen screen;
	
	private long lastSlash = 0;

	/**
	 * The speed at which the animation runs.
	 */
	private final float animationSpeed;

	public LibPlayer(PlayerCharacter character, String playerName, Vector position, GameScreen gameScreen, int roomId, boolean isSpectating) {
		super(character, playerName, position, gameScreen.getGameManager(), roomId, gameScreen, isSpectating);
		this.screen = gameScreen;
		this.animationSpeed = 0.05f;
		this.changeSkin();
		this.changeAnimation();
		this.isSpectating = isSpectating;
	}

	public LibPlayer(GameScreen screen) {
		super(screen.getGameManager(), screen);
		this.screen = screen;

		this.animationSpeed = 0.05f;
	}

	public void setData(SimplePlayer player) {
		this.setDataInner(player);

		this.changeSkin();
		this.changeAnimation();
	}

	/**
	 * Gets the current animation of this player.
	 *
	 * @return The animation of the player.
	 */
	public Animation getCurrentAnimation() {
		return this.currentAnimation;
	}

	public void changeAnimation() {
		if (super.animation != IDLE) {
			this.currentAnimation = this.playerSkin.getAnimation(super.animation, super.direction);
		} else {
			this.currentAnimation = new Animation(0, this.playerSkin.getAnimation(WALKING, super.direction).getKeyFrame(0));
		}
	}

	/**
	 * Switches the textures (skin) of this player.
	 */
	private void changeSkin() {
		switch (super.skin) {
			case SKELETON_NORMAL:
				this.playerSkin = GameScreen.getSkinManager().getSkeletonNormal();
				break;
			case SKELETON_HOODED:
				this.playerSkin = GameScreen.getSkinManager().getSkeletonHooded();
				break;
			case HUMAN_SOLDIER:
				this.playerSkin = GameScreen.getSkinManager().getHumanSoldier();
				break;
			case HUMAN_PIRATE:
				this.playerSkin = GameScreen.getSkinManager().getHumanPirate();
				break;
		}
	}

	int counter = 0;
	/**
	 * Method that is performed each tick and focuses on drawing.
	 *
	 * @param batch The Sprite Batch to use.
	 */
	public void render(SpriteBatch batch) {
		super.stateTime += Gdx.graphics.getDeltaTime();

		TextureRegion currentFrame = null;
		
		if(currentAnimation == this.playerSkin.getAnimation(SLASHING, super.direction)) {
			currentFrame = this.currentAnimation.getKeyFrame(counter);
			counter++;
		} else {
			currentFrame = this.currentAnimation.getKeyFrame(super.stateTime, true);
		}
		
		if(counter != 0) {
			currentFrame =  this.playerSkin.getAnimation(SLASHING, super.direction).getKeyFrame(counter);
			counter++;
		}
		
		if(counter >= 10) {
			counter = 0;
		}
		
		
		batch.draw(currentFrame, super.x, super.y, Utils.GRIDSIZE, Utils.GRIDSIZE);

		super.checkSlashing();
	}
	
	/**
	 * Makes the player die.
	 * @param killerName 
	 */
	@Override
	public void die(String killerName) {
		super.die(killerName);

		// Set animation to DEATH
		super.animation = PlayerAnimation.DYING;
		this.changeAnimation();
	}

	/**
	 * Sets the player's animation to idle.
	 */
	@Override
	public void setIdle() {
		if (!super.isDead) {
			super.animation = IDLE;
			this.changeAnimation();
		}
	}

	/**
	 * Slashes in the given direction, given the player can slash.
	 */
	@Override
	public void slash() {
		if (lastSlash == 0 || System.currentTimeMillis() - lastSlash >= 500) {
			super.animation = SLASHING;
			this.animation = SLASHING;
			this.changeAnimation();
			this.slash.play();
			lastSlash = System.currentTimeMillis();
		}
	}

	int counter2 = 0;
	/**
	 * Sets the direction to the given direction.
	 *
	 * @param direction The new direction.
	 */
	@Override
	public void setDirection(Direction direction) {
		if(!(currentAnimation == playerSkin.getAnimation(SLASHING, super.direction)) || counter2 == 0) {
			this.setDirectionInner(direction);
			super.animation = WALKING;
			this.changeAnimation();
		} else {
			counter2++;
		}
		
		if(counter2++ > 25) {
			counter2 = 0;
		}
	}

	/**
	 * Destroy this instance.
	 */
	public void destroy() {
		this.currentAnimation = null;
	}

	/**
	 * Toggles the boolean in GameScreen to render the scoreboard.
	 */
	public void toggleShowScoreboard() {
		this.screen.toggleShowScoreboard();
	}
}

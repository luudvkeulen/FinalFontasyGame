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
import static com.ffxvi.game.entities.PlayerAnimation.IDLE;
import static com.ffxvi.game.entities.PlayerAnimation.SLASHING;
import static com.ffxvi.game.entities.PlayerAnimation.WALKING;
import com.ffxvi.game.models.Direction;
import com.ffxvi.game.models.Player;
import com.ffxvi.game.models.PlayerCharacter;
import com.ffxvi.game.models.SimplePlayer;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.SkinManager;
import com.ffxvi.game.support.Utils;
import com.ffxvi.game.support.Vector;

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

	/**
	 * The speed at which the animation runs.
	 */
	private final float animationSpeed;

	public LibPlayer(PlayerCharacter character, String playerName, Vector position, GameScreen gameScreen, int roomId) {
		super(character, playerName, position, gameScreen.getGameManager(), roomId, gameScreen);
		this.screen = gameScreen;
		this.animationSpeed = 0.05f;
		this.changeSkin();
		this.changeAnimation();
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

	private void changeAnimation() {
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
			case SKELETON_DAGGER:
				this.playerSkin = GameScreen.getSkinManager().getNormalSkeleton();
				break;
			case SKELETON_HOODED_BOW:
				this.playerSkin = GameScreen.getSkinManager().getHoodedSkeleton();
				break;
			case SKELETON_HOODED_DAGGER:
				this.playerSkin = GameScreen.getSkinManager().getHoodedSkeleton();
				break;
		}
	}

	/**
	 * Method that is performed each tick and focuses on drawing.
	 *
	 * @param batch The Sprite Batch to use.
	 */
	public void render(SpriteBatch batch) {
		super.stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = this.currentAnimation.getKeyFrame(super.stateTime, true);
		batch.draw(currentFrame, super.x, super.y, Utils.GRIDSIZE, Utils.GRIDSIZE);
	}

	/**
	 * Sets the player's animation to idle.
	 */
	public void setIdle() {
		super.animation = IDLE;
		this.changeAnimation();
	}

	/**
	 * Slashes in the given direction, given the player can slash.
	 */
	public void slash() {
		super.animation = SLASHING;
		this.animation = SLASHING;
		this.changeAnimation();
	}

	/**
	 * Sets the direction to the given direction.
	 *
	 * @param direction The new direction.
	 */
	public void setDirection(Direction direction) {
		this.setDirectionInner(direction);
		super.animation = WALKING;
		this.changeAnimation();
	}

	/**
	 * Destroy this instance.
	 */
	public void destroy() {
		this.currentAnimation = null;
	}
}

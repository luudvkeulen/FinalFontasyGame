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
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gebruiker-pc
 */
public class LibPlayer extends Player {

	/**
	 * A boolean indicating whether the player is spectating.
	 */
	private boolean isSpectating;
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

	public LibPlayer(PlayerCharacter character, String playerName, Vector position, GameScreen gameScreen, int roomId, boolean isSpectating) {
		super(character, playerName, position, gameScreen.getGameManager(), roomId);
		this.screen = gameScreen;
		this.animationSpeed = 0.05f;
		this.changeSkin();
		this.changeAnimation();
		this.isSpectating = isSpectating;
	}

	public LibPlayer(GameScreen screen) {
		super(screen.getGameManager());
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
			case SKELETON_HOODED:
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

		super.checkSlashing();
	}

	/**
	 * Sets the player's animation to idle.
	 */
	@Override
	public void setIdle() {
		super.animation = IDLE;
		this.changeAnimation();
	}

	/**
	 * Slashes in the given direction, given the player can slash.
	 */
	@Override
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
		boolean shouldMove = false;

		if (!this.checkCollision(this.getCollisionBox(), GameScreen.getCurrentMap().getWallObjects(), GameScreen.getCurrentMap().getObjects())) {
			shouldMove = true;
		}

		this.setDirectionInner(direction, shouldMove);

		this.checkDoorCollision(this.getCollisionBox(), GameScreen.getCurrentMap().getDoors());
		/**
		 * Checks if the given rectangle collides with a door.
		 *
		 * @param rec The rectangle to check for collision.
		 * @param objects The objects (doors) of which should be looked within
		 * the rectangle.
		 */
		super.animation = WALKING;
		this.changeAnimation();
	}

	/**
	 * Checks the given rec for collision with the given (wall)objects.
	 *
	 * @param rec The rectangle to check.
	 * @param objects The objects to make sure are not in the rectangle.
	 * @param wallobjects The wall objects to make sure are not in the
	 * rectangle.
	 * @return true if collision, false if not.
	 */
	private boolean checkCollision(Rectangle rec, MapObjects objects, MapObjects wallobjects) {
		for (RectangleMapObject mapObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangleMapObject = mapObject.getRectangle();
			if (rec.overlaps(rectangleMapObject)) {
				return true;
			}
		}

		for (RectangleMapObject mapObject : wallobjects.getByType(RectangleMapObject.class)) {
			Rectangle rectangleMapObject = mapObject.getRectangle();
			if (rec.overlaps(rectangleMapObject)) {
				return true;
			}
		}

		return false;
	}

	private void checkDoorCollision(Rectangle rec, MapObjects objects) {
		for (RectangleMapObject mapObject : objects.getByType(RectangleMapObject.class)) {
			Rectangle rectangleMapObject = mapObject.getRectangle();
			if (rec.overlaps(rectangleMapObject)) {
				int mapId = Integer.parseInt(mapObject.getName().replaceAll("\\D", ""));

				try {
					this.screen.setLevel(mapId, this.direction);
				} catch (IllegalArgumentException ex) {
					Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
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

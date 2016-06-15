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
import com.badlogic.gdx.utils.Timer;
import static com.ffxvi.game.entities.PlayerAnimation.IDLE;
import static com.ffxvi.game.entities.PlayerAnimation.SLASHING;
import static com.ffxvi.game.entities.PlayerAnimation.WALKING;
import com.ffxvi.game.models.Direction;
import com.ffxvi.game.models.Player;
import com.ffxvi.game.models.PlayerCharacter;
import static com.ffxvi.game.models.PlayerCharacter.HUMAN_PIRATE;
import static com.ffxvi.game.models.PlayerCharacter.HUMAN_SOLDIER;
import static com.ffxvi.game.models.PlayerCharacter.SKELETON_HOODED;
import static com.ffxvi.game.models.PlayerCharacter.SKELETON_NORMAL;
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

		if (currentAnimation == this.playerSkin.getAnimation(SLASHING, super.direction)) {
			currentFrame = this.currentAnimation.getKeyFrame(counter);
			counter++;
		} else {
			currentFrame = this.currentAnimation.getKeyFrame(super.stateTime, true);
		}

		if (counter != 0) {
			currentFrame = this.playerSkin.getAnimation(SLASHING, super.direction).getKeyFrame(counter);
			counter++;
		}

		if (counter >= 10) {
			counter = 0;
		}

		batch.draw(currentFrame, super.x, super.y, Utils.GRIDSIZE, Utils.GRIDSIZE);

		super.checkSlashing();
	}

	/**
	 * Makes the player die.
	 *
	 * @param killerName
	 */
	@Override
	public void die(final String killerName) {
		super.die(killerName);

		// Set animation to DEATH
		super.animation = PlayerAnimation.DYING;
		this.changeAnimation();

		// Delay in seconds
		float delay = 1;

		// Wait for X time
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {

				// Respawn player
				screen.respawn(killerName);
				LibPlayer.this.respawn();
			}
		}, delay);
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
	public void setDirection(Direction direction) {
		this.direction = direction;

		//If slashing, don't move
		if (!(currentAnimation == playerSkin.getAnimation(SLASHING, super.direction)) || counter2 == 0) {

			if (!this.checkCollision(this.getCollisionBox(), GameScreen.getCurrentMap().getWallObjects(), GameScreen.getCurrentMap().getObjects())) {
				this.move();
				this.animation = PlayerAnimation.WALKING;
				this.changeAnimation();
			}
		} else {
			counter2++;
		}
		if (counter2++ > 30) {
			counter2 = 0;
		}

		this.checkDoorCollision(this.getCollisionBox(), GameScreen.getCurrentMap().getDoors());

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

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
package com.ffxvi.game.models;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.ffxvi.game.entities.PlayerAnimation;
import static com.ffxvi.game.entities.PlayerAnimation.SLASHING;
import com.ffxvi.game.support.PropertyListenerNames;
import com.ffxvi.game.support.Utils;
import com.ffxvi.game.support.Vector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Entity which represents a player.
 */
public class Player extends SimplePlayer {

	/**
	 * The amount of coordinates a player moves per tick while walking.
	 */
	private static final float WALK_SPEED = 5f;

	/**
	 * The amount of coordinates a player moves per tick while
	 * running/sprinting.
	 */
	private static final float RUN_SPEED = 8f;

	/**
	 * The cooldown between firing.
	 */
	private static final float SHOOTCOOLDOWN = 0.5f;

	/**
	 * The direction where the player is aiming his/her weapon in degrees.
	 */
	private float aimDirection;

	/**
	 * The time before a next shot can be fired.
	 */
	private long shootStart;

	/**
	 * The grid size of the player in width.
	 */
	private final int modifiedGridSizeX;

	/**
	 * The grid size of the player in height.
	 */
	private final int modifiedGridSizeY;

	private GameManager gameManager;

	private PropertyChangeSupport propertyChangeSupport;

	/**
	 * A boolean indicating if the player is dead.
	 */
	protected boolean isDead;

	/**
	 * A boolean indicating if the player is spectating.
	 */
	protected boolean isSpectating;

	private long lastSlash = 0;

	/**
	 * Default constructor for Player.
	 *
	 * @param character The player's character.
	 * @param playerName The name of this player. This can not be an empty
	 * String (excluding spaces).
	 * @param position The position of this player.
	 * @param gameManager The gamemanager object.
	 * @param roomId The id of the room where the player is in.
	 */
	public Player(PlayerCharacter character, String playerName, Vector position, GameManager gameManager, int roomId) {
		super(playerName, position.getX(), position.getY(), roomId, character);

		this.gameManager = gameManager;

		this.speed = Player.WALK_SPEED;

		this.aimDirection = 0f;

		int gridsize = Utils.GRIDSIZE;
		this.modifiedGridSizeX = gridsize - 32;
		this.modifiedGridSizeY = gridsize - 16;

		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}

	/**
	 * Special constructor for Player, only use this for player data received
	 * from the server.
	 *
	 * @param gameManager The gamemanager object.
	 */
	public Player(GameManager gameManager) {
		super("blank", 0, 0, 1, PlayerCharacter.SKELETON_NORMAL);

		this.gameManager = gameManager;

		this.aimDirection = 0f;

		int gridsize = Utils.GRIDSIZE;
		this.modifiedGridSizeX = gridsize - 32;
		this.modifiedGridSizeY = gridsize - 16;
	}

	/**
	 * Special method for setting all of this Player's data to the given
	 * SimplePlayer data. Use this to prevent having to create a new Player
	 * object every time.
	 *
	 * @param player the SimplePlayer's data that is to replace this Player's
	 * data
	 */
	protected void setDataInner(SimplePlayer player) {
		super.playerName = player.playerName;
		super.hitPoints = player.hitPoints;
		super.score = player.score;
		super.roomId = player.roomId;
		super.x = player.x;
		super.y = player.y;
		super.speed = player.speed;
		super.direction = player.direction;
		super.skin = player.skin;
		super.animation = player.animation;
		super.stateTime = player.stateTime;
	}

	/**
	 * Gets the x position of this player.
	 *
	 * @return The x position of the player.
	 */
	@Override
	public float getX() {
		return this.x;
	}

	/**
	 * Gets the y position of this player.
	 *
	 * @return The y position of the player.
	 */
	@Override
	public float getY() {
		return this.y;
	}

	/**
	 * Sets the position of this player.
	 *
	 * @param x The new x position of this player.
	 * @param y The new y position of this player.
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the players x and y to the given position.
	 *
	 * @param position The player's position.
	 */
	public void setPosition(Vector position) {
		if (position == null) {
			throw new IllegalArgumentException("Position can not be null.");
		}

		this.x = position.getX();
		this.y = position.getY();
	}

	/**
	 * Gets the players movement speed.
	 *
	 * @return The movement speed of the player.
	 */
	public float getMoveSpeed() {
		return this.speed;
	}

	/**
	 * Sets the players movement speed.
	 *
	 * @param moveSpeed The movement speed of the player. When negative, throws
	 * an IllegalArgumentException.
	 */
	public void setMoveSpeed(float moveSpeed) {
		if (moveSpeed < 0) {
			throw new IllegalArgumentException();
		}

		this.speed = moveSpeed;
	}

	/**
	 * Sets whether the player is sprinting.
	 *
	 * @param isSprinting A boolean indicating whether the player is sprinting.
	 */
	public void setSprint(boolean isSprinting) {
		if (isSprinting) {
			this.speed = Player.RUN_SPEED;
		} else {
			this.speed = Player.WALK_SPEED;
		}
	}

	/**
	 * Sets the aim direction of this player by the given coördinates of the
	 * mouse.
	 *
	 * @param mousePosition The position of the mouse, relative to the player's
	 * position.
	 */
	public void setAimDirection(Vector mousePosition) {
		if (mousePosition == null) {
			throw new IllegalArgumentException("Mouse position can not be null.");
		}

		// Calculate the direction of the bullet using arctan
		float dir = (float) Math.toDegrees(Math.atan2(mousePosition.getY()
				- this.getPosition().getX() - (this.modifiedGridSizeY) - (Utils.GRIDSIZE / 3),
				mousePosition.getX() - this.getPosition().getX()
				- (this.modifiedGridSizeX / 2)));

		if (dir < 0) {
			dir += 360;
		}

		this.aimDirection = dir;
	}

	/**
	 * Sets the aim direction of this player by the given axis input from the
	 * controller.
	 *
	 * @param controllerInputX The value of the x-axis.
	 * @param controllerInputY The value of the y-axis.
	 */
	public void setAimDirection(float controllerInputX, float controllerInputY) {
		if (Float.compare(controllerInputX, 0f) == 0 && Float.compare(controllerInputY, 0f) == 0) {
			throw new IllegalArgumentException("inputX and inputY can't both be 0.");
		}

		float angle = (float) Math.toDegrees(Math.atan2(controllerInputX,
				controllerInputY));

		angle -= 90;

		if (angle < 0) {
			angle += 360;
		}

		this.aimDirection = angle;
	}

	public void setRoomId(int id) {
		roomId = id;
	}

	/**
	 * Gets the name of this player.
	 *
	 * @return The name of the player.
	 */
	@Override
	public String getName() {
		return this.playerName;
	}

	/**
	 * Gets the player's score.
	 *
	 * @return The score of the player.
	 */
	@Override
	public int getScore() {
		return score;
	}

	/**
	 * Sets the player's score.
	 *
	 * @param score The score to set. When smaller than 0, throws an
	 * IllegalArgumentException.
	 */
	public void setScore(int score) {
		if (score < 0) {
			throw new IllegalArgumentException("The score can not be a negative value.");
		}

		this.score = score;
	}

	/**
	 * Receives damage which is subtracted from the player's hitpoints.
	 *
	 * @param amount The amount of damage.
	 * @param attacker The name of the player that caused the damage.
	 */
	public void receiveDamage(int amount, String attacker) {
		if (!this.isDead && !this.isSpectating) {
			this.hitPoints -= amount;

			if (this.hitPoints <= 0) {
				// Set the health to 0
				this.hitPoints = 0;

				this.die(attacker);
				this.gameManager.chatManager.sendMessage("[SERVER]", this.playerName.toLowerCase() + " HAS DIED");
			}
			this.firePropertyChangeEvent(PropertyListenerNames.PLAYER_HEALTH, null);
		}
	}

	/**
	 * Lets the player die.
	 *
	 * @param killer
	 * @return A boolean indicating if the player could die.
	 */
	public boolean die(final String killer) {
		if (!this.isDead && !this.isSpectating) {
			this.stateTime = 0;
			// Set animation to DEATH
			this.animation = PlayerAnimation.DYING;

			// Set isDead to true
			this.isDead = true;
			// Delay in seconds
			float delay = 1;
			return true;
		}

		return false;
	}

	public void respawn() {
		isDead = false;

		// Reset hitpoints
		hitPoints = 100;
	}

	/**
	 * Gets the players position.
	 *
	 * @return The position of the player.
	 */
	public Vector getPosition() {
		return new Vector(this.x, this.y);
	}

	/**
	 * A boolean indicating whether the player can fire, based on whether the
	 * cooldown since the last shot has passed.
	 *
	 * @return A boolean indicating whether the player can fire.
	 */
	private boolean canFire() {
		if (!this.isSpectating && !this.isDead) {
			return System.nanoTime() - this.shootStart > SHOOTCOOLDOWN * 1000000000;
		}

		return false;
	}

	/**
	 * Fires a new projectile at the aim direction, given the player can fire.
	 *
	 * @return a boolean indicating whether the projectile was added.
	 */
	public boolean fire() {
		if (this.canFire()) {
			// Reset the shoot delay
			this.shootStart = System.nanoTime();
			// Create a bullet inside the player with the direction and speed
			this.gameManager.addProjectile(new Projectile(new Vector(this.x
					+ (modifiedGridSizeX), this.y + (modifiedGridSizeY / 2)),
					30, this.aimDirection, this.roomId, this.playerName, this.gameManager), false);

			return true;
		}

		return false;
	}

	protected boolean canSlash() {
		return !this.isDead && !this.isSpectating && (lastSlash == 0 || System.currentTimeMillis() - lastSlash >= 500);
	}

	/**
	 * Slashes in the given direction, given the player can slash.
	 */
	public void slash() {
		if (this.canSlash()) {
			this.animation = SLASHING;
			this.lastSlash = System.currentTimeMillis();
		}
	}

	/**
	 * Moves in the current direction.
	 */
	protected void move() {
		if (!this.isDead) {
			switch (this.direction) {
				default:
				case LEFT:
					x -= this.speed;
					break;
				case RIGHT:
					x += this.speed;
					break;
				case UP:
					y += this.speed;
					break;
				case DOWN:
					y -= this.speed;
					break;
			}

			this.animation = PlayerAnimation.WALKING;
			this.gameManager.sendPlayer(new SimplePlayer(this));
		}
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
		
	}

	/**
	 * Gets a collision box for the direction in which the player is moving.
	 *
	 * @return
	 */
	protected Rectangle getCollisionBox() {
		Rectangle rec;

		switch (this.direction) {
			default:
			case LEFT:
				rec = new Rectangle(x + 16 - this.speed, y,
						this.modifiedGridSizeX, this.modifiedGridSizeY);
				break;
			case RIGHT:
				rec = new Rectangle(x + 16 + this.speed, y,
						this.modifiedGridSizeX, this.modifiedGridSizeY);
				break;
			case UP:
				rec = new Rectangle(x + 16, y + this.speed,
						this.modifiedGridSizeX, this.modifiedGridSizeY);
				break;
			case DOWN:
				rec = new Rectangle(x + 16, y - this.speed,
						this.modifiedGridSizeX, this.modifiedGridSizeY);
				break;
		}

		return rec;
	}

	/**
	 * Update method
	 */
	public void update() {
		this.gameManager.sendPlayer(new SimplePlayer(this));
	}

	protected void checkSlashing() {
		if (!this.isSpectating && !this.isDead) {
			//Check if player gets slashed
			Collection<SimplePlayer> localMultiplayers = new ArrayList(gameManager.getMultiplayers());
			if (localMultiplayers.isEmpty()) {
				return;
			}
			for (SimplePlayer p : localMultiplayers) {
				if (p.animation == PlayerAnimation.SLASHING
						&& !p.getName().equals(this.playerName)) {
					Circle cEnemy = new Circle();
					cEnemy.x = p.getX();
					cEnemy.y = p.getY();
					cEnemy.radius = 50.0f;

					Circle cThis = new Circle();
					cThis.x = this.getX();
					cThis.y = this.getY();
					cThis.radius = 50.0f;

					if (cEnemy.overlaps(cThis)) {
						this.receiveDamage(1, p.playerName);
					}
				}
			}
		}
	}

	/**
	 * Sets the player's animation to idle.
	 */
	public void setIdle() {
		this.animation = PlayerAnimation.IDLE;
	}

	/**
	 * Gets the collision rectangle of this player.
	 *
	 * @return The collision rectangle of this player.
	 */
	public Rectangle getRectangle() {
		return new Rectangle(this.x, this.y, this.modifiedGridSizeX, this.modifiedGridSizeY);
	}

	public void subscribe(PropertyChangeListener listener, String property) {
		this.propertyChangeSupport.addPropertyChangeListener(property, listener);

	}

	public void unsubsribe(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

	private void firePropertyChangeEvent(String property, Object newValue) {
		this.propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, property, null, newValue));
	}
}

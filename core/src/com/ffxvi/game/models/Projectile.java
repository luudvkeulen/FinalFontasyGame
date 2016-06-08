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

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.ffxvi.game.screens.GameScreen;
import com.ffxvi.game.support.Vector;
import java.util.Objects;

/**
 * This class contains all the properties for the Projectile. It contains the
 * position, rotation and AmmoType.
 */
public class Projectile extends SimpleProjectile {

	/**
	 * Vector2f containing the position of the projectile.
	 */
	protected Vector position;

	/**
	 * The nanotime at which this projectile was spawned.
	 */
	private long startTime;

	/**
	 * The delay in seconds before the projectile despawns.
	 */
	protected long despawnDelay;

	/**
	 * A boolean indicating if the projectile can collide.
	 */
	protected boolean canCollide;

	private GameManager gameManager;
	/**
	 * A GameScreen object.
	 */
	/**
	 * Initializes a new projectile object.
	 *
	 * @param position The position in the room.
	 * @param speed The speed of the projectile. When smaller than 0, throw an
	 * IllegalArgumentException.
	 * @param rotation The rotation of the projectile. When not at least 0 or
	 * smaller than 360, throw an IllegalArgumentException.
	 * @param roomID The id of the room which this projectile exists in. When
	 * not greater than 0, throw an IllegalArgumentException.
	 * @param playerName The name of the player which fired the bullet. When an
	 * empty String (excluding spaces), throw an IllegalArgumentException.
	 */
	public Projectile(Vector position, float speed, float rotation, int roomID, String playerName, GameManager gameManager) {
		super(rotation, speed, position.getX(), position.getY(), playerName, roomID);

		if (rotation < 0 || rotation >= 360) {
			throw new IllegalArgumentException("The rotation of the projectile must be >= 0 & < 360.");
		}

		if (roomID <= 0) {
			throw new IllegalArgumentException("The room id must be bigger than 0.");
		}

		if (playerName == null || playerName.trim().isEmpty()) {
			throw new IllegalArgumentException("The playername can neither be null nor an empty String (excluding spaces).");
		}

		this.position = position;
		this.rotation = rotation;
		this.speed = speed;
		this.canCollide = true;
		this.despawnDelay = 10;
		this.startTime = System.nanoTime();
		
		this.gameManager = gameManager;
	}

	protected boolean checkPlayerCollision(Rectangle rec, Player mainPlayer) {
		// Get rectangle of this player
		Rectangle playerRectangle = mainPlayer.getRectangle();

		// Check if the rectangles overlap
		if (rec.overlaps(playerRectangle)) {
			// Return true if it has collision
			return true;
		}

		// Return false if no collision has been found
		return false;
	}

	/**
	 * Gets the position in the room of this projectile.
	 *
	 * @return A Vector2f containing the position in the room of this
	 * projectile.
	 */
	public Vector getPosition() {
		return this.position;
	}

	/**
	 * Gets the do-remove boolean from this class.
	 *
	 * @return A boolean which says if a projectile can be destroyed.
	 */
	public boolean shouldRemove() {
		return System.nanoTime() - this.startTime > this.despawnDelay * 1000000000;
	}

	public void collideWithPlayer() {
		this.canCollide = false;
		this.despawnDelay = 0;
		this.speed = 0;

	}

	protected boolean checkCollision() {
		Rectangle playerCollision = new Rectangle(this.position.getX(), this.position.getY(), 32, 32);

		// Check collision with player if the player's name is not equal
		// to the owner's name of the projectile
		System.out.printf("GameManager = %s.\n", gameManager);
		if (!this.playerName.equals(gameManager.getMainPlayer().getName())) {
			boolean collisionPlayer = checkPlayerCollision(playerCollision, gameManager.getMainPlayer());

			if (collisionPlayer) {
				collideWithPlayer();

				gameManager.getMainPlayer().receiveDamage(10, this.playerName);

				// Return, as the bullet should be removed and should no
				// longer be checked for collision with walls
				return true;
			}
		}
		Rectangle wallCollision = new Rectangle(this.position.getX(), this.position.getY(), 1, 1);
		// Check collision with walls
		boolean collisionWall = checkWallCollision(wallCollision, GameScreen.getCurrentMap().getWallObjects());

		if (collisionWall) {
			this.position.setX(this.position.getX() - (this.speed * (float) Math.cos(this.rotation * Math.PI / 180)));
			this.position.setY(this.position.getY() - (this.speed * (float) Math.sin(this.rotation * Math.PI / 180)));
			this.canCollide = false;
			this.speed = 0;
			return true;
		}
		return false;
	}
	
	/**
     * Checks if the given rectangle collides with any of the given mapobjects.
     * @param rec The rectangle to check.
     * @param objects The objects to check for collision with.
     * @return 
     */
    private boolean checkWallCollision(Rectangle rec, MapObjects objects) {
        for (RectangleMapObject mapObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangleMapObject = mapObject.getRectangle();
            if (rec.overlaps(rectangleMapObject)) {
                return true;
            }
        }
        return false;
    }

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13 * hash + Objects.hashCode(this.position);
		hash = 13 * hash + Float.floatToIntBits(this.rotation);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Projectile other = (Projectile) obj;
		if (Float.floatToIntBits(this.rotation) != Float.floatToIntBits(other.rotation)) {
			return false;
		}
		if (!Objects.equals(this.position, other.position)) {
			return false;
		}
		return true;
	}

}

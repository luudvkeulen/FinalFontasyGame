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
package com.ffxvi.game.entities;

import java.io.Serializable;

/**
 * Simple projectile used for cross-component communication.
 *
 */
public class SimpleProjectile implements Serializable {

    /**
     * The rotation of the projectile.
     */
    protected float rotation;

    /**
     * The speed of this projectile.
     */
    protected float speed;

    /**
     * The x position of the projectile.
     */
    protected float x;

    /**
     * The y position of the projectile.
     */
    protected float y;

    /**
     * The name of the player who fired the projectile.
     */
    protected String playerName;

    /**
     * The id of the room this projectile is located in.
     */
    protected int roomID;

    /**
     * Initializes a new Simple Projectile.
     *
     * @param rotation The rotation of the projectile. When not greater than 0
     * or smaller than 360, throw an IllegalArgumentException.
     * @param speed The speed of the projectile. When negative, throw an
     * IllegalArgumentException.
     * @param x The x position. When negative, throw an
     * IllegalArgumentException.
     * @param y The y position. When negative, throw an
     * IllegalArgumentException.
     * @param playerName THe name of the player. When an empty String (excluding
     * spaces), throw an IllegalArgumentException.
     * @param roomID The id of the room this projectile is located in. When
     * negative, throw an IllegalArgumentException.
     */
    public SimpleProjectile(float rotation, float speed, float x, float y, String playerName, int roomID) {

        if (rotation < 0 || rotation >= 360) {
            throw new IllegalArgumentException("Rotation must be in the range >= 0 <= 360.");
        }

        if (speed < 0) {
            throw new IllegalArgumentException("Speed can not be negative.");
        }

        if (x < 0) {
            throw new IllegalArgumentException("X can not be negative.");
        }

        if (y < 0) {
            throw new IllegalArgumentException("Y can not be negative.");
        }

        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Playername can neither be null nor an empty String (excluding spaces).");
        }

        if (roomID < 0) {
            throw new IllegalArgumentException("RoomID can not be a negative value.");
        }

        this.rotation = rotation;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.playerName = playerName;
        this.roomID = roomID;
    }
	
	public SimpleProjectile(Projectile projectile) {

        if (projectile == null) {
            throw new IllegalArgumentException("Projectile may not be null");
        }
		
		this.rotation = projectile.rotation;
		this.speed = projectile.speed;
		this.x = projectile.x;
		this.y = projectile.y;
		this.playerName = projectile.playerName;
		this.roomID = projectile.roomID;
	}
	
	/**
	 * Get the angle that this projectile was fired at
	 * @return the angle that this projectile was fired at
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Get the speed of this projectile
	 * @return the speed of this projectile
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * Get the X position of this projectile
	 * @return the X position of this projectile
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Get the Y position of this projectile
	 * @return the Y position of this projectile
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Get the name of the player that shot this projectile
	 * @return the name of the player that shot this projectile
	 */
	public String getShooterName() {
		return playerName;
	}
	
	/**
	 * Get the ID of the room that this projectile is in
	 * @return the ID of the room that this projectile is in
	 */
	public int getRoomID() {
		return roomID;
	}
}

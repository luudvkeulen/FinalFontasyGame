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

/**
 * An enumeration indicating which direction an entity is facing. This is
 * currently used for players and projectiles.
 */
public enum Direction {
	/**
	 * Towards the top side of the screen.
	 */
	UP,
	/**
	 * Towards the top left side of the screen.
	 */
	UPLEFT,
	/**
	 * Towards the left side of the screen.
	 */
	LEFT,
	/**
	 * Towards the bottom left side of the screen.
	 */
	DOWNLEFT,
	/**
	 * Towards the bottom side of the screen.
	 */
	DOWN,
	/**
	 * Towards the bottom right side of the screen.
	 */
	DOWNRIGHT,
	/**
	 * Towards the right side of the screen.
	 */
	RIGHT,
	/**
	 * Towards the upper right side of the screen.
	 */
	UPRIGHT
}

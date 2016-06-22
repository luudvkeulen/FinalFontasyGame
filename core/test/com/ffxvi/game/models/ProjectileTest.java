/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.models;

import com.badlogic.gdx.math.Rectangle;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.support.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.internal.runners.statements.ExpectException;

/**
 *
 * @author Joel
 */
public class ProjectileTest {

	public ProjectileTest() {
	}
	float x = 2f;
	float y = 2f;
	float speed = 30f;
	float rotation = 180f;
	int roomdID = 1;
	String playerName = "speler";
	Projectile projectile;
	GameManager gameManager;

	@Before
	public void setUp() {
		MainClass.getInstance().selectedIp = "";
		gameManager = new GameManager(false);
		projectile = new Projectile(new Vector(x, y), speed, rotation, roomdID, playerName, gameManager);
	}

	/**
	 *
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testIllegalArgumentRoomID() {
		Projectile projectile2 = new Projectile(new Vector(x, y), speed, rotation, 0, playerName, gameManager);
	}
	
	/**
	 *
	 */

	/**
	 *
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorNullProjectile() {
		Projectile projectile2 = new Projectile(null,gameManager);
	}
	/**
	 *
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testConstructorNullManager() {
		Projectile projectile2 = new Projectile(projectile,null);
	}
	/**
	 *
	 */
	@Test
	public void testConstructorProjectile() {
		Projectile projectile2 = new Projectile(projectile,gameManager);
		assertNotNull(projectile2);
	}
	
	/**
	 *
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testIllegalArgumentplayerName() {
		Projectile projectile2 = new Projectile(new Vector(x, y), speed, rotation, roomdID, "", gameManager);
	}
	@Test (expected = IllegalArgumentException.class)
	public void testIllegalArgumentplayerNameNull() {
		Projectile projectile2 = new Projectile(new Vector(x, y), speed, rotation, roomdID, null, gameManager);
	}

	/**
	 * Test of checkPlayerCollision method, of class Projectile.
	 */
	@Test
	public void testCheckPlayerCollision() {
		Rectangle rec = new Rectangle(0, 0, 5, 5);
		Player mainPlayer = new Player(PlayerCharacter.HUMAN_PIRATE, playerName, new Vector(2,2), gameManager, roomdID);
		Projectile instance = projectile;
		boolean expResult = true;
		boolean result = instance.checkPlayerCollision(rec, mainPlayer);
		assertEquals(expResult, result);
	}

	/**
	 * Test of getPosition method, of class Projectile.
	 */
	@Test
	public void testGetPosition() {
		Vector expResult = new Vector(2, 2);
		Vector result = projectile.getPosition();
		assertEquals(expResult, result);
	}

	/**
	 * Test of shouldRemove method, of class Projectile.
	 */
	@Test
	public void testShouldRemove() {
		Projectile instance = projectile;
		boolean expResult = true;
		instance.despawnDelay = 0;
		boolean result = instance.shouldRemove();
		assertEquals(expResult, result);
	}

	/**
	 * Test of collideWithPlayer method, of class Projectile.
	 */
	@Test
	public void testCollideWithPlayer() {
		Projectile instance = projectile;
		instance.collideWithPlayer();
		assertEquals(false, instance.canCollide);
		long l = 0;
		assertEquals(l, instance.despawnDelay);

	}
	/**
	 * Test of collideWithPlayer method, of class Projectile.
	 */
	@Test
	public void testCheckCollisionBCS() {
		Projectile instance = projectile;
		Player mainPlayer = new Player(PlayerCharacter.HUMAN_PIRATE, "andereSpeler", new Vector(2,2), gameManager, roomdID);
		try {
			
			gameManager.setMainPlayer(mainPlayer);
		} catch (NullPointerException ex) {
			
		
		}
		instance.collideWithPlayer();
		boolean result = instance.checkCollision();
		assertTrue(result);
	}

}

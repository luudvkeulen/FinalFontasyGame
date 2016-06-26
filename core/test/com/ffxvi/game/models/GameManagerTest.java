/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.models;

import com.ffxvi.game.MainClass;
import com.ffxvi.game.support.Vector;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joel
 */
public class GameManagerTest {

	public GameManagerTest() {
	}
	GameManager gameManager;

	@Before
	public void setUp() {
		MainClass.getInstance().selectedIp = "";
		gameManager = new GameManager(false);
	}

	/**
	 * Test of getProjectiles method, of class GameManager.
	 */
	@Test
	public void testGetAddProjectiles() {
		System.out.println("getProjectiles");

		assertNotNull(gameManager.getProjectiles());
		Projectile projectile = new Projectile(new Vector(1f, 1f),
				30f, 30f, 1, "bliep", gameManager);
		gameManager.addProjectile(projectile, true);
		assertEquals(1, gameManager.getProjectiles().size());
		gameManager.removeProjectile(projectile);
		assertEquals(0, gameManager.getProjectiles().size());
	}

	/**
	 * Test of getPlayer method, of class GameManager.
	 */
	@Test
	public void testGetAddMultiPlayers() {

		assertNotNull(gameManager.getMultiplayers());
		SimplePlayer player = new SimplePlayer("speler", 0f, 0f, 1, PlayerCharacter.HUMAN_SOLDIER);
		gameManager.addToMultiplayers(player);
		assertEquals(1, gameManager.getMultiplayers().size());
		List<SimplePlayer> players = new ArrayList<SimplePlayer>();
		players.add(player);
		players.add(player);
		players.add(player);
		gameManager.addMultiPlayers(players);
		assertEquals(3, gameManager.getMultiplayers().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAddMultiPlayersNull() {

		gameManager.addMultiPlayers(null);
	}

	/**
	 * Test of getPlayer method, of class GameManager.
	 */
	@Test
	public void testGetAddMultiPlayer() {
		Player player = new Player(gameManager);
		gameManager.setMultiplayer(player);
		assertNotNull(gameManager.getMultiplayer());
	}

	@Test
	public void testGetAddMainPlayer() {
		Player player = new Player(gameManager);
		try {

			gameManager.setMainPlayer(player);
		} catch (NullPointerException e) {

		}

		assertNotNull(gameManager.getMainPlayer());
	}
}

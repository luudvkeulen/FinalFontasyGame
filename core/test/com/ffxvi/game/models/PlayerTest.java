/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.models;

import com.badlogic.gdx.math.Rectangle;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.support.Vector;
import java.beans.PropertyChangeListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joel
 */
public class PlayerTest {

	public PlayerTest() {
	}
	float x = 2f;
	float y = 2f;
	float speed = 30f;
	float rotation = 180f;
	int roomdID = 1;
	String playerName = "speler";
	Player player;
	GameManager gameManager;

	@Before
	public void setUp() {
		MainClass.getInstance().selectedIp = "";
		gameManager = new GameManager(false);
		player = new Player(PlayerCharacter.HUMAN_PIRATE, playerName, new Vector(2, 2), gameManager, roomdID);
	}

	/**
	 * Test of getX method, of class Player.
	 */
	@Test
	public void testGetX() {
		System.out.println("getX");
		Player instance = player;
		float expResult = 2f;
		float result = instance.getX();
		assertEquals(expResult, result, 0);

	}

	/**
	 * Test of getY method, of class Player.
	 */
	@Test
	public void testGetY() {
		System.out.println("getY");
		Player instance = player;
		float expResult = 2f;
		float result = instance.getY();
		assertEquals(expResult, result, 0);

	}

	/**
	 * Test of setPosition method, of class Player.
	 */
	@Test
	public void testSetPosition_float_float() {
		System.out.println("setPosition");
		float x = 3f;
		float y = 3f;
		player.setPosition(x, y);
		assertEquals(3, player.getX(), 0);
		assertEquals(3, player.getY(), 0);
	}

	/**
	 * Test of setPosition method, of class Player.
	 */
	@Test
	public void testSetPosition_Vector() {
		player.setPosition(new Vector(3, 3));
		assertEquals(3, player.getX(), 0);
		assertEquals(3, player.getY(), 0);
	}

	/**
	 * Test of setPosition method, of class Player.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPositionNull_Vector() {
		player.setPosition(null);
	}

	/**
	 * Test of getMoveSpeed method, of class Player.
	 */
	@Test
	public void testGetMoveSpeed() {
		float expResult = 5f;
		assertEquals(expResult, player.getMoveSpeed(), 0);
	}

	/**
	 * Test of setMoveSpeed method, of class Player.
	 */
	@Test
	public void testSetMoveSpeed() {

		float moveSpeed = 40f;
		player.setMoveSpeed(moveSpeed);
		assertEquals(moveSpeed, player.getMoveSpeed(), 0);

	}

	/**
	 * Test of setMoveSpeed method, of class Player.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMoveSpeedNegative() {

		float moveSpeed = -40f;
		player.setMoveSpeed(moveSpeed);

	}

	/**
	 * Test of setSprint method, of class Player.
	 */
	@Test
	public void testSetSprintRun() {
		player.setSprint(true);
		float result = player.getMoveSpeed();
		float expResult = 8f;
		assertEquals(expResult, result,0);
	}
	/**
	 * Test of setSprint method, of class Player.
	 */
	@Test
	public void testSetSprintWalk() {
		player.setSprint(true);
		player.setSprint(false);
		float result = player.getMoveSpeed();
		float expResult = 5f;
		assertEquals(expResult, result,0);
	}

	/**
	 * Test of setAimDirection method, of class Player.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testSetAimDirection_VectorNull() {
		System.out.println("setAimDirection");
		Vector mousePosition = null;
		player.setAimDirection(mousePosition);
	}
	
	/**
	 * Test of setAimDirection method, of class Player.
	 */
	@Test
	public void testSetAimDirection_Vector() {
		System.out.println("setAimDirection");
		Vector mousePosition = new Vector(2, -4);
		player.setAimDirection(mousePosition);
		try {
			player.fire();
		} catch (NullPointerException ex)
		{
			
		} catch (java.lang.ExceptionInInitializerError ex) {
		
		}
		
		
		
		assertEquals(90f, gameManager.getProjectiles().get(0).getRotation());
	}
//
//	/**
//	 * Test of setAimDirection method, of class Player.
//	 */
//	@Test
//	public void testSetAimDirection_float_float() {
//		System.out.println("setAimDirection");
//		float controllerInputX = 0.0F;
//		float controllerInputY = 0.0F;
//		Player instance = null;
//		instance.setAimDirection(controllerInputX, controllerInputY);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of setRoomId method, of class Player.
//	 */
//	@Test
//	public void testSetRoomId() {
//		System.out.println("setRoomId");
//		int id = 0;
//		Player instance = null;
//		instance.setRoomId(id);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getName method, of class Player.
//	 */
//	@Test
//	public void testGetName() {
//		System.out.println("getName");
//		Player instance = null;
//		String expResult = "";
//		String result = instance.getName();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getScore method, of class Player.
//	 */
//	@Test
//	public void testGetScore() {
//		System.out.println("getScore");
//		Player instance = null;
//		int expResult = 0;
//		int result = instance.getScore();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of setScore method, of class Player.
//	 */
//	@Test
//	public void testSetScore() {
//		System.out.println("setScore");
//		int score = 0;
//		Player instance = null;
//		instance.setScore(score);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of receiveDamage method, of class Player.
//	 */
//	@Test
//	public void testReceiveDamage() {
//		System.out.println("receiveDamage");
//		int amount = 0;
//		String attacker = "";
//		Player instance = null;
//		instance.receiveDamage(amount, attacker);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of die method, of class Player.
//	 */
//	@Test
//	public void testDie() {
//		System.out.println("die");
//		String killer = "";
//		Player instance = null;
//		instance.die(killer);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of respawn method, of class Player.
//	 */
//	@Test
//	public void testRespawn() {
//		System.out.println("respawn");
//		Player instance = null;
//		instance.respawn();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getPosition method, of class Player.
//	 */
//	@Test
//	public void testGetPosition() {
//		System.out.println("getPosition");
//		Player instance = null;
//		Vector expResult = null;
//		Vector result = instance.getPosition();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of fire method, of class Player.
//	 */
//	@Test
//	public void testFire() {
//		System.out.println("fire");
//		Player instance = null;
//		boolean expResult = false;
//		boolean result = instance.fire();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of setIdle method, of class Player.
//	 */
//	@Test
//	public void testSetIdle() {
//		System.out.println("setIdle");
//		Player instance = null;
//		instance.setIdle();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of slash method, of class Player.
//	 */
//	@Test
//	public void testSlash() {
//		System.out.println("slash");
//		Player instance = null;
//		instance.slash();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of move method, of class Player.
//	 */
//	@Test
//	public void testMove() {
//		System.out.println("move");
//		Player instance = null;
//		instance.move();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getCollisionBox method, of class Player.
//	 */
//	@Test
//	public void testGetCollisionBox() {
//		System.out.println("getCollisionBox");
//		Player instance = null;
//		Rectangle expResult = null;
//		Rectangle result = instance.getCollisionBox();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of update method, of class Player.
//	 */
//	@Test
//	public void testUpdate() {
//		System.out.println("update");
//		Player instance = null;
//		instance.update();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of checkSlashing method, of class Player.
//	 */
//	@Test
//	public void testCheckSlashing() {
//		System.out.println("checkSlashing");
//		Player instance = null;
//		instance.checkSlashing();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getRectangle method, of class Player.
//	 */
//	@Test
//	public void testGetRectangle() {
//		System.out.println("getRectangle");
//		Player instance = null;
//		Rectangle expResult = null;
//		Rectangle result = instance.getRectangle();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of subscribe method, of class Player.
//	 */
//	@Test
//	public void testSubscribe() {
//		System.out.println("subscribe");
//		PropertyChangeListener listener = null;
//		String property = "";
//		Player instance = null;
//		instance.subscribe(listener, property);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of unsubsribe method, of class Player.
//	 */
//	@Test
//	public void testUnsubsribe() {
//		System.out.println("unsubsribe");
//		PropertyChangeListener listener = null;
//		Player instance = null;
//		instance.unsubsribe(listener);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//	
}

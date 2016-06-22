/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.models;

import com.badlogic.gdx.math.Rectangle;
import com.ffxvi.game.MainClass;
import com.ffxvi.game.support.Utils;
import com.ffxvi.game.support.Vector;
import java.beans.PropertyChangeListener;
import javax.rmi.CORBA.Util;
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
		Vector mousePosition = new Vector(4, 2);
		player.setAimDirection(mousePosition);
		try {
			player.fire();
		} catch (NullPointerException ex)
		{
			
		} catch (java.lang.ExceptionInInitializerError ex) {
		
		}
		
		
		
		assertEquals(258.53046f, gameManager.getProjectiles().get(0).getRotation(),0);
	}

	/**
	 * Test of setAimDirection method, of class Player.
	 */
	@Test
	public void testSetAimDirection_float_floatDown() {

		float controllerInputX = 0.0F;
		float controllerInputY = -1.0F;
		player.setAimDirection(controllerInputX, controllerInputY);
			try {
			player.fire();
		} catch (NullPointerException ex)
		{
			
		} catch (java.lang.ExceptionInInitializerError ex) {
		
		}
		assertEquals(90f, gameManager.getProjectiles().get(0).getRotation(),0);
	}
	/**
	 * Test of setAimDirection method, of class Player.
	 */
	@Test
	public void testSetAimDirection_float_floatRight() {

		float controllerInputX = 1.0F;
		float controllerInputY = 0.0F;
		player.setAimDirection(controllerInputX, controllerInputY);
			try {
			player.fire();
		} catch (NullPointerException ex)
		{
			
		} catch (java.lang.ExceptionInInitializerError ex) {
		
		}
		assertEquals(0f, gameManager.getProjectiles().get(0).getRotation(),0);
	}
	/**
	 * Test of setAimDirection method, of class Player.
	 */
	@Test
	public void testSetAimDirection_float_floatUp() {

		float controllerInputX = 0.0F;
		float controllerInputY = 1.0F;
		player.setAimDirection(controllerInputX, controllerInputY);
			try {
			player.fire();
		} catch (NullPointerException ex)
		{
			
		} catch (java.lang.ExceptionInInitializerError ex) {
		
		}
		assertEquals(270, gameManager.getProjectiles().get(0).getRotation(),0);
	}
	/**
	 * Test of setAimDirection method, of class Player.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testSetAimDirection_float_floatIllegalArg() {

		float controllerInputX = 0.0F;
		float controllerInputY = 0.0F;
		player.setAimDirection(controllerInputX, controllerInputY);
	}

	/**
	 * Test of setRoomId method, of class Player.
	 */
	@Test
	public void testSetRoomId() {
		System.out.println("setRoomId");
		int expectedResult = 2;
		player.setRoomId(2);
		assertEquals(expectedResult, player.getRoomId());
	}

	/**
	 * Test of getName method, of class Player.
	 */
	@Test
	public void testGetName() {
		String expResult = "speler";
		String result = player.getName();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getScore method, of class Player.
	 */
	@Test
	public void testGetScore() {
		System.out.println("getScore");
		int expResult = 0;
		int result = player.getScore();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setScore method, of class Player.
	 */
	@Test
	public void testSetScore() {
		System.out.println("setScore");
		int score = 10;
		player.setScore(score);
		assertEquals(score, player.getScore());
	}
	
		/**
	 * Test of setScore method, of class Player.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testSetScoreNegative() {
		System.out.println("setScore");
		int score = -1;
		player.setScore(score);
		assertEquals(score, player.getScore());
	}

	/**
	 * Test of receiveDamage method, of class Player.
	 */
	@Test
	public void testReceiveDamage() {
		System.out.println("receiveDamage");
		int amount = 10;
		String attacker = "andereSpeler";
		player.receiveDamage(amount, attacker);
		assertEquals(100-amount, player.getHitPoints());
	}
	/**
	 * Test of receiveDamage method, of class Player.
	 */
	@Test
	public void testReceiveDamageNegative() {
		System.out.println("receiveDamage");
		int amount = -10;
		String attacker = "andereSpeler";
		player.receiveDamage(amount, attacker);
		assertEquals(100-amount, player.getHitPoints());
	}
		/**
	 * Test of receiveDamage method, of class Player.
	 */
	@Test
	public void testReceiveDamageMoreThanHP() {
		System.out.println("receiveDamage");
		int amount = 1000;
		String attacker = "andereSpeler";
		try {
			player.receiveDamage(amount, attacker);
		} catch (NullPointerException ex){
		
		}	
		assertEquals(0, player.getHitPoints());
	}

	/**
	 * Test of die method, of class Player.
	 */
	@Test
	public void testDie() {
		System.out.println("die");
		String killer = "andereSpeler";
		player.die(killer);
		assertTrue(player.isDead);
	}

	/**
	 * Test of respawn method, of class Player.
	 */
	@Test
	public void testRespawn() {
		String killer = "respawn";
		player.die(killer);
		player.respawn();
		assertFalse(player.isDead);
		assertEquals(100, player.getHitPoints());
	}

	/**
	 * Test of getPosition method, of class Player.
	 */
	@Test
	public void testGetPosition() {
		System.out.println("getPosition");
		Vector expResult = new Vector(2f,2f);
		Vector result = player.getPosition();
		assertEquals(expResult, result);
	}
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


	/**
	 * Test of move method, of class Player.
	 */
	@Test
	public void testMove() {
		System.out.println("move");
		player.direction = Direction.LEFT;
		try {
			player.move();
		} catch (NullPointerException ex){
		
		}
		
		assertEquals(2f-player.getMoveSpeed(), player.getPosition().getX(),0);
		assertEquals(2f, player.getPosition().getY(),0);
	}
	/**
	 * Test of move method, of class Player.
	 */
	@Test
	public void testMoveLeft() {
		System.out.println("move");
		player.direction = Direction.LEFT;
		try {
			player.move();
		} catch (NullPointerException ex){
		
		}
		
		assertEquals(2f-player.getMoveSpeed(), player.getPosition().getX(),0);
		assertEquals(2f, player.getPosition().getY(),0);
	}
	/**
	 * Test of move method, of class Player.
	 */
	@Test
	public void testMoveRight() {
		System.out.println("move");
		player.direction = Direction.RIGHT;
		try {
			player.move();
		} catch (NullPointerException ex){
		
		}
		
		assertEquals(2f+player.getMoveSpeed(), player.getPosition().getX(),0);
		assertEquals(2f, player.getPosition().getY(),0);
	}
	/**
	 * Test of move method, of class Player.
	 */
	@Test
	public void testMoveDown() {
		System.out.println("move");
		player.direction = Direction.DOWN;
		try {
			player.move();
		} catch (NullPointerException ex){
		
		}
		
		assertEquals(2f, player.getPosition().getX(),0);
		assertEquals(2f-player.getMoveSpeed(), player.getPosition().getY(),0);
	}
	/**
	 * Test of move method, of class Player.
	 */
	@Test
	public void testMoveUp() {
		System.out.println("move");
		player.direction = Direction.UP;
		try {
			player.move();
		} catch (NullPointerException ex){
		
		}
		
		assertEquals(2f, player.getPosition().getX(),0);
		assertEquals(2f+player.getMoveSpeed(), player.getPosition().getY(),0);
	}

	/**
	 * Test of getCollisionBox method, of class Player.
	 */
	@Test
	public void testGetCollisionBoxLeft() {
		System.out.println("getCollisionBox");
		player.direction = Direction.LEFT;
		Rectangle expResult = new Rectangle(2f+16-5f,2f,Utils.GRIDSIZE - 32,Utils.GRIDSIZE - 16);
		Rectangle result = player.getCollisionBox();
		assertEquals(expResult, result);
	}
		/**
	 * Test of getCollisionBox method, of class Player.
	 */
	@Test
	public void testGetCollisionBoxRight() {
		System.out.println("getCollisionBox");
		player.direction = Direction.RIGHT;
		Rectangle expResult = new Rectangle(2f+16+5f,2f,Utils.GRIDSIZE - 32,Utils.GRIDSIZE - 16);
		Rectangle result = player.getCollisionBox();
		assertEquals(expResult, result);
	}
		/**
	 * Test of getCollisionBox method, of class Player.
	 */
	@Test
	public void testGetCollisionBoxUp() {
		System.out.println("getCollisionBox");
		player.direction = Direction.UP;
		Rectangle expResult = new Rectangle(2f+16,2f+5f,Utils.GRIDSIZE - 32,Utils.GRIDSIZE - 16);
		Rectangle result = player.getCollisionBox();
		assertEquals(expResult, result);
	}
		/**
	 * Test of getCollisionBox method, of class Player.
	 */
	@Test
	public void testGetCollisionBoxDown() {
		System.out.println("getCollisionBox");
		player.direction = Direction.DOWN;
		Rectangle expResult = new Rectangle(2f+16,2f-5f,Utils.GRIDSIZE - 32,Utils.GRIDSIZE - 16);
		Rectangle result = player.getCollisionBox();
		assertEquals(expResult, result);
	}
		@Test
	public void testGetCollisionBoxDownSprinting() {
		System.out.println("getCollisionBox");
		player.setSprint(true);
		player.direction = Direction.DOWN;
		Rectangle expResult = new Rectangle(2f+16,2f-8f,Utils.GRIDSIZE - 32,Utils.GRIDSIZE - 16);
		Rectangle result = player.getCollisionBox();
		assertEquals(expResult, result);
	}

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ffxvi.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ffxvi.game.support.Vector;
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
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of getX method, of class Player.
	 */
	@Test
	public void testGetX() {
		System.out.println("getX");
		Player instance = null;
		float expResult = 0.0F;
		float result = instance.getX();
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getY method, of class Player.
	 */
	@Test
	public void testGetY() {
		System.out.println("getY");
		Player instance = null;
		float expResult = 0.0F;
		float result = instance.getY();
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getCurrentAnim method, of class Player.
	 */
	@Test
	public void testGetCurrentAnim() {
		System.out.println("getCurrentAnim");
		Player instance = null;
		Animation expResult = null;
		Animation result = instance.getCurrentAnim();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setPosition method, of class Player.
	 */
	@Test
	public void testSetPosition_float_float() {
		System.out.println("setPosition");
		float x = 0.0F;
		float y = 0.0F;
		Player instance = null;
		instance.setPosition(x, y);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setPosition method, of class Player.
	 */
	@Test
	public void testSetPosition_Vector() {
		System.out.println("setPosition");
		Vector position = null;
		Player instance = null;
		instance.setPosition(position);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getMoveSpeed method, of class Player.
	 */
	@Test
	public void testGetMoveSpeed() {
		System.out.println("getMoveSpeed");
		Player instance = null;
		float expResult = 0.0F;
		float result = instance.getMoveSpeed();
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setMoveSpeed method, of class Player.
	 */
	@Test
	public void testSetMoveSpeed() {
		System.out.println("setMoveSpeed");
		float moveSpeed = 0.0F;
		Player instance = null;
		instance.setMoveSpeed(moveSpeed);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setSprint method, of class Player.
	 */
	@Test
	public void testSetSprint() {
		System.out.println("setSprint");
		boolean isSprinting = false;
		Player instance = null;
		instance.setSprint(isSprinting);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setAimDirection method, of class Player.
	 */
	@Test
	public void testSetAimDirection_Vector() {
		System.out.println("setAimDirection");
		Vector position = null;
		Player instance = null;
		boolean expResult = false;
		boolean result = instance.setAimDirection(position);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setAimDirection method, of class Player.
	 */
	@Test
	public void testSetAimDirection_float_float() {
		System.out.println("setAimDirection");
		float controllerInputX = 0.0F;
		float controllerInputY = 0.0F;
		Player instance = null;
		instance.setAimDirection(controllerInputX, controllerInputY);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getName method, of class Player.
	 */
	@Test
	public void testGetName() {
		System.out.println("getName");
		Player instance = null;
		String expResult = "";
		String result = instance.getName();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getScore method, of class Player.
	 */
	@Test
	public void testGetScore() {
		System.out.println("getScore");
		Player instance = null;
		int expResult = 0;
		int result = instance.getScore();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setScore method, of class Player.
	 */
	@Test
	public void testSetScore() {
		System.out.println("setScore");
		int score = 0;
		Player instance = null;
		instance.setScore(score);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getHitPoints method, of class Player.
	 */
	@Test
	public void testGetHitPoints() {
		System.out.println("getHitPoints");
		Player instance = null;
		int expResult = 0;
		int result = instance.getHitPoints();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getPosition method, of class Player.
	 */
	@Test
	public void testGetPosition() {
		System.out.println("getPosition");
		Player instance = null;
		Vector expResult = null;
		Vector result = instance.getPosition();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of render method, of class Player.
	 */
	@Test
	public void testRender() {
		System.out.println("render");
		SpriteBatch batch = null;
		Player instance = null;
		instance.render(batch);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of update method, of class Player.
	 */
	@Test
	public void testUpdate() {
		System.out.println("update");
		Player instance = null;
		instance.update();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of fire method, of class Player.
	 */
	@Test
	public void testFire() {
		System.out.println("fire");
		Player instance = null;
		instance.fire();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of slash method, of class Player.
	 */
	@Test
	public void testSlash() {
		System.out.println("slash");
		Player instance = null;
		instance.slash();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of DirectionInput method, of class Player.
	 */
	@Test
	public void testDirectionInput() {
		System.out.println("DirectionInput");
		Direction direction = null;
		Player instance = null;
		instance.DirectionInput(direction);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setIdle method, of class Player.
	 */
	@Test
	public void testSetIdle() {
		System.out.println("setIdle");
		Player instance = null;
		instance.setIdle();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
	
}

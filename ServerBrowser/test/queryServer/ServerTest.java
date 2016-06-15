/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queryServer;

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
public class ServerTest {
	
	public ServerTest() {
	}
	
	IServer instance;
	@Before
	public void setUp() {
		instance = new Server("127.0.0.1",420,10,"testServers");
	}


	/**
	 * Test of getAddress method, of class Server.
	 */
	@Test
	public void testGetAddress() throws Exception {
		String expResult = "127.0.0.1";
		String result = instance.getAddress();
		assertEquals(expResult, result);
		
	}

	/**
	 * Test of getPort method, of class Server.
	 */
	@Test
	public void testGetPort() throws Exception {
		System.out.println("getPort");
		int expResult = 420;
		int result = instance.getPort();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getFullAddress method, of class Server. This method is supposed to return: address + ":" + port
	 */
	@Test
	public void testGetFullAddress() throws Exception {
		String expResult = "127.0.0.1:420";
		String result = instance.getFullAddress();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getName method, of class Server.
	 */
	@Test
	public void testGetName() throws Exception {
		String expResult = "testServers";
		String result = instance.getName();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getPlayers method, of class Server.
	 */
	@Test
	public void testGetPlayers() throws Exception {
		int expResult = 10;
		int result = instance.getPlayers();
		assertEquals(expResult, result);
	}
	
}

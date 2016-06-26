/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queryServer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
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
public class ServerListTest {
	
	public ServerListTest() {
	}
	IServerList instance;
	@Before
	public void setUp() throws RemoteException {
		instance = new ServerList();
	}

	/**
	 * Test of getServers method, of class ServerList.
	 */
	@Test
	public void testGetServers() throws RemoteException {
		List<IServer> expResult = new ArrayList<>();
		List<IServer> result = instance.getServers();
		assertEquals(expResult.size(), result.size());

	}

	/**
	 * Test of addServer method, of class ServerList.
	 */
	@Test
	public void testAddServer_3args() throws Exception {

		int players = 10;
		String address = "127.0.0.1";
		int port = 420;
		ServerList instance = new ServerList();
		instance.addServer(players, address, port);
		assertEquals(1,instance.getServers().size());
	}

	/**
	 * Test of addServer method, of class ServerList.
	 */
	@Test
	public void testAddServer_4args() throws Exception {
		System.out.println("addServer");
		int players = 10;
		String address = "127.0.0.1";
		int port = 420;
		String name = "TestServer";
		ServerList instance = new ServerList();
		instance.addServer(players, address, port, name);
		
		assertEquals(1,instance.getServers().size());
	}

	/**
	 * Test of serverExists method, of class ServerList.
	 */
	@Test
	public void testServerExists() throws Exception {
		System.out.println("serverExists");
		String address = "";
		int port = 0;
		ServerList instance = new ServerList();
		boolean expResult = false;
		boolean result = instance.serverExists(address, port);
		assertEquals(expResult, result);

	}

	/**
	 * Test of removeServer method, of class ServerList.
	 */
	@Test
	public void testRemoveServer() throws Exception {
		int players = 10;
		String address = "127.0.0.1";
		int port = 420;
		String name = "TestServer";
		instance.addServer(players, address, port, name);
		instance.removeServer("127.0.0.1:420");
		assertEquals(0, instance.getServers().size());
	}

	
}

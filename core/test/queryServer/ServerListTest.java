/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queryServer;

import java.rmi.RemoteException;
import org.junit.*;

/**
 *
 * @author Acer
 */
public class ServerListTest {
    ServerList serverList;
    /*
    @Before
    public void init() throws RemoteException {
        serverList = new ServerList();
    }
    /*
    @Test
    public void addServer() throws RemoteException {
        Server server1 = new Server("127.0.0.1", 420);
        serverList.addServer(server1.getAddress(), server1.getPort());
        Assert.assertEquals(1, serverList.getServers().size());
    }
    
    @Test
    public void removeServer() throws RemoteException {
        Server server1 = new Server("127.0.0.1", 420);
        serverList.addServer(server1.getAddress(), server1.getPort());
        Assert.assertEquals(1, serverList.getServers().size());
        
        serverList.removeServer(server1.getFullAddress());
        Assert.assertEquals(0, serverList.getServers().size());
    }
    
    @Test
    public void removeServerNoServer() throws RemoteException {
        Server server1 = new Server("127.0.0.1", 420);
        Server server2 = new Server("127.0.0.2", 420);
        serverList.addServer(server2.getAddress(), server2.getPort());
        Assert.assertEquals(1, serverList.getServers().size());
        
        serverList.removeServer(server1.getFullAddress());
        Assert.assertEquals(1, serverList.getServers().size());
    }
    */
    @Test
    public void getServers() {
        Assert.assertNotNull(serverList.getServers());
    }
   */
}

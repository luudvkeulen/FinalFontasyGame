package queryServer;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClearServers extends TimerTask {
	
	private final ServerList serverList;
	private final Registry registry;
	
	public ClearServers(ServerList sl, Registry r) {
		this.serverList = sl;
		this.registry = r;
	}
	
	@Override
	public void run() {
		/*for(IServer server : serverList.servers) {
			try {
				System.out.println(server.getFullAddress());
			} catch (RemoteException ex) {
				Logger.getLogger(ClearServers.class.getName()).log(Level.SEVERE, null, ex);
			}
		}*/
		serverList.clearServers();
		try {
			registry.rebind("serverList", serverList);
			//System.out.println("Succesfully rebinded server list");
		} catch (RemoteException re) {
			System.out.println("queryServer: RemoteException: " + re.getMessage());
		}
	}
}

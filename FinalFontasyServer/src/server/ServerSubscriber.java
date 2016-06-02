package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import queryServer.IServerList;
public class ServerSubscriber {
	
	private Registry registry;
	private IServerList serverList;
	
	private static final int PORT = 420;
	private static final String IP = "localhost";
	static final String BINDINGNAME = "serverList";
	
	public ServerSubscriber() throws IOException {		
		connectToRegistry();
		
		serverList.addServer(Inet4Address.getLocalHost().getHostAddress(), 1338);
		System.out.println(Inet4Address.getLocalHost().getHostAddress());
		
		startTimer();
	}
	
	public ServerSubscriber(String address) throws RemoteException, UnknownHostException {
		connectToRegistry();
		serverList.addServer(address, 1338);
		startTimer();
	}
	
	public ServerSubscriber(String address, int port) throws RemoteException, UnknownHostException {
		connectToRegistry();
		serverList.addServer(address, port);
		startTimer();
	}
	
	private void connectToRegistry() {
		//Try to get the registry
		try {
			registry = LocateRegistry.getRegistry(IP, PORT);
			System.out.println("Registry lookup succesfull");
		} catch (RemoteException re) {
			System.out.println("GameServer: RemoteException: " + re.getMessage());
			registry = null;
		}
		
		if(registry != null) {
			try {
				serverList = (IServerList) registry.lookup(BINDINGNAME);
			} catch (RemoteException re) {
				System.out.println("GameServer: RemoteException: " + re.getMessage());
				serverList = null;
			} catch (NotBoundException nbe) {
				System.out.println("GameServer: NotBoundException: " + nbe.getMessage());
				serverList = null;
			}
		}
	}
	
	private void startTimer() throws UnknownHostException {
		Timer timer = new Timer();
		timer.schedule(new ServerSubscriberTask(serverList, Inet4Address.getLocalHost().getHostAddress(), 1338), 0, 500);
	}
	
	public void removeServer(String address) throws RemoteException {		
		if(serverList != null) {
			serverList.removeServer(address);
		}
	}
}

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
	private String address;
	private int port;
	private Timer timer;
	
	private static final int PORT = 420;
	private static final String IP = "128.199.32.134";
	static final String BINDINGNAME = "serverList";
	
	public ServerSubscriber() throws IOException {		
		connectToRegistry();
		
		serverList.addServer(Inet4Address.getLocalHost().getHostAddress(), 1338);
		System.out.println("Added server");
		this.address = Inet4Address.getLocalHost().getHostAddress();
		this.port = 0;
		
		startTimer("", 0);
	}
	
	public ServerSubscriber(String address) throws RemoteException, UnknownHostException {
		connectToRegistry();
		this.address = address;
		this.port = 0;
		serverList.addServer(address, 1338);
		startTimer(address, 0);
	}
	
	public ServerSubscriber(String address, int port) throws RemoteException, UnknownHostException {
		connectToRegistry();
		this.address = address;
		this.port = port;
		serverList.addServer(address, port);
		startTimer(address, port);
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
				System.out.println("ServerList binded");
			} catch (RemoteException re) {
				System.out.println("GameServer: RemoteException: " + re.getMessage());
				serverList = null;
			} catch (NotBoundException nbe) {
				System.out.println("GameServer: NotBoundException: " + nbe.getMessage());
				serverList = null;
			}
		}
	}
	
	private void startTimer(String address, int port) throws UnknownHostException {
		timer = new Timer();
		if(address.equals("") && port == 0) {
			timer.schedule(new ServerSubscriberTask(serverList, Inet4Address.getLocalHost().getHostAddress(), 1338), 0, 500);
		} else if(!address.equals("") && port == 0) {
			timer.schedule(new ServerSubscriberTask(serverList, address, 1338), 0, 500);
		} else {
			timer.schedule(new ServerSubscriberTask(serverList, address, port), 0, 500);
		}
	}
	
	private void startTimer(String address, int port, String name) throws UnknownHostException {
		timer = new Timer();
		if(address.equals("") && port == 0) {
			timer.schedule(new ServerSubscriberTask(serverList, Inet4Address.getLocalHost().getHostAddress(), 1338), 0, 500);
		} else if(!address.equals("") && port == 0) {
			timer.schedule(new ServerSubscriberTask(serverList, address, 1338, name), 0, 500);
		} else {
			timer.schedule(new ServerSubscriberTask(serverList, address, port, name), 0, 500);
		}
	}
	
	public void removeServer(String address) throws RemoteException {		
		if(serverList != null) {
			serverList.removeServer(address);
		}
	}
	
	public void renameServer(String name) throws RemoteException, UnknownHostException {
		if(name.equals("")) return;
		timer.cancel();
		serverList.removeServer(this.address + ":" + this.port);
		if(port != 0) {
			serverList.addServer(address, port, name);
			startTimer(address, port, name);
		} else {
			serverList.addServer(address, 1338, name);
			startTimer(address, 1338, name);
		}
	}
}

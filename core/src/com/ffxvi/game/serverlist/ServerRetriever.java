package com.ffxvi.game.serverlist;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
public class ServerRetriever {
	
	private Registry registry;
	private IServerList serverList;
	
	private static final int PORT = 420;
	private static final String IP = "localhost";
	static final String BINDINGNAME = "serverList";
	
	public ServerRetriever() throws IOException {
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
	
	public List<String> getAddresses() throws RemoteException {
		List<String> addresses = new ArrayList<String>();
		if(registry != null) {
			for(IServer server : serverList.getServers()) {
				addresses.add(server.getFullAddress());
			}
		}
		return addresses;
	}
	
	public static void main(String[] args) throws IOException {
		ServerRetriever server = new ServerRetriever();
	}
}

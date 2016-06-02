package server;

import java.rmi.RemoteException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import queryServer.IServerList;

public class ServerSubscriberTask extends TimerTask {
	private final IServerList serverList;
	private final String address;
	private final int port;
	
	public ServerSubscriberTask (IServerList serverList, String address, int port) {
		this.serverList = serverList;
		this.address = address;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			serverList.addServer(address, port);
		} catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		}
	}
}

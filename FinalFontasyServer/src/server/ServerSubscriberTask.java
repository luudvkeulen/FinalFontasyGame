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
	private final String name;
	
	public ServerSubscriberTask (IServerList serverList, String address, int port) {
		this.serverList = serverList;
		this.address = address;
		this.port = port;
		this.name = "";
	}
	
	public ServerSubscriberTask (IServerList serverList, String address, int port, String name) {
		this.serverList = serverList;
		this.address = address;
		this.port = port;
		this.name = name;
	}
	
	@Override
	public void run() {
		try {
			if(name.equals("")){
				serverList.addServer(address, port);
			} else {
				serverList.addServer(address, port, name);
			}
		} catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		}
	}
}

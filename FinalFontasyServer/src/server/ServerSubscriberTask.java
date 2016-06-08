package server;

import java.rmi.RemoteException;
import java.util.TimerTask;
import queryServer.IServerList;

public class ServerSubscriberTask extends TimerTask {
	private final IServerList serverList;
	private final String address;
	private final int port;
	private final String name;
	private final Server server;
	
	public ServerSubscriberTask (Server server, IServerList serverList, String address, int port) {
		this.serverList = serverList;
		this.address = address;
		this.port = port;
		this.name = "";
		this.server = server;
	}
	
	public ServerSubscriberTask (Server server, IServerList serverList, String address, int port, String name) {
		this.serverList = serverList;
		this.address = address;
		this.port = port;
		this.name = name;
		this.server = server;
	}
	
	@Override
	public void run() {
		try {
			if(name.equals("")){
				serverList.addServer(server.getPlayerInfo().size(), address, port);
			} else {
				serverList.addServer(server.getPlayerInfo().size(), address, port, name);
			}
		} catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		}
	}
}

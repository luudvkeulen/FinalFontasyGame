package com.ffxvi.game.serverlist;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	String getAddress() throws RemoteException;
	
	int getPort() throws RemoteException;
	
	String getFullAddress() throws RemoteException;
}

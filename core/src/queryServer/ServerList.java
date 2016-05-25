/*
 * (C) Copyright 2016 - S33A
 * Final Fontasy XVI, Version 1.0.
 * 
 * Contributors:
 *   Pim Janissen
 *   Luud van Keulen
 *   Robin de Kort
 *   Koen Schilders
 *   Guido Thomasse
 *   Joel Verbeek
 */
package queryServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the servers.
 *
 */
public class ServerList extends UnicastRemoteObject implements IServerList {

    /**
     * A list of servers.
     */
    private final List<IServer> servers;

    /**
     * Initializes a new ServerList.
     *
     * @throws RemoteException when an (un)expected error regarding RMI occurs.
     */
    public ServerList() throws RemoteException {
        this.servers = new ArrayList();
    }

    @Override
    public List<IServer> getServers() {
        return this.servers;
    }

    @Override
    public void addServer(String address, int port) throws RemoteException {
        servers.add(new Server(address, port));
        System.out.println("added server:" + address + port);
        for (IServer server : servers) {
            System.out.println(server.getFullAddress());
        }
    }
}
